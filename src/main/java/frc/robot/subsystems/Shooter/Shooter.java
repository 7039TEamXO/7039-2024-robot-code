// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Shooter;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.Constants;
import frc.robot.DashBoard;
import frc.robot.GlobalData;
import frc.robot.LimeLight;
import frc.robot.Robot;
import frc.robot.subsystems.SubSystemManager;

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
        shooterMaster.configClosedloopRamp(0.2);
        shooterSlave.configClosedloopRamp(0.2);

    }

    // TODO TUNE

    public static void operate(ShooterState state) {
        switch (state) {
            case AMP_SHOOTING:
                vel_w = -6250 + DashBoard.getAmpOffset();
                break;
            case DEPLETE:
                vel_w = 0.4;
                break;
            case PODIUM_SHOOTING:
                vel_w = 13000;
                break;
            case STOP:
                vel_w = 0;
                break;
            case SUBWOOFER_SHOOTING:
                vel_w = -12000;
                break;
            case DEFLECT:
                vel_w = 2500;
                break;
        }
        if (vel_w == 0) {
            shooterMaster.set(ControlMode.PercentOutput, 0);
        } else {
            shooterMaster.set(ControlMode.Velocity, vel_w);
        }

    }

    public static boolean readyToShoot() {
        if (vel_w != 0 && Math.abs(Math.abs(vel_w) - Math.abs(shooterMaster.getSelectedSensorVelocity())) < 800) {
            counter++;
        } else {
            counter = 0;
        }
        // System.out.println(" vel not 0: " + (vel_w != 0) + " error: " +
        // (Math.abs(Math.abs(vel_w) -
        // Math.abs(shooterMaster.getSelectedSensorVelocity())) < 300));
        if (Robot.autoFirst) {
            return counter > 3 && GlobalData.auto;
        }
        final boolean inVel = counter > 3;
        return inVel && GlobalData.auto || SubSystemManager.joyPs4Controller.getR2Button()
                && (Math.abs(Math.abs(LimeLight.getTy()) - Constants.wantedTY) < Constants.tyTolerance) && (Math.abs(LimeLight.getTx()) < 5) && inVel
                || SubSystemManager.joyPs4Controller.getL2Button() && inVel; // shooter spinning at wanted velocity and driver
                                                                    // wants to shoot
    }

    public static double getShooterMasterVelocity() {
        return shooterMaster.getSelectedSensorVelocity();
    }

    public static double getShooterMasterCurrent() {
        return shooterMaster.getStatorCurrent();
    }

    public static double getShooterSlaveVelocity() {
        return shooterSlave.getSelectedSensorVelocity();
    }

    public static double getShooterSlaveCurrent() {
        return shooterSlave.getStatorCurrent();
    }
}
