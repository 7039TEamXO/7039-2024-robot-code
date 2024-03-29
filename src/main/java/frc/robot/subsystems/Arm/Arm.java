// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Arm;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

/** Add your docs here. */
public class Arm {
    private static TalonFX armMotor = new TalonFX(60);

    private static double wantedPos = 0;

    @SuppressWarnings("removal")
    public static void init() {
        armMotor.setInverted(false);
        armMotor.config_kP(0, 0.15);
        armMotor.config_kI(0, 0.0001);
        armMotor.setSelectedSensorPosition(0);
        armMotor.setNeutralMode(NeutralMode.Brake);
        armMotor.configReverseSoftLimitThreshold(0);
        armMotor.configReverseSoftLimitEnable(true);
        armMotor.configForwardSoftLimitThreshold(15000);
        armMotor.configForwardSoftLimitEnable(true);
        armMotor.configPeakOutputForward(0.65);
        armMotor.configPeakOutputReverse(-0.3);
        armMotor.configAllowableClosedloopError(0, 50);
        armMotor.configNominalOutputForward(0.085);
        armMotor.configMotionCruiseVelocity(5000);
        armMotor.configMotionAcceleration(20000);
        // StatorCurrentLimitConfiguration statorLimit = new StatorCurrentLimitConfiguration();
        // statorLimit.currentLimit = 50;
        // statorLimit.enable = true;
        // armMotor.configStatorCurrentLimit(statorLimit);
    }

    public static void operate(ArmState state) {
        // System.out.println(armMotor.getSelectedSensorPosition());
        switch (state) {
            case CLOSE:
                wantedPos = 0;
                break;
            case OPEN:
                wantedPos = 12250;// 12650;//14000
                break;
        }
        armMotor.set(ControlMode.MotionMagic, wantedPos);
    }

    public static boolean reached() {
        return Math.abs(wantedPos - armMotor.getSelectedSensorPosition()) < 700;
    }
}
