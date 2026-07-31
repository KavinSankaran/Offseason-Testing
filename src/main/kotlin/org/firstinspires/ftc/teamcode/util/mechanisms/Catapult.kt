package org.firstinspires.ftc.teamcode.util.mechanisms

import com.pedropathing.ivy.commands.Commands.*
import com.pedropathing.ivy.groups.Groups.sequential
import dev.nextftc.hardware.actuators.NextMotor
import dev.nextftc.robot.Mechanism
import org.firstinspires.ftc.teamcode.util.HardwareUtil.motor

class Catapult : Mechanism {
    private val right = motor("launcher")
    private val left = motor("launcher2").apply { follow(right, NextMotor.Direction.REVERSE) }

    private var isUp = false

    val up = instant { right.throttle = 1.0 }
    val down = instant { right.throttle = -1.0 }
    val stop = instant { right.throttle = 0.0 }

    val volt = sequential(up, waitMs(100.0), stop).requiring(this)
    val stabilize = sequential(up, waitMs(0.9), down).requiring(this)
    val toggle = sequential(
        instant { isUp = !isUp },
        conditional({ !isUp }, volt, down)
    ).requiring(this)
}