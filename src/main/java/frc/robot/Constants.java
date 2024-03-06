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
                public static final double angleToletrance = 0.3f;

                // Auto field constants
                public static final double notesX = 2.87;
                public static final double yBetweenNotes = 1.44;
                // AUTO code
                public static AutoPoint[] dontMove = {
                                new AutoPoint(new Pose2d(new Translation2d(0, 0), new Rotation2d(0)),
                                                RobotState.TRAVEL),
                };
                public static AutoPoint[] oneMeter = {
                                new AutoPoint(new Pose2d(new Translation2d(0, 0), new Rotation2d(0)),
                                                RobotState.TRAVEL),
                                new AutoPoint(new Pose2d(new Translation2d(1, 0), new Rotation2d(0)),
                                                RobotState.TRAVEL),
                };

                public static AutoPoint[] middle_one = {
                                new AutoPoint(new Pose2d(new Translation2d(0.92, 0), new Rotation2d(0)),
                                                RobotState.TRAVEL),
                                new AutoPoint(new Pose2d(new Translation2d(0.92, 0), new Rotation2d(0)),
                                                RobotState.SUBWOOFER),
                                new AutoPoint(new Pose2d(new Translation2d(notesX + 0.2, 0), new Rotation2d(0)),
                                                RobotState.INTAKE),
                                new AutoPoint(new Pose2d(new Translation2d(notesX + 0.2, 0), new Rotation2d(0)),
                                                RobotState.PODIUM),
                                new AutoPoint(new Pose2d(new Translation2d(notesX + 0.2, 0), new Rotation2d(0)),
                                                RobotState.TRAVEL),
                };
                public static AutoPoint[] middle_three = {
                                new AutoPoint(new Pose2d(new Translation2d(0.92, 0), new Rotation2d(0)),
                                                RobotState.TRAVEL),
                                new AutoPoint(new Pose2d(new Translation2d(0.92, 0), new Rotation2d(0)),
                                                RobotState.TRAVEL),
                                new AutoPoint(new Pose2d(new Translation2d(0.92, 0), new Rotation2d(0)),
                                                RobotState.SUBWOOFER),
                                new AutoPoint(new Pose2d(new Translation2d(2.6, 0), new Rotation2d(0)),
                                                RobotState.INTAKE),
                                new AutoPoint(new Pose2d(new Translation2d(2.6, 0), new Rotation2d(0)),
                                                RobotState.PODIUM),
                                // 2ND
                                new AutoPoint(new Pose2d(new Translation2d(2, 0), new Rotation2d(0)),
                                                RobotState.TRAVEL),
                                new AutoPoint(new Pose2d(new Translation2d(1.6, yBetweenNotes),
                                                new Rotation2d(0)),
                                                RobotState.TRAVEL),
                                new AutoPoint(new Pose2d(new Translation2d(2.2, yBetweenNotes - 0.15),
                                                new Rotation2d(0)),
                                                RobotState.INTAKE),
                                new AutoPoint(new Pose2d(new Translation2d(0.92, 0), new Rotation2d(0)),
                                                RobotState.INTAKE),
                                new AutoPoint(new Pose2d(new Translation2d(0.92, 0), new Rotation2d(0)),
                                                RobotState.SUBWOOFER),
                                // 3RD
                                new AutoPoint(new Pose2d(new Translation2d(1.6, -yBetweenNotes),
                                                new Rotation2d(0)),
                                                RobotState.INTAKE),
                                new AutoPoint(new Pose2d(new Translation2d(2.5, -yBetweenNotes),
                                                new Rotation2d(0)),
                                                RobotState.INTAKE),
                                new AutoPoint(new Pose2d(new Translation2d(0.92, 0), new Rotation2d(0)),
                                                RobotState.INTAKE),
                                new AutoPoint(new Pose2d(new Translation2d(0.92, 0), new Rotation2d(0)),
                                                RobotState.SUBWOOFER),
                };

                public static AutoPoint[] right_one = {
                                new AutoPoint(new Pose2d(new Translation2d(0, -1), new Rotation2d(0)),
                                                RobotState.TRAVEL),
                                new AutoPoint(new Pose2d(new Translation2d(0, -1), new Rotation2d(0)),
                                                RobotState.TRAVEL),
                                new AutoPoint(new Pose2d(new Translation2d(0.75, -0.7),
                                                new Rotation2d(Math.toRadians(0))),
                                                RobotState.TRAVEL),
                                new AutoPoint(new Pose2d(new Translation2d(0.75, -0.7),
                                                new Rotation2d(Math.toRadians(-45))),
                                                RobotState.TRAVEL),
                                new AutoPoint(new Pose2d(new Translation2d(0.75, -0.7),
                                                new Rotation2d(Math.toRadians(-45))),
                                                RobotState.SUBWOOFER),
                                new AutoPoint(new Pose2d(new Translation2d(0.75, -0.7),
                                                new Rotation2d(Math.toRadians(-45))),
                                                RobotState.SUBWOOFER),
                                new AutoPoint(new Pose2d(new Translation2d(0.75, -0.7), new Rotation2d(0)),
                                                RobotState.TRAVEL),
                                new AutoPoint(new Pose2d(new Translation2d(2, -0.7), new Rotation2d(0)),
                                                RobotState.INTAKE),

                };
                public static AutoPoint[] left_one = {
                                new AutoPoint(new Pose2d(new Translation2d(0, 1), new Rotation2d(0)),
                                                RobotState.TRAVEL),
                                new AutoPoint(new Pose2d(new Translation2d(0, 1), new Rotation2d(0)),
                                                RobotState.TRAVEL),
                                new AutoPoint(new Pose2d(new Translation2d(0.75, 0.7),
                                                new Rotation2d(Math.toRadians(0))),
                                                RobotState.TRAVEL),
                                new AutoPoint(new Pose2d(new Translation2d(0.75, 0.7),
                                                new Rotation2d(Math.toRadians(45))),
                                                RobotState.TRAVEL),
                                new AutoPoint(new Pose2d(new Translation2d(0.75, 0.7),
                                                new Rotation2d(Math.toRadians(45))),
                                                RobotState.SUBWOOFER),
                                new AutoPoint(new Pose2d(new Translation2d(0.75, 0.7),
                                                new Rotation2d(Math.toRadians(45))),
                                                RobotState.SUBWOOFER),
                                new AutoPoint(new Pose2d(new Translation2d(0.75, 0.7), new Rotation2d(0)),
                                                RobotState.TRAVEL),
                                new AutoPoint(new Pose2d(new Translation2d(2, 0.7), new Rotation2d(0)),
                                                RobotState.INTAKE),

                };

                public static AutoPoint[] side_red = {
                                // 1 shooting stage
                                createAutoPoint(2.90, 2.55, 0, RobotState.TRAVEL),
                                createAutoPoint(2.90, 2.55, 0, RobotState.TRAVEL),
                                createAutoPoint(3.3, 2.55, 0, RobotState.TRAVEL),
                                createAutoPoint(3.3, 2.55, 63, RobotState.TRAVEL),
                                createAutoPoint(3.3, 2.55, 63, RobotState.PODIUM),

                                // Intake 2 Note
                                createAutoPoint(3.3, 2.55, 0, RobotState.TRAVEL),
                                createAutoPoint(3.3, 4.75, 0, RobotState.TRAVEL),
                                createAutoPoint(9.5, 4.75, 0, RobotState.INTAKE),
                                createAutoPoint(7, 4, 0, RobotState.INTAKE),
                                createAutoPoint(3.5, 2.3, 0, RobotState.TRAVEL),

                                // 2 shooting stage
                                createAutoPoint(3.4, 2.2, 57, RobotState.TRAVEL),
                                createAutoPoint(3.4, 2.2, 57, RobotState.PODIUM),
                                createAutoPoint(3.4, 2.2, 0, RobotState.TRAVEL)

                };
                public static AutoPoint[] side_blue = {
                                // 1 shooting stage
                                createAutoPoint(2.90, -2.55, 0, RobotState.TRAVEL),
                                createAutoPoint(2.90, -2.55, 0, RobotState.TRAVEL),
                                createAutoPoint(3.3, -2.55, 0, RobotState.TRAVEL),
                                createAutoPoint(3.3, -2.55, -63, RobotState.TRAVEL),
                                createAutoPoint(3.3, -2.55, -63, RobotState.PODIUM),

                                // Intake 2 Note
                                createAutoPoint(3.3, -2.55, 0, RobotState.TRAVEL),
                                createAutoPoint(3.3, -4.75, 0, RobotState.TRAVEL),
                                createAutoPoint(10, -4.75, 0, RobotState.INTAKE),
                                createAutoPoint(7, -4, 0, RobotState.INTAKE),

                                // 2 shooting stage
                                createAutoPoint(3.5, -2.3, 0, RobotState.TRAVEL),
                                createAutoPoint(3.4, -2.2, -57, RobotState.TRAVEL),
                                createAutoPoint(3.4, -2.2, -57, RobotState.PODIUM),
                                createAutoPoint(3.4, -2.2, 0, RobotState.TRAVEL)

                };

                public static AutoPoint createAutoPoint(double x, double y, double degree, RobotState robotState) {
                        return new AutoPoint(
                                        new Pose2d(new Translation2d(x, y), new Rotation2d(Math.toRadians(degree))),
                                        robotState);
                }
        }

        public static final class DriveConstants {

                public static final double MAX_VELOCITY_METERS_PER_SECOND = 2;
                public static final double DRIVE_STICK_DEADBAND = 0.05;
                public static final double DRIVE_MODIFT_AXIS_K = 0.3;

                // The left-to-right distance between the drivetrain wheels
                // Should be measured from center to center.
                public static final double DRIVETRAIN_TRACKWIDTH_METERS = 0.57;

                // The front-to-back distance between the drivetrain wheels.
                // Should be measured from center to center.
                public static final double DRIVETRAIN_WHEELBASE_METERS = 0.57;
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
                public static final double FRONT_LEFT_MODULE_STEER_OFFSET = -Math.toRadians(203.75518798828125);
                // public static final double FRONT_LEFT_MODULE_STEER_OFFSET =
                // -Math.toRadians(0);
                // Front Right
                public static final int FRONT_RIGHT_MODULE_DRIVE_MOTOR = 62;
                public static final int FRONT_RIGHT_MODULE_STEER_MOTOR = 61;
                public static final int FRONT_RIGHT_MODULE_STEER_ENCODER = 9;
                public static final double FRONT_RIGHT_MODULE_STEER_OFFSET = -Math.toRadians(208.3831787109375);// was 207.861328125
                // public static final double FRONT_RIGHT_MODULE_STEER_OFFSET =
                // -Math.toRadians(0);

                // Back Left
                public static final int BACK_LEFT_MODULE_DRIVE_MOTOR = 55;
                public static final int BACK_LEFT_MODULE_STEER_MOTOR = 18;
                public static final int BACK_LEFT_MODULE_STEER_ENCODER = 29;
                public static final double BACK_LEFT_MODULE_STEER_OFFSET = -Math.toRadians(216.47186279296875);
                // public static final double BACK_LEFT_MODULE_STEER_OFFSET =
                // -Math.toRadians(0);

                // Back Right
                public static final int BACK_RIGHT_MODULE_DRIVE_MOTOR = 57;
                public static final int BACK_RIGHT_MODULE_STEER_MOTOR = 1;
                public static final int BACK_RIGHT_MODULE_STEER_ENCODER = 8;
                public static final double BACK_RIGHT_MODULE_STEER_OFFSET = -Math.toRadians(241.52069091796875);
                // public static final double BACK_RIGHT_MODULE_STEER_OFFSET =
                // -Math.toRadians(0);

                private static final double driveGearRatio = (6.75 / 1.0);
                private static final double wheelCircumference = 0.116 * Math.PI;
                public static final double ticksPerMeter = driveGearRatio * 2048 / wheelCircumference;

                public static final double RotationPID_Kp = 0.1;
                public static final double RotationPID_Ki = 0.0002;
                public static final double RotationPID_Kd = 0.0001;

        }

        // lime constants:
        public static final double tyTolerance = 0.5;
        public static final double wantedTY = 16.2;
        public static final double distanceKp = 0.22;

        public static final float INF = 100000;

        public static final float maxVelAuto = 3; // m/s
}
