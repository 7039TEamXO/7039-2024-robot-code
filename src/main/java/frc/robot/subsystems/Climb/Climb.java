// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Climb;

import com.ctre.phoenix.motorcontrol.ControlMode;

import frc.robot.Constants;

/** Add your docs here. */
public class Climb {
    private static double servo_wantedPos = Constants.Climb.wantedPos;
    private static double motor_power = Constants.Climb.power;

    public static void init() {
        Constants.Climb.climbMotor.setInverted(false);
    }

    public static void operate(ClimbState state) {

        switch (state) {
            case CLIMB:
                servo_wantedPos = 1;
                motor_power = 0;
                break;
            case CLOSE:
                servo_wantedPos = 0.5;
                motor_power = 0;
                break;
            case UP:
                servo_wantedPos = 1;
                motor_power = 1;
                break;
          }
        Constants.Climb.climbMotor.set(ControlMode.Velocity, motor_power);
        Constants.Climb.servoMotor.setPosition(servo_wantedPos);
    }
}
