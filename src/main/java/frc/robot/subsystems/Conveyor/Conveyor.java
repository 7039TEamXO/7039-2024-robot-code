// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Conveyor;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import frc.robot.DashBoard;

/** Add your docs here. */
public class Conveyor {
    private static TalonFX conveyor = new TalonFX(15);
    private static TalonFX conveyorLower = new TalonFX(20);

    private static double power = 0;

    public static void init() {
        conveyor.setInverted(false);
        conveyorLower.setInverted(true);
        //  DashBoard.data.addNumber("conveyor Vel", () -> conveyor.getSelectedSensorVelocity());
        // DashBoard.data.addNumber("conveyorLower Vel", () -> conveyorLower.getSelectedSensorVelocity());
        // DashBoard.data.addNumber("conveyorLower Stator", () -> conveyorLower.getStatorCurrent());
        // DashBoard.data.addNumber("conveyorLower Supplier", () -> conveyorLower.getSupplyCurrent());
        // DashBoard.data.addNumber("conveyor Stator", () -> conveyor.getStatorCurrent());
        // DashBoard.data.addNumber("conveyor Supplier", () -> conveyor.getSupplyCurrent());
    }

    public static void operate(ConveyorState state) {
        switch (state) {
            case HIGH_SHOOTER:
                power = 0.8;
                break;
            case LOW_SHOOTER:
                power = -0.6;
                break;
            case STOP:
                power = 0;
                break;
        };
        conveyor.set(ControlMode.PercentOutput, power);
        conveyorLower.set(ControlMode.PercentOutput, power);

    }
}