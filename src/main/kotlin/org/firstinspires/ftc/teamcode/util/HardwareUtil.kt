package org.firstinspires.ftc.teamcode.util

import dev.nextftc.hardware.actuators.NextMotor
import dev.nextftc.hardware.actuators.NextMotor.Direction

object HardwareUtil {
    fun motor(
        name: String,
        direction: Direction = Direction.FORWARD,
        zeroPowerBehavior: NextMotor.ZeroPowerBehavior = NextMotor.ZeroPowerBehavior.BRAKE
    ): NextMotor {
        return NextMotor(name).apply {
            this.direction = direction
            this.zeroPowerBehavior = zeroPowerBehavior
        }
    }
}