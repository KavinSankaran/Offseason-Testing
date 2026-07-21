package org.firstinspires.ftc.teamcode.subsystems

import dev.nextftc.hardware.actuators.NextMotor
import dev.nextftc.robot.Mechanism

class Intake : Mechanism {
    private val intake = NextMotor("Intake").apply { direction = NextMotor.Direction.REVERSE }

    val on = instant { intake.setThrottle(1.0) }
    val off = instant { intake.setThrottle(0.0) }
    val reverse = instant { intake.setThrottle(-1.0) }
    fun custom(throttle: () -> Double) = infinite { intake.setThrottle(throttle()) }
}