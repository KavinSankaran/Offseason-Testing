package org.firstinspires.ftc.teamcode.subsystems

import dev.nextftc.hardware.actuators.NextMotor
import dev.nextftc.hardware.sensors.NextDigitalSensor
import dev.nextftc.robot.Mechanism
import dev.nextftc.robot.Telemetry

class Intake : Mechanism {
    private val intake = NextMotor("Intake").apply { direction = NextMotor.Direction.REVERSE }
    private val beam = NextDigitalSensor("breakBeam", true)

    enum class BeamState { BLOCKED, OPEN }
    private var state = BeamState.BLOCKED
    private var count = 0

    val on = instant { intake.throttle = 1.0 }
    val off = instant { intake.throttle = 0.0 }
    val reverse = instant { intake.throttle = -1.0 }
    fun custom(throttle: () -> Double) = infinite { intake.throttle = throttle() }

    override fun periodic() {
        when (state) {
            BeamState.BLOCKED -> {
                if (!beam.isTriggered) {
                    state = BeamState.OPEN
                }
            }

            BeamState.OPEN -> {
                if (beam.isTriggered && intake.throttle >= 0.0) {
                    count++
                    state = BeamState.BLOCKED
                } else {
                    count += 0
                }
            }
        }

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
