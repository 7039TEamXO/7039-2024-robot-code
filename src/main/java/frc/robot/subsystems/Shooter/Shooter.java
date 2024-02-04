// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Shooter;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix6.configs.SlotConfigs;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.DashBoard;

/** Add your docs here. */
public class Shooter {

    private static TalonFX shooterMaster = new TalonFX(54);
    private static TalonFX shooterSlave = new TalonFX(5);
    private static double vel_w = 0;
    private static int counter = 0;
    public static ShuffleboardTab data = Shuffleboard.getTab("Data");

    public static void init() {
        shooterMaster.setInverted(false);
        shooterSlave.setInverted(true);
        shooterMaster.configNeutralDeadband(0);
        shooterSlave.configNeutralDeadband(0);
        shooterSlave.follow(shooterMaster);
        shooterMaster.config_kP(0, 0.15);
        shooterMaster.config_kF(0, 0.063);
        DashBoard.data.addNumber("Sh M V", () -> shooterMaster.getSelectedSensorVelocity());
        DashBoard.data.addNumber("Sh S V", () -> shooterSlave.getSelectedSensorVelocity());
        DashBoard.data.addNumber("Sh S Stat", () -> shooterSlave.getStatorCurrent());
        DashBoard.data.addNumber("Sh S Sup", () -> shooterSlave.getSupplyCurrent());
        DashBoard.data.addNumber("Sh M Stat", () -> shooterMaster.getStatorCurrent());
        DashBoard.data.addNumber("Sh M Sup", () -> shooterMaster.getSupplyCurrent());
    }

    // TODO TUNE

    public static void operate(ShooterState state) {
        switch (state) {
            case AMP_SHOOTING:
                vel_w = -6000;
                break;
            case DEPLETE:
                vel_w = 0.4;
                break;
            case PODIUM_SHOOTING:
                vel_w = 11000;
                break;
            case STOP:
                vel_w = 0;
                break;
            case SUBWOOFER_SHOOTING:
                vel_w = -10000;
                break;
        }
       shooterMaster.set(ControlMode.Velocity, vel_w);
   
    }

    public static boolean readyToShoot() {
        if (vel_w != 0 && Math.abs(Math.abs(vel_w) - Math.abs(shooterMaster.getSelectedSensorVelocity())) < 800) {
            counter++;
        } else {
            counter = 0;
        }
        // System.out.println(" vel not 0: " + (vel_w != 0) + " error: " + (Math.abs(Math.abs(vel_w) - Math.abs(shooterMaster.getSelectedSensorVelocity())) < 300));
        return counter > 10;
    }
}
  