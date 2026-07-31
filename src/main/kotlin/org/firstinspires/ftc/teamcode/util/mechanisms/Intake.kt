package org.firstinspires.ftc.teamcode.util.mechanisms

import dev.nextftc.hardware.actuators.NextMotor
import dev.nextftc.hardware.sensors.NextDigitalSensor
import dev.nextftc.robot.*
import org.firstinspires.ftc.teamcode.util.HardwareUtil.motor

class Intake : Mechanism {
    private val intake = motor("Intake")
    private val beam = NextDigitalSensor("breakBeam", true)

    private var count = 0
    private var lastDetected = false

    val on = instant { intake.throttle = 1.0 }
    val off = instant { intake.throttle = 0.0 }
    val reverse = instant { intake.throttle = -1.0 }
    fun custom(throttle: () -> Double) = infinite { intake.throttle = throttle() }

    override fun periodic() {
        val detected: Boolean = !beam.isTriggered
        if (detected && !lastDetected) count++

        lastDetected = detected

        Telemetry.log(beam.debug())
        Telemetry.log("Artifacts:", count)
    }

    fun getCount() = count

    fun setCount(count: Int): Int {
        this.count = count
        return this.count
    }

    fun reset(){
        count = 0
        reverse.cancel()
    }

    fun overflow() = count == 3
}