package org.firstinspires.ftc.teamcode.util.mechanisms

import com.pedropathing.ivy.commands.Commands
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot.*
import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.hardware.IMU
import dev.nextftc.hardware.actuators.NextMotor
import dev.nextftc.hardware.sensors.NextIMU
import dev.nextftc.robot.Mechanism
import dev.nextftc.robot.drive.mecanumDrive
import dev.nextftc.robot.drive.mecanumDriveFieldCentric
import dev.nextftc.robot.drive.tankDrive
import org.firstinspires.ftc.teamcode.util.HardwareUtil.motor

class Drivetrain : Mechanism {
    private val lf = motor("leftFront", NextMotor.Direction.REVERSE)
    private val lb = motor("leftBack", NextMotor.Direction.REVERSE)
    private val rf = motor("rightFront")
    private val rb = motor("rightBack")
    private val motors = listOf(lf, rf, lb, rb)

    private val revHubOrientation = RevHubOrientationOnRobot(LogoFacingDirection.FORWARD, UsbFacingDirection.UP)
    private val imu = NextIMU().apply { initialize(IMU.Parameters(revHubOrientation)) }

    fun mecanum(gamepad: Gamepad) = mecanumDrive(lf, rf, lb, rb, gamepad)
    fun fieldCentric(gamepad: Gamepad) = mecanumDriveFieldCentric(lf, rf, lb, rb, gamepad, imu::yaw)
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
    }
        .setEnd { stop() }
        .requiring(this)

    fun stop(){ motors.forEach { it.throttle = 0.0 } }
    fun reset(){ imu.resetYaw() }
}