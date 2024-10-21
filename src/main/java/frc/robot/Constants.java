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
import frc.robot.subsystems.Intake.IntakeState;

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
                public static AutoPoint[] test = {
                                createAutoPoint(0, 0, 0, RobotState.TRAVEL),
                                createAutoPoint(0, -1, 0,RobotState.TRAVEL),
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
                                                RobotState.TRAVEL, 2.5f),
                                new AutoPoint(new Pose2d(new Translation2d(1.6, yBetweenNotes),
                                                new Rotation2d(0)),
                                                RobotState.TRAVEL, 2.5f),
                                new AutoPoint(new Pose2d(new Translation2d(2.2, yBetweenNotes),
                                                new Rotation2d(0)),
                                                RobotState.INTAKE),
                                new AutoPoint(new Pose2d(new Translation2d(0.92, 0), new Rotation2d(0)),
                                                RobotState.INTAKE),
                                new AutoPoint(new Pose2d(new Translation2d(0.92, 0), new Rotation2d(0)),
                                                RobotState.SUBWOOFER),
                                // 3RD
                                new AutoPoint(new Pose2d(new Translation2d(1.6, -yBetweenNotes),
                                                new Rotation2d(0)),
                                                RobotState.INTAKE, 2.5f),
                                new AutoPoint(new Pose2d(new Translation2d(2.5, -yBetweenNotes),
                                                new Rotation2d(0)),
                                                RobotState.INTAKE),
                                new AutoPoint(new Pose2d(new Translation2d(0.92, 0), new Rotation2d(0)),
                                                RobotState.INTAKE),
                                new AutoPoint(new Pose2d(new Translation2d(0.92, 0), new Rotation2d(0)),
                                                RobotState.SUBWOOFER),
                };

                public static AutoPoint[] middle_red = {
                                createAutoPoint(0.92, 0, 0, RobotState.TRAVEL),
                                createAutoPoint(0.92, 0, 0, RobotState.TRAVEL),
                                createAutoPoint(0.92, 0, 0, RobotState.SUBWOOFER),
                                createAutoPoint(2.6, 0, 0, RobotState.INTAKE, 1.2f),
                                createAutoPoint(2.6, 0, 0, RobotState.PODIUM),
                                createAutoPoint(2.6, 0, -90, RobotState.TRAVEL),
                                createAutoPoint(2.2, -yBetweenNotes, -90, RobotState.INTAKE, 1.3f),
                                createAutoPoint(0.92, 0, 0, RobotState.TRAVEL, 1),
                                createAutoPoint(0.92, 0, 0, RobotState.SUBWOOFER),
                                createAutoPoint(2.4, yBetweenNotes, 35, RobotState.INTAKE, 1.3f),
                                createAutoPoint(0.92, 0, 0, RobotState.INTAKE, 1),
                                createAutoPoint(0.92, 0, 0, RobotState.SUBWOOFER),
                                createAutoPoint(8, -1.75, 0, RobotState.TRAVEL)

                };
                public static AutoPoint[] middle_blue = {
                                createAutoPoint(0.92, 0, 0, RobotState.TRAVEL),
                                createAutoPoint(0.92, 0, 0, RobotState.TRAVEL),
                                createAutoPoint(0.92, 0, 0, RobotState.SUBWOOFER),
                                createAutoPoint(2.6, 0, 0, RobotState.INTAKE, 1.2f),
                                createAutoPoint(2.6, 0, 0, RobotState.PODIUM),
                                createAutoPoint(2.6, 0, 90, RobotState.TRAVEL),
                                createAutoPoint(2.4, yBetweenNotes, 90, RobotState.INTAKE, 1.3f),
                                createAutoPoint(0.92, 0, 0, RobotState.TRAVEL, 1),
                                createAutoPoint(0.92, 0, 0, RobotState.SUBWOOFER),
                                createAutoPoint(2.4, -yBetweenNotes, -35, RobotState.INTAKE, 1.3f),
                                createAutoPoint(0.92, 0, 0, RobotState.INTAKE, 1),
                                createAutoPoint(0.92, 0, 0, RobotState.SUBWOOFER),
                                createAutoPoint(8, 1.75, 0, RobotState.TRAVEL)
                };

                public static double farNoteY = 3.75;

                public static AutoPoint[] side_red = {
                                //first
                                createAutoPoint(0.5, 0.5, 60, RobotState.TRAVEL),
                                createAutoPoint(0.5, 0.5, 60, RobotState.SUBWOOFER),
                                //second
                                createAutoPoint(4.5, 2.6, 0, RobotState.INTAKE, 2.2f),
                                createAutoPoint(8.3, 0.55, -10, RobotState.INTAKE, 1.7f),//1.2
                                createAutoPoint(8.3, 0.55, -10, RobotState.INTAKE, 1.7f),
                                createAutoPoint(4.8, 0.2, -10, RobotState.TRAVEL, 2.2f),
                                createAutoPoint(2.5, -1, -15, RobotState.TRAVEL, 2.2f),// y = -1
                                
                                createAutoPoint(2.5, -1, -15, RobotState.PODIUM),//d-10 x2.8
                                //last
                                createAutoPoint(2.5, -0.4, -10, RobotState.TRAVEL),
                                createAutoPoint(8.3, -0.4, 5, RobotState.INTAKE, 2.4f),// y-0.2 d10
                                createAutoPoint(4.5, -0.2, -10, RobotState.INTAKE, 2.2f),
                                createAutoPoint(2.1, -0.8, -15, RobotState.TRAVEL, 2.2f),//y-0.75 d10
                                createAutoPoint(2.1, -0.8, -15, RobotState.PODIUM)
                                //createAutoPoint(2.3, -0.75, -5, RobotState.PODIUM),//y-0.75 d10



                // public static AutoPoint[] side_red = {
                //                 //first
                //                 createAutoPoint(0.5, 0.5, 60, RobotState.TRAVEL),
                //                 createAutoPoint(0.5, 0.5, 60, RobotState.SUBWOOFER),
                //                 //second
                //                 createAutoPoint(4.5, 2.6, 0, RobotState.INTAKE, 2.2f),
                //                 createAutoPoint(8.3, 0.1, -10, RobotState.INTAKE, 1.7f),//1.2
                //                 createAutoPoint(8.3, -0.2, -10, RobotState.INTAKE, 1.7f),
                //                 createAutoPoint(6.8, 0.0, -10, RobotState.TRAVEL, 2.2f),
                //                 createAutoPoint(2.5, -1, -10, RobotState.TRAVEL, 2.2f),// y = -1
                                
                //                 createAutoPoint(2.3, -1, -10, RobotState.PODIUM),//d-10 x2.8
                //                 // //last
                //                 createAutoPoint(2.3, -0.4, -10, RobotState.TRAVEL),
                //                 createAutoPoint(8.3, -0.4, 5, RobotState.INTAKE, 2.4f),// y-0.2 d10
                //                 createAutoPoint(2.3, -1, -10, RobotState.INTAKE, 2.2f),//y-0.75 d10
                //                 createAutoPoint(2.3, -1, -10, RobotState.PODIUM)
                //                 //createAutoPoint(2.3, -0.75, -5, RobotState.PODIUM),//y-0.75 d10

                };
                public static AutoPoint[] side_blue = {
                                createAutoPoint(0.5, -0.5, -60, RobotState.TRAVEL),
                                createAutoPoint(0.5, -0.5, -60, RobotState.SUBWOOFER),
                                createAutoPoint(4.5, -2.6, 0, RobotState.INTAKE, 2.2f),
                                createAutoPoint(8.5, -1.2, 10, RobotState.INTAKE, 1.7f),
                                createAutoPoint(2.8, 1, 10, RobotState.INTAKE, 2.2f),
                                createAutoPoint(2.8, 1, 10, RobotState.PODIUM),
                                createAutoPoint(8.5, 0.2, -10, RobotState.INTAKE, 2.4f),
                                createAutoPoint(2.8, 0.75, 10, RobotState.INTAKE, 2.2f),
                                createAutoPoint(2.8, 0.75, 10, RobotState.PODIUM),

                };
                public static AutoPoint[] steal_side_red = {
                                createAutoPoint(2.9, 4.8, 0, RobotState.DEFLECT),
                                createAutoPoint(9.3, 5.2, 0, RobotState.DEFLECT),
                                createAutoPoint(9.3, 4, -65, RobotState.DEFLECT, 1),
                                createAutoPoint(9.3, 3, -90, RobotState.INTAKE, 1),
                                createAutoPoint(4.7, 0.6, 10, RobotState.INTAKE, 1.8f),
                                createAutoPoint(4.7, 0.6, 10, RobotState.PODIUM),
                                createAutoPoint(9.5, 1.8, 10, RobotState.INTAKE, 2),
                                createAutoPoint(4.7, 0.6, 10, RobotState.INTAKE, 1.8f),
                                createAutoPoint(4.7, 0.6, 10, RobotState.PODIUM),
                };

                public static AutoPoint[] steal_side_blue = {
                                createAutoPoint(2.9, -4.8, 0, RobotState.DEFLECT),
                                createAutoPoint(9.3, -5.2, 0, RobotState.DEFLECT),
                                createAutoPoint(9.3, -4, 65, RobotState.DEFLECT, 1),
                                createAutoPoint(9.3, -3, 90, RobotState.INTAKE, 1),
                                createAutoPoint(4.7, -0.6, -10, RobotState.INTAKE, 1.8f),
                                createAutoPoint(4.7, -0.6, -10, RobotState.PODIUM),
                                createAutoPoint(9.5, -1.8, -10, RobotState.INTAKE, 2),
                                createAutoPoint(4.7, -0.6, -10, RobotState.INTAKE, 1.8f),
                                createAutoPoint(4.7, -0.6, -10, RobotState.PODIUM),

                };

                public static AutoPoint[] side_red_one = {
                        createAutoPoint(0.5, 0.5, -60, RobotState.TRAVEL),
                        createAutoPoint(0.5, 0.5, -60, RobotState.SUBWOOFER),
                        createAutoPoint(0.5, -0.3, 0, RobotState.TRAVEL),
                        createAutoPoint(8.5, -0.3, 0, RobotState.INTAKE),

                };

                public static AutoPoint[] side_blue_one = {
                        createAutoPoint(0.5, -0.5, 60, RobotState.TRAVEL),
                        createAutoPoint(0.5, -0.5, 60, RobotState.SUBWOOFER),

                };

                public static AutoPoint createAutoPoint(double x, double y, double degree, RobotState robotState) {
                        return new AutoPoint(
                                        new Pose2d(new Translation2d(x, y), new Rotation2d(Math.toRadians(degree))),
                                        robotState);
                }

                public static AutoPoint createAutoPoint(double x, double y, double degree, RobotState robotState,
                                float maxVel) {
                        return new AutoPoint(
                                        new Pose2d(new Translation2d(x, y), new Rotation2d(Math.toRadians(degree))),
                                        robotState, maxVel);
                }
        }

        public static final class DriveConstants {

                public static final double MAX_VELOCITY_METERS_PER_SECOND = 2;//2
                public static final double MAX_OMEGA_RADIANS = 1.5;//1.5
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
                public static final double FRONT_LEFT_MODULE_STEER_OFFSET = -Math.toRadians(206.27929687499997);//204.25781249999991
                // public static final double FRONT_LEFT_MODULE_STEER_OFFSET =
                // -Math.toRadians(0);%
                // Front Right
                public static final int FRONT_RIGHT_MODULE_DRIVE_MOTOR = 17;
                public static final int FRONT_RIGHT_MODULE_STEER_MOTOR = 61;
                public static final int FRONT_RIGHT_MODULE_STEER_ENCODER = 9;
                public static final double FRONT_RIGHT_MODULE_STEER_OFFSET = -Math.toRadians(207.42187500000003);// was 206.80664062500003
                                                                                                                 // 207.861328125
                // public static final double FRONT_RIGHT_MODULE_STEER_OFFSET =
                // -Math.toRadians(0);

                // Back Left
                public static final int BACK_LEFT_MODULE_DRIVE_MOTOR = 57;
                public static final int BACK_LEFT_MODULE_STEER_MOTOR = 2;
                public static final int BACK_LEFT_MODULE_STEER_ENCODER = 0;
                public static final double BACK_LEFT_MODULE_STEER_OFFSET = -Math.toRadians(330.908203125);//332.3982238769531
                // public static final double BACK_LEFT_MODULE_STEER_OFFSET =
                // -Math.toRadians(0);

                // Back Right
                public static final int BACK_RIGHT_MODULE_DRIVE_MOTOR = 33;//55
                public static final int BACK_RIGHT_MODULE_STEER_MOTOR = 18;
                public static final int BACK_RIGHT_MODULE_STEER_ENCODER = 29;
                public static final double BACK_RIGHT_MODULE_STEER_OFFSET = -Math.toRadians(129.46289062499997);//132.5390625000003
                // public static final double BACK_RIGHT_MODULE_STEER_OFFSET =
                // -Math.toRadians(0);

                private static final double driveGearRatio = (6.75 / 1.0);
                private static final double wheelCircumference = 0.116 * Math.PI;
                public static final double ticksPerMeter = driveGearRatio * 2048 / wheelCircumference;

                public static final double RotationPID_Kp = 0.1;
                public static final double RotationPID_Ki = 0.0002;
                public static final double RotationPID_Kd = 0.0001;
                public static final double ticksPerMeterFactor = 5 / 6.2;
        }

        // lime constants:
        public static final double tyTolerance = 0.5;//0.5
        public static final double wantedTY=19.0;//16,2
        public static final double distanceKp = 0.22;

        public static final float INF = 100000;

        public static final float maxVelAuto = 2.7f; // m/s
        public static final float maxOmegaAuto = 1.3f;
        
}