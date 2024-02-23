// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Climb;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
// import frc.robot.subsystems.Shooter.ShooterState;

import edu.wpi.first.wpilibj.Servo;
import frc.robot.Robot;
import frc.robot.subsystems.RobotState;

/** Add your docs here. */
public class Climb {
    private static TalonFX climbMotor = new TalonFX(21);
    private static Servo climbServo = new Servo(9);

    private static double wantedPower = 0;
    private static double servoPos = 0;

    public static void init() {
        climbMotor.setInverted(false);
        climbMotor.configReverseSoftLimitThreshold(-190000);
        climbMotor.configReverseSoftLimitEnable(true);

    }

    public static void operate(ClimbState state) {
        switch (state) {
            case DOWN:
                wantedPower = -0.5;
                break;
            case UP:
                wantedPower = 0.5;
                break;
            case STOP:
                wantedPower = 0;
                break;
        }
        climbMotor.set(ControlMode.PercentOutput, wantedPower);
        servoPos = Robot.robotState.equals(RobotState.CLIMB) ? 1 : 0;
        climbServo.set(servoPos);
    }
}