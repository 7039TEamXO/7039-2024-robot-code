// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Conveyor;

import com.ctre.phoenix.motorcontrol.can.TalonFX;

/** Add your docs here. */
public class Conveyor {
    private static TalonFX conveyorMaster = new TalonFX(9);
    private static TalonFX conveyorSlave = new TalonFX(8);

    private static double power = 0;

    public static void init() {
        conveyorMaster.setInverted(false);
        conveyorSlave.setInverted(true);
        conveyorSlave.follow(conveyorMaster);
    }

    public static void operate(ConveyorState state) {
        switch (state) {
            case HIGH_SHOOTER:
                power = 0.5;
                break;
            case LOW_SHOOTER:
                power = -0.5;
                break;
            case STOP:
                power = 0;
                break;
        }
    }
}