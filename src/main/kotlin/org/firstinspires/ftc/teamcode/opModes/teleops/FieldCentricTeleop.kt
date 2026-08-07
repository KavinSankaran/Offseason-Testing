package org.firstinspires.ftc.teamcode.opModes.teleops

import com.pedropathing.ivy.Scheduler
import com.pedropathing.ivy.Scheduler.schedule
import com.pedropathing.ivy.commands.Commands.*
import com.pedropathing.ivy.groups.Groups.*
import dev.nextftc.robot.opmode.BulkReadHook
import dev.nextftc.robot.opmode.NextOpMode
import dev.nextftc.robot.opmode.NextTeleop
import dev.nextftc.robot.triggers.CommandGamepad
import dev.nextftc.robot.triggers.Trigger
import org.firstinspires.ftc.teamcode.util.Robot

@NextTeleop("FieldCentricTeleop")
class FieldCentricTeleop(private val robot: Robot) : NextOpMode(robot, BulkReadHook) {
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

        schedule(robot.drivetrain.fieldCentric(gamepad1))

        gp1.rightTrigger.isOver(0.5).onTrue(instant(robot.drivetrain::reset))

        gp2.rightStickY.isBetween(-0.01..0.01)
            .whileFalse(robot.intake.custom { -gp2.rightStickY.value })
            .whileTrue(robot.intake.off)

        gp2.a.onTrue(instant(robot.intake::reset))

        gp2.rightBumper.onTrue(robot.catapult.toggle)
        gp2.leftBumper.onTrue(robot.catapult.stabilize)
    }
}