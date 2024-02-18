// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Arm;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
// import frc.robot.subsystems.Shooter.ShooterState;

import edu.wpi.first.wpilibj.Servo;
import frc.robot.Robot;
import frc.robot.subsystems.RobotState;

/** Add your docs here. */
public class Arm {
    private static TalonFX armMotor = new TalonFX(60);

    private static double wantedPos = 0;

    public static void init() {
        armMotor.setInverted(false);
        armMotor.config_kP(0, 0.03);
        armMotor.setSelectedSensorPosition(0);
        armMotor.setNeutralMode(NeutralMode.Brake);
        armMotor.configReverseSoftLimitThreshold(0);
        armMotor.configReverseSoftLimitEnable(true);
        armMotor.configForwardSoftLimitThreshold(15000);
        armMotor.configForwardSoftLimitEnable(true);
        armMotor.configPeakOutputForward(0.25);
        armMotor.configPeakOutputReverse(-0.1);
    }

    public static void operate(ArmState state) {
        switch (state) {
            case CLOSE:
            wantedPos = 0;
                break;
            case OPEN:
            wantedPos = 13500;
                break;
        }
        // armMotor.set(ControlMode.Position, wantedPos);
        System.out.println(armMotor.getSelectedSensorPosition());
    }

    public static boolean reached(){
        return true;//Math.abs(wantedPos - armMotor.getSelectedSensorPosition()) < 1000;
    }
}