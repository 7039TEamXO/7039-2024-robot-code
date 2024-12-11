// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Intake;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.AnalogInput;
import frc.robot.DashBoard;

/** Add your docs here. */
public class Intake {
    private static TalonFX intake = new TalonFX(4);
    private static AnalogInput irInput = new AnalogInput(0);
    private static int irValue = irInput.getValue();
    private static boolean disableIR = false;

    private static double power = 0;

    public static void init() {
        intake.setInverted(true);
        // DashBoard.data.addNumber("Intake Vel", () ->
        // intake.getSelectedSensorVelocity());
        // DashBoard.data.addNumber("Intake Stator", () -> intake.getStatorCurrent());
        // DashBoard.data.addNumber("Intake Supplier", () -> intake.getSupplyCurrent());

    }

    public static void operate(IntakeState state) {
        irValue = irInput.getValue();
        disableIR =  DashBoard.disable_ir_input.getBoolean(false);
        switch (state) {
            case DEPLETE:
                power = -0.5;
                break;
            case COLLECT:
                power = 0.65;
                break;
            case STOP:
                power = 0;
                break;
            case LOADING:
                power = -0.05;

                break;

        }

        intake.set(ControlMode.PercentOutput, power);
        // System.out.println(irValue);
    }

    public static double getCurrent() {
        return intake.getStatorCurrent();
    }
    public static double getTemperature(){
        return intake.getTemperature();
    }
    public static boolean isGamePieceIn() {
        if (disableIR) {
            return false;
        }
        return irValue >= 1700;
    }

    public static int getIrInput() {
        return irValue;
    }

}
