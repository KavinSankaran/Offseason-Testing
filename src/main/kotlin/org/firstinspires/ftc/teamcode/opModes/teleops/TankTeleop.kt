package org.firstinspires.ftc.teamcode.opModes.teleops

import com.pedropathing.ivy.Scheduler
import com.pedropathing.ivy.Scheduler.schedule
import com.pedropathing.ivy.commands.Commands
import com.pedropathing.ivy.commands.Commands.*
import com.pedropathing.ivy.groups.Groups.*
import dev.nextftc.robot.opmode.BulkReadHook
import dev.nextftc.robot.opmode.NextOpMode
import dev.nextftc.robot.opmode.NextTeleop
import dev.nextftc.robot.triggers.CommandGamepad
import dev.nextftc.robot.triggers.Trigger
import org.firstinspires.ftc.teamcode.util.Robot

@NextTeleop("TankTeleop")
class TankTeleop(private val robot: Robot) : NextOpMode(robot, BulkReadHook) {
    init {
        Trigger.defaultEventLoop.clear()
        Scheduler.reset()

        val gp1 = CommandGamepad(gamepad = gamepad1)
        val gp2 = CommandGamepad(gamepad = gamepad2)

        Trigger { robot.intake.overflow() }.onTrue(
            sequential(
                robot.intake.reverse,
                instant { robot.intake.setCount(3) }
            )
        )

        schedule(robot.drivetrain.tank(gamepad1))

        gp1.leftBumper.whileTrue(robot.drivetrain.strafeLeft)
        gp1.rightBumper.whileTrue(robot.drivetrain.strafeRight)
        gp1.y.whileTrue(robot.drivetrain.forward)
        gp1.a.whileTrue(robot.drivetrain.backward)

        gp1.a.and(gp1.start).onTrue(
            parallel(
                instant(robot.drivetrain::stop),
                instant(robot.drivetrain.backward::cancel)
            )
        )

        gp2.rightStickY.isBetween(-0.01..0.01)
            .whileFalse(robot.intake.custom { -gp2.rightStickY.value })
            .whileTrue(robot.intake.off)

        gp2.a.onTrue(Commands.instant(robot.intake::reset))

        gp2.rightBumper.onTrue(robot.catapult.toggle)
        gp2.leftBumper.onTrue(robot.catapult.stabilize)
    }
}