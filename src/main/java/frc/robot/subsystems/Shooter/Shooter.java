// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Shooter;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import frc.robot.Constants;

/** Add your docs here. */
public class Shooter {

    private static double sVel_w = Constants.Shooter.vel_w;

    public static void init() {
        Constants.Shooter.shooterMaster.setInverted(false);
        Constants.Shooter.shooterSlave.setInverted(true);
        Constants.Shooter.shooterSlave.follow(Constants.Shooter.shooterMaster);
    }

    // TODO TUNE

    public static void operate(ShooterState state) {
        switch (state) {
            case AMP_SHOOTING:
                sVel_w = 0.1;
                break;
            case DEPLETE:
                sVel_w = 0.1;
                break;
            case PODIUM_SHOOTING:
                sVel_w = 0.1;
                break;
            case STOP:
                sVel_w = 0.1;
                break;
            case SUBWOOFER_SHOOTING:
                sVel_w = 0.1;
                break;

               
        }
        Constants.Shooter.shooterMaster.set(ControlMode.Velocity, sVel_w);
    }

    public static boolean readyToShoot() {
        return Math.abs(sVel_w - Constants.Shooter.shooterMaster.getSelectedSensorVelocity()) < 0.1;
    }
}
