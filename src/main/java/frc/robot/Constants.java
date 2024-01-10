// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.SPI;
import frc.robot.auto.AutoPoint;
import frc.robot.subsystems.RobotState;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class AutonomousConstants {
    public static final double AUTO_DRIVE_TIME = 4;
    public static final double AUTO_DRIVE_X_VELOCITY = 0.25;
    public static final double TO_RAMP_DRIVE_X_VELOCITY = 0.26;
    //
    public static final double BALANCE_DRIVE_X_VELOCITY = 0.12;
    public static final double BALANCE_PARAMETER = 0.0065;
    public static final double PITCH_PARAMETER = 9;
    //
    public static final double AUTO_EJECT_CUBE_FLOOR_MOTOR_OUTPUT = -0.80;
    public static final double AUTO_INTAKE_CUBE_FLOOR_MOTOR_OUTPUT = 0.5;

    public static final double distanceToletrance = 0.15f;
    public static final double angleToletrance = 0.25f;  
    public static AutoPoint[] bump = {
      new AutoPoint(new Pose2d(new Translation2d(0, 0), new Rotation2d(0)), RobotState.TRAVEL),
      /*new AutoPoint(new Pose2d(new Translation2d(4.5, 0), new Rotation2d(0)), RobotState.INTAKE_CUBE),*/
      new AutoPoint(new Pose2d(new Translation2d(5, -0.3), new Rotation2d(0)), RobotState.INTAKE_CUBE),
      new AutoPoint(new Pose2d(new Translation2d(5.5, -0.6), new Rotation2d(0)), RobotState.INTAKE_CUBE),
      new AutoPoint(new Pose2d(new Translation2d(7, -1), new Rotation2d(Math.toRadians(0))), RobotState.INTAKE_CUBE),
      new AutoPoint(new Pose2d(new Translation2d(7, -1), new Rotation2d(Math.toRadians(30))), RobotState.INTAKE_CUBE),
      new AutoPoint(new Pose2d(new Translation2d(4.5, -0.4), new Rotation2d(Math.toRadians(0))),
          RobotState.INTAKE_CUBE),
          new AutoPoint(new Pose2d(new Translation2d(2.5, -0.1), new Rotation2d(Math.toRadians(0))),
          RobotState.TRAVEL),
      new AutoPoint(new Pose2d(new Translation2d(0.2, 0), new Rotation2d(Math.toRadians(0))), RobotState.TRAVEL),
      new AutoPoint(new Pose2d(new Translation2d(-0.5, -0.7), new Rotation2d(Math.toRadians(0))), RobotState.MID_PLACE),
      
  };
  public static AutoPoint[] bumpBlue = {
    new AutoPoint(new Pose2d(new Translation2d(0, 0), new Rotation2d(0)), RobotState.TRAVEL),
    /*new AutoPoint(new Pose2d(new Translation2d(4.5, 0), new Rotation2d(0)), RobotState.INTAKE_CUBE),*/
    new AutoPoint(new Pose2d(new Translation2d(5, 0.3), new Rotation2d(0)), RobotState.INTAKE_CUBE),
    new AutoPoint(new Pose2d(new Translation2d(5.5, 0.4), new Rotation2d(0)), RobotState.INTAKE_CUBE),
    new AutoPoint(new Pose2d(new Translation2d(7, 0.4), new Rotation2d(Math.toRadians(0))), RobotState.INTAKE_CUBE),
    new AutoPoint(new Pose2d(new Translation2d(7, 0.4), new Rotation2d(Math.toRadians(-30))), RobotState.INTAKE_CUBE),
    new AutoPoint(new Pose2d(new Translation2d(4.5, 0.3), new Rotation2d(Math.toRadians(0))),
        RobotState.INTAKE_CUBE),
        new AutoPoint(new Pose2d(new Translation2d(2.5, 0.1), new Rotation2d(Math.toRadians(0))),
        RobotState.TRAVEL),
    new AutoPoint(new Pose2d(new Translation2d(0.2, 0), new Rotation2d(Math.toRadians(0))), RobotState.TRAVEL),
    new AutoPoint(new Pose2d(new Translation2d(-0.5, 0.7), new Rotation2d(Math.toRadians(0))), RobotState.MID_PLACE),
    
};
  public static AutoPoint[] climb = {
    new AutoPoint(new Pose2d(new Translation2d(0, 0), new Rotation2d(0)), RobotState.TRAVEL),
    new AutoPoint(new Pose2d(new Translation2d(7, 0), new Rotation2d(0)), RobotState.INTAKE_CUBE),
    
};

  }

  public static final class DriveConstants {

    public static final double MAX_VELOCITY_METERS_PER_SECOND = 2;
    public static final double DRIVE_STICK_DEADBAND = 0.05;
    public static final double DRIVE_MODIFT_AXIS_K = 0.3;

    // The left-to-right distance between the drivetrain wheels
    // Should be measured from center to center.
    public static final double DRIVETRAIN_TRACKWIDTH_METERS = 0.61;

    // The front-to-back distance between the drivetrain wheels.
    // Should be measured from center to center.
    public static final double DRIVETRAIN_WHEELBASE_METERS = 0.61;
    public static final SPI.Port DRIVETRAIN_NAVX_ID = SPI.Port.kMXP;

    public static final int CurrentLimtDrive = 45;
    public static final int CurrentLimtSteer = 30;
    public static final double RotationSpeedKp = 0.7;
    public static final double DriveSecondsFromNeutralToFull = 0;
    public static final double AlignPIDTolerance = 5;

    // Front Left
    public static final int FRONT_LEFT_MODULE_DRIVE_MOTOR = 6;
    public static final int FRONT_LEFT_MODULE_STEER_MOTOR = 7;
    public static final int FRONT_LEFT_MODULE_STEER_ENCODER = 11;
    public static final double FRONT_LEFT_MODULE_STEER_OFFSET = -Math.toRadians(24.334716796875);// 207.9437255859375-180 

    // Front Right
    public static final int FRONT_RIGHT_MODULE_DRIVE_MOTOR = 0;
    public static final int FRONT_RIGHT_MODULE_STEER_MOTOR = 1;
    public static final int FRONT_RIGHT_MODULE_STEER_ENCODER = 9;
    public static final double FRONT_RIGHT_MODULE_STEER_OFFSET = -Math.toRadians(29.786682128906246);// 207.32574462890625-180 

    // Back Left
    public static final int BACK_LEFT_MODULE_DRIVE_MOTOR = 4;
    public static final int BACK_LEFT_MODULE_STEER_MOTOR = 5;
    public static final int BACK_LEFT_MODULE_STEER_ENCODER = 10;
    public static final double BACK_LEFT_MODULE_STEER_OFFSET = -Math.toRadians(236.15976562500003);//119.7+180

    // Back Right
    public static final int BACK_RIGHT_MODULE_DRIVE_MOTOR = 2;
    public static final int BACK_RIGHT_MODULE_STEER_MOTOR = 3;
    public static final int BACK_RIGHT_MODULE_STEER_ENCODER = 8;
    public static final double BACK_RIGHT_MODULE_STEER_OFFSET = -Math.toRadians(128.49375);// 328.6171875


    private static final double driveGearRatio = (6.75 / 1.0);
    private static final double wheelCircumference = 0.116 * Math.PI;
    public static final double ticksPerMeter = driveGearRatio * 2048 / wheelCircumference;

    public static final double RotationPID_Kp = 0.1;
    public static final double RotationPID_Ki = 0.0002;
    public static final double RotationPID_Kd = 0.0001;

  }
}
