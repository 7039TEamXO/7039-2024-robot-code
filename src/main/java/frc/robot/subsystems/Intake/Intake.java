// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Intake;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import frc.robot.subsystems.RobotState;

import edu.wpi.first.wpilibj.AnalogInput;
import frc.robot.DashBoard;
import frc.robot.Robot;

/** Add your docs here. */
public class Intake {
    private static TalonFX intake = new TalonFX(4);
    private static AnalogInput ir_input = new AnalogInput(0);
    private static int ir_value = ir_input.getValue();

    private static double power = 0;

    public static void init() {
        intake.setInverted(true);
        // DashBoard.data.addNumber("Intake Vel", () ->
        // intake.getSelectedSensorVelocity());
        // DashBoard.data.addNumber("Intake Stator", () -> intake.getStatorCurrent());
        // DashBoard.data.addNumber("Intake Supplier", () -> intake.getSupplyCurrent());

    }

    public static void operate(IntakeState state) {
       ir_value =  ir_input.getValue();
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
            case LOADING:
                if (Robot.robotState.equals(RobotState.AMP)) {
                    //original power = -0.4;
                    
                    power = -0.2;
                } else {
                    power = -0.1;
                }
                break;

        }

        intake.set(ControlMode.PercentOutput, power);
    }

    public static double getCurrent() {
        return intake.getStatorCurrent();
    }

    public static boolean isGamePieceIn() {
        return ir_value >= 1700;
    }

    public static int getIr_input() {
        return ir_value;
    }

}
