// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Shooter;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

/** Add your docs here. */
public class Shooter {
    private static TalonFX shooterMaster = new TalonFX(54);
    private static TalonFX shooterSlave = new TalonFX(5);
    private static double vel_w = 0;

    public static void init(){
        shooterMaster.setInverted(false);
        shooterSlave.setInverted(false);
        shooterSlave.follow(shooterMaster);
    }

    //TODO TUNE

    public static void operate(ShooterState state) {
        switch (state) {
            case AMP_SHOOTING:
                vel_w = -0.4;
                break;
            case DEPLETE:
                vel_w = 0.4;
                break;
            case PODIUM_SHOOTING:
                vel_w = 0.8;
                break;
            case STOP:
                vel_w = 0;
                break;
            case SUBWOOFER_SHOOTING:
                vel_w = -0.7;
                break;
        }
        shooterMaster.set(ControlMode.PercentOutput, vel_w);
        System.out.println(shooterMaster.getSelectedSensorVelocity());
    }

    public static boolean readyToShoot(){
        return true;// Math.abs(vel_w - shooterMaster.getSelectedSensorVelocity()) < 0.1;
    }
}
