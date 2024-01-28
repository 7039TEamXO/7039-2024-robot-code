// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Conveyor;
import frc.robot.Constants;

/** Add your docs here. */
public class Conveyor {
    public static double s_power = Constants.Conveyor.power;
    public static void init() {
        Constants.Conveyor.conveyorMaster.setInverted(false);
        Constants.Conveyor.conveyorSlave.setInverted(true);
        Constants.Conveyor.conveyorSlave.follow(Constants.Conveyor.conveyorMaster);
    }

    public static void operate(ConveyorState state) {
        switch (state) {
            case HIGH_SHOOTER:
                s_power = 0.5;
                break;
            case LOW_SHOOTER:
                s_power = -0.5;
                break;
            case STOP:
                s_power = 0;
                break;
        }
    }
}