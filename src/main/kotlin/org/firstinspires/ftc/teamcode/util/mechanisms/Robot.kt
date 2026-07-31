package org.firstinspires.ftc.teamcode.util.mechanisms

import dev.nextftc.hardware.RobotController
import dev.nextftc.robot.Mechanism
import dev.nextftc.robot.NextRobot
import org.firstinspires.ftc.teamcode.pedroPathing.Constants

class Robot : NextRobot {
    val intake = Intake()
    val catapult = Catapult()
    val drivetrain = Drivetrain()

    val follower by lazy { Constants.createFollower(RobotController.hardwareMap) }

    override val mechanisms: Set<Mechanism>
        get() = setOf(intake, catapult, drivetrain)

    init {
        intake.setCount(0)
        drivetrain.reset()
    }
}