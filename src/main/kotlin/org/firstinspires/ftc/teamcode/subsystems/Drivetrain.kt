package org.firstinspires.ftc.teamcode.subsystems

import com.pedropathing.ivy.commands.Commands
import dev.nextftc.hardware.actuators.NextMotor
import dev.nextftc.robot.Mechanism
import dev.nextftc.robot.triggers.CommandGamepad
import kotlin.math.abs
import kotlin.math.max

class Drivetrain : Mechanism {
    private val lf = NextMotor("leftFront").apply { direction = NextMotor.Direction.REVERSE }
    private val lb = NextMotor("leftBack").apply { direction = NextMotor.Direction.REVERSE }
    private val rf = NextMotor("rightFront")
    private val rb = NextMotor("rightBack")

    fun mecanum(gamepad: CommandGamepad) = Commands.infinite {
        val yValue = -gamepad.leftStickY.value
        val xValue = gamepad.leftStickX.value
        val rxValue = gamepad.rightStickX.value

        val denominator = max(abs(xValue) + abs(yValue) + abs(rxValue), 1.0)

        lf.setThrottle((yValue + xValue + rxValue) / denominator)
        lb.setThrottle((yValue - xValue + rxValue) / denominator)
        rf.setThrottle((yValue - xValue - rxValue) / denominator)
        rb.setThrottle((yValue + xValue - rxValue) / denominator)

    }

    fun tank(gamepad: CommandGamepad) = Commands.infinite {
        val leftPower = -gamepad.leftStickY.value
        val rightPower = -gamepad.rightStickY.value

        lf.setThrottle(leftPower)
        lb.setThrottle(leftPower)
        rf.setThrottle(rightPower)
        rb.setThrottle(rightPower)
    }

    val strafeLeft = setDtPowers(-1.0, 1.0, 1.0, -1.0)
    val strafeRight = setDtPowers(1.0, -1.0, -1.0, 1.0)
    val forward = setDtPowers(1.0, 1.0, 1.0, 1.0)
    val backward = setDtPowers(-1.0, -1.0, -1.0, -1.0)

    fun setDtPowers(lfPow: Double, lbPow: Double, rfPow: Double, rbPow: Double) = Commands.infinite {
        lf.setThrottle(lfPow)
        rf.setThrottle(rfPow)
        lb.setThrottle(lbPow)
        rb.setThrottle(rbPow)
    }
        .setEnd { stop() }
        .requiring(this)

    fun stop(){
        lf.setThrottle(0.0)
        lb.setThrottle(0.0)
        rf.setThrottle(0.0)
        rb.setThrottle(0.0)
    }
}