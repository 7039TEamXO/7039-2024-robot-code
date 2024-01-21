// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Shooter;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

/** Add your docs here. */
public class Shooter {
    private static TalonFX shooterMaster = new TalonFX(11);
    private static TalonFX shooterSlave = new TalonFX(12);
    private static double vel_w = 0;

    public static void init(){
        shooterMaster.setInverted(false);
        shooterSlave.setInverted(true);
        shooterSlave.follow(shooterMaster);
    }

    //TODO TUNE

    public static void operate(ShooterState state) {
        switch (state) {
            case AMP_SHOOTING:
                vel_w = 0.1;
                break;
            case DEPLETE:
                vel_w = 0.1;
                break;
            case PODIUM_SHOOTING:
                vel_w = 0.1;
                break;
            case STOP:
                vel_w = 0.1;
                break;
            case SUBWOOFER_SHOOTING:
                vel_w = 0.1;
                break;
        }
        shooterMaster.set(ControlMode.Velocity, vel_w);
    }

    public static boolean readyToShoot(){
        return Math.abs(vel_w - shooterMaster.getSelectedSensorVelocity()) < 0.1;
    }
}
