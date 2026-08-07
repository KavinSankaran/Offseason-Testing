package org.firstinspires.ftc.teamcode.util.mechanisms

import com.pedropathing.ivy.commands.Commands.conditional
import com.pedropathing.ivy.commands.Commands.waitMs
import com.pedropathing.ivy.groups.Groups.sequential
import dev.nextftc.hardware.actuators.NextMotor
import dev.nextftc.robot.Mechanism

class Catapult : Mechanism {
    private val right = NextMotor("launcher").apply { zeroPowerBehavior = NextMotor.ZeroPowerBehavior.BRAKE }
    private val left = NextMotor("launcher2").apply {
        follow(right, NextMotor.Direction.REVERSE)
        zeroPowerBehavior = NextMotor.ZeroPowerBehavior.BRAKE
    }

    private var isUp = false

    val up = instant { right.throttle = 1.0 }
    val down = instant { right.throttle = -1.0 }
    val stop = instant { right.throttle = 0.0 }

    val volt = sequential(up, waitMs(100.0), stop).requiring(this)
    val stabilize = sequential(up, waitMs(0.9), down).requiring(this)
    val toggle = sequential(
            instant { isUp = !isUp },
            conditional({ !isUp }, volt, down),
        ).requiring(this)
}
