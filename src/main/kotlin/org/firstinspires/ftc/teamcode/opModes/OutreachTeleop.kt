package org.firstinspires.ftc.teamcode.opModes

import com.pedropathing.ivy.Scheduler
import com.pedropathing.ivy.Scheduler.schedule
import com.pedropathing.ivy.commands.Commands.instant
import com.pedropathing.ivy.groups.Groups.parallel
import dev.nextftc.robot.opmode.BulkReadHook
import dev.nextftc.robot.opmode.NextOpMode
import dev.nextftc.robot.opmode.NextTeleop
import dev.nextftc.robot.triggers.CommandGamepad
import dev.nextftc.robot.triggers.Trigger
import org.firstinspires.ftc.teamcode.util.mechanisms.Robot

@NextTeleop("OutreachTeleop")
class OutreachTeleop(private val robot: Robot) : NextOpMode(robot, BulkReadHook){
    init {
        Trigger.defaultEventLoop.clear()
        Scheduler.reset()

        val gp1 = CommandGamepad(gamepad = gamepad1)
        val gp2 = CommandGamepad(gamepad = gamepad2)

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

        gp2.rightBumper.onTrue(robot.catapult.toggle)
        gp2.leftBumper.onTrue(robot.catapult.stabilize)
    }
}