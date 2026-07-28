package org.firstinspires.ftc.teamcode.subsystems

import com.pedropathing.ivy.commands.Commands
import dev.nextftc.hardware.actuators.NextMotor
import dev.nextftc.robot.Mechanism
import dev.nextftc.robot.triggers.CommandGamepad
import kotlin.math.abs
import kotlin.math.max

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

    fun mecanum(gamepad: CommandGamepad) = Commands.infinite {
        val yValue = -gamepad.leftStickY.value
        val xValue = gamepad.leftStickX.value
        val rxValue = gamepad.rightStickX.value

        val denominator = max(abs(xValue) + abs(yValue) + abs(rxValue), 1.0)

        lf.throttle = ((yValue + xValue + rxValue) / denominator)
        lb.throttle = ((yValue - xValue + rxValue) / denominator)
        rf.throttle = ((yValue - xValue - rxValue) / denominator)
        rb.throttle = ((yValue + xValue - rxValue) / denominator)

    }

    fun tank(gamepad: CommandGamepad) = Commands.infinite {
        val leftPower = -gamepad.leftStickY.value
        val rightPower = -gamepad.rightStickY.value

        lf.throttle = leftPower
        lb.throttle = leftPower
        rf.throttle = rightPower
        rb.throttle = rightPower
    }

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

    fun stop(){
        lf.throttle = 0.0
        lb.throttle = 0.0
        rf.throttle = 0.0
        rb.throttle = 0.0
    }
}