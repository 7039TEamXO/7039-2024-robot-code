// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Intake;

import com.ctre.phoenix.motorcontrol.can.TalonFX;

import frc.robot.Constants;
/** Add your docs here. */
public class Intake {

    private static double s_power = Constants.Intake.power;

    public static void init() {
        Constants.Intake.intake.setInverted(false);
    }

    public static void operate(IntakeState state) {
        switch (state) {
            case DEPLETE:
                s_power = -0.5;
                break;
            case COLLECT:
                s_power = 0.5;
                break;
            case STOP:
                s_power = 0;
                break;

        }
    }
}
