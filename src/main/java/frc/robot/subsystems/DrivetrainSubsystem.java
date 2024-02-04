package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.Pigeon2;
import com.kauailabs.navx.frc.AHRS;
import com.swervedrivespecialties.swervelib.MkSwerveModuleBuilder;
import com.swervedrivespecialties.swervelib.MotorType;
import com.swervedrivespecialties.swervelib.SdsModuleConfigurations;
import com.swervedrivespecialties.swervelib.SwerveModule;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DrivetrainSubsystem extends SubsystemBase {
        private static final double MAX_VOLTAGE = 12.0;
        public static final double MAX_VELOCITY_METERS_PER_SECOND = 4.14528;
        public static final double MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND = MAX_VELOCITY_METERS_PER_SECOND /
                        Math.hypot(Constants.DriveConstants.DRIVETRAIN_TRACKWIDTH_METERS / 2.0,
                                        Constants.DriveConstants.DRIVETRAIN_WHEELBASE_METERS / 2.0);

        private final SwerveModule frontLeftModule;
        private final SwerveModule frontRightModule;
        private final SwerveModule backLeftModule;
        private final SwerveModule backRightModule;

        private final AHRS gyroscope = new AHRS(Constants.DriveConstants.DRIVETRAIN_NAVX_ID, (byte) 200);

        private final SwerveDriveKinematics kinematics = new SwerveDriveKinematics(
                        new Translation2d(Constants.DriveConstants.DRIVETRAIN_TRACKWIDTH_METERS / 2.0,
                                        Constants.DriveConstants.DRIVETRAIN_WHEELBASE_METERS / 2.0),
                        new Translation2d(Constants.DriveConstants.DRIVETRAIN_TRACKWIDTH_METERS / 2.0,
                                        -Constants.DriveConstants.DRIVETRAIN_WHEELBASE_METERS / 2.0),
                        new Translation2d(-Constants.DriveConstants.DRIVETRAIN_TRACKWIDTH_METERS / 2.0,
                                        Constants.DriveConstants.DRIVETRAIN_WHEELBASE_METERS / 2.0),
                        new Translation2d(-Constants.DriveConstants.DRIVETRAIN_TRACKWIDTH_METERS / 2.0,
                                        -Constants.DriveConstants.DRIVETRAIN_WHEELBASE_METERS / 2.0));
        private final SwerveDriveOdometry odometry;

        private ChassisSpeeds chassisSpeeds = new ChassisSpeeds(0.0, 0.0, 0.0);

        public DrivetrainSubsystem() {
                ShuffleboardTab shuffleboardTab = Shuffleboard.getTab("Drivetrain");
                ShuffleboardTab gyroShuffleboardTab = Shuffleboard.getTab("Gyro");
                frontLeftModule = new MkSwerveModuleBuilder()
                                .withLayout(shuffleboardTab.getLayout("Front Left Module", BuiltInLayouts.kList)
                                                .withSize(2, 4)
                                                .withPosition(0, 0))
                                .withGearRatio(SdsModuleConfigurations.MK4_L2)
                                .withDriveMotor(MotorType.FALCON,
                                                Constants.DriveConstants.FRONT_LEFT_MODULE_DRIVE_MOTOR)
                                .withSteerMotor(MotorType.FALCON,
                                                Constants.DriveConstants.FRONT_LEFT_MODULE_STEER_MOTOR)
                                .withSteerEncoderPort(Constants.DriveConstants.FRONT_LEFT_MODULE_STEER_ENCODER)
                                .withSteerOffset(Constants.DriveConstants.FRONT_LEFT_MODULE_STEER_OFFSET)
                                .build();

                frontRightModule = new MkSwerveModuleBuilder()
                                .withLayout(shuffleboardTab.getLayout("Front Right Module", BuiltInLayouts.kList)
                                                .withSize(2, 4)
                                                .withPosition(2, 0))
                                .withGearRatio(SdsModuleConfigurations.MK4_L2)
                                .withDriveMotor(MotorType.FALCON,
                                                Constants.DriveConstants.FRONT_RIGHT_MODULE_DRIVE_MOTOR)
                                .withSteerMotor(MotorType.FALCON,
                                                Constants.DriveConstants.FRONT_RIGHT_MODULE_STEER_MOTOR)
                                .withSteerEncoderPort(Constants.DriveConstants.FRONT_RIGHT_MODULE_STEER_ENCODER)
                                .withSteerOffset(Constants.DriveConstants.FRONT_RIGHT_MODULE_STEER_OFFSET)
                                .build();

                backLeftModule = new MkSwerveModuleBuilder()
                                .withLayout(shuffleboardTab.getLayout("Back Left Module", BuiltInLayouts.kList)
                                                .withSize(2, 4)
                                                .withPosition(4, 0))
                                .withGearRatio(SdsModuleConfigurations.MK4_L2)
                                .withDriveMotor(MotorType.FALCON, Constants.DriveConstants.BACK_LEFT_MODULE_DRIVE_MOTOR)
                                .withSteerMotor(MotorType.FALCON, Constants.DriveConstants.BACK_LEFT_MODULE_STEER_MOTOR)
                                .withSteerEncoderPort(Constants.DriveConstants.BACK_LEFT_MODULE_STEER_ENCODER)
                                .withSteerOffset(Constants.DriveConstants.BACK_LEFT_MODULE_STEER_OFFSET)
                                .build();

                backRightModule = new MkSwerveModuleBuilder()
                                .withLayout(shuffleboardTab.getLayout("Back Right Module", BuiltInLayouts.kList)
                                                .withSize(2, 4)
                                                .withPosition(6, 0))
                                .withGearRatio(SdsModuleConfigurations.MK4_L2)
                                .withDriveMotor(MotorType.FALCON,
                                                Constants.DriveConstants.BACK_RIGHT_MODULE_DRIVE_MOTOR)
                                .withSteerMotor(MotorType.FALCON,
                                                Constants.DriveConstants.BACK_RIGHT_MODULE_STEER_MOTOR)
                                .withSteerEncoderPort(Constants.DriveConstants.BACK_RIGHT_MODULE_STEER_ENCODER)
                                .withSteerOffset(Constants.DriveConstants.BACK_RIGHT_MODULE_STEER_OFFSET)
                                .build();

                odometry = new SwerveDriveOdometry(
                                kinematics,
                                Rotation2d.fromDegrees(-gyroscope.getAngle()),
                                new SwerveModulePosition[] { frontLeftModule.getPosition(),
                                                frontRightModule.getPosition(), backLeftModule.getPosition(),
                                                backRightModule.getPosition() });
                gyroShuffleboardTab.addNumber("Gyroscope Yaw", () -> gyroscope.getYaw());
                gyroShuffleboardTab.addNumber("Gyroscope Angle", () -> getRotation().getDegrees());
                gyroShuffleboardTab.addNumber("Pose X", () -> odometry.getPoseMeters().getX());
                gyroShuffleboardTab.addNumber("Pose Y", () -> odometry.getPoseMeters().getY());
        }

        public void zeroGyroscope() {
                odometry.resetPosition(
                                Rotation2d.fromDegrees(-gyroscope.getAngle()),
                                new SwerveModulePosition[] { frontLeftModule.getPosition(),
                                                frontRightModule.getPosition(), backLeftModule.getPosition(),
                                                backRightModule.getPosition() },
                                new Pose2d(odometry.getPoseMeters().getTranslation(), Rotation2d.fromDegrees(0.0)));
        }

        public Rotation2d getRotation() {
                return odometry.getPoseMeters().getRotation();
        }

        public void drive(ChassisSpeeds chassisSpeeds) {
                this.chassisSpeeds = chassisSpeeds;
        }

        @Override
        public void periodic() {

                odometry.update(
                                Rotation2d.fromDegrees(-gyroscope.getAngle()),
                                new SwerveModulePosition[] { frontLeftModule.getPosition(),
                                                frontRightModule.getPosition(), backLeftModule.getPosition(),
                                                backRightModule.getPosition() });

                SwerveModuleState[] states = kinematics.toSwerveModuleStates(chassisSpeeds);

                frontLeftModule.set(states[0].speedMetersPerSecond / MAX_VELOCITY_METERS_PER_SECOND * MAX_VOLTAGE,
                                states[0].angle.getRadians());
                frontRightModule.set(states[1].speedMetersPerSecond / MAX_VELOCITY_METERS_PER_SECOND * MAX_VOLTAGE,
                                states[1].angle.getRadians());
                backLeftModule.set(states[2].speedMetersPerSecond / MAX_VELOCITY_METERS_PER_SECOND * MAX_VOLTAGE,
                                states[2].angle.getRadians());
                backRightModule.set(states[3].speedMetersPerSecond / MAX_VELOCITY_METERS_PER_SECOND * MAX_VOLTAGE,
                                states[3].angle.getRadians());
        }
}