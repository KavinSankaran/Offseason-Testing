package org.firstinspires.ftc.teamcode.util.mechanisms

import com.pedropathing.ivy.commands.Commands
import com.qualcomm.robotcore.hardware.Gamepad
import dev.nextftc.hardware.actuators.NextMotor
import dev.nextftc.hardware.sensors.NextPinpoint
import dev.nextftc.robot.Mechanism
import dev.nextftc.robot.drive.mecanumDrive
import dev.nextftc.robot.drive.mecanumDriveFieldCentric
import dev.nextftc.robot.drive.tankDrive

class Drivetrain : Mechanism {
    private val lf = NextMotor("leftFront").apply {
        direction = NextMotor.Direction.REVERSE
        zeroPowerBehavior = NextMotor.ZeroPowerBehavior.BRAKE
    }
    private val lb = NextMotor("leftBack").apply {
        direction = NextMotor.Direction.REVERSE
        zeroPowerBehavior = NextMotor.ZeroPowerBehavior.BRAKE
    }
    private val rf = NextMotor("rightFront").apply { zeroPowerBehavior = NextMotor.ZeroPowerBehavior.BRAKE }
    private val rb = NextMotor("rightBack").apply { zeroPowerBehavior = NextMotor.ZeroPowerBehavior.BRAKE }
    private val motors = listOf(lf, rf, lb, rb)

    private val pinpoint = NextPinpoint("pinpoint").apply { resetPosAndIMU() }

    fun mecanum(gamepad: Gamepad) = mecanumDrive(lf, rf, lb, rb, gamepad)
    fun fieldCentric(gamepad: Gamepad) = mecanumDriveFieldCentric(lf, rf, lb, rb, gamepad, pinpoint.pose.heading::toDouble)
    fun tank(gamepad: Gamepad) = tankDrive(lf, rf, lb, rb, gamepad)

    val strafeLeft = setDtPowers(-1.0, 1.0, 1.0, -1.0)
    val strafeRight = setDtPowers(1.0, -1.0, -1.0, 1.0)
    val forward = setDtPowers(1.0, 1.0, 1.0, 1.0)
    val backward = setDtPowers(-1.0, -1.0, -1.0, -1.0)

    fun setDtPowers(lfPow: Double, lbPow: Double, rfPow: Double, rbPow: Double) = Commands.infinite {
        lf.throttle = lfPow
        rf.throttle = rfPow
        lb.throttle = lbPow
        rb.throttle = rbPow
    }.setEnd { stop() }.requiring(this)

    fun stop() = motors.forEach { it.throttle = 0.0 }
    fun reset() = pinpoint.resetPosAndIMU()

    override fun periodic() = pinpoint.update()
}
