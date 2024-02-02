// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Intake;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
/** Add your docs here. */
public class Intake {
    private static TalonFX intake = new TalonFX(4);
    private static  AnalogInput ir_input = new AnalogInput(0);

    private static double power = 0;

    public static void init() {
        intake.setInverted(true);
    }

    public static void operate(IntakeState state) {
        switch (state) {
            case DEPLETE:
                power = -0.5;
                break;
            case COLLECT:
                power = 0.5;
                break;
            case STOP:
                power = 0;
                break;

        }

        intake.set(ControlMode.PercentOutput, power);
    }
    public static double getCurrent() {
        return intake.getStatorCurrent() ;
    }
    public static boolean isGamePieceIn() {
        return ir_input.getValue() >= 2000;}
      

}
