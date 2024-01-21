package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import frc.robot.Constants.DriveConstants;

import com.swervedrivespecialties.swervelib.Mk4SwerveModuleHelper;
import com.swervedrivespecialties.swervelib.Mk4iSwerveModuleHelper;
import com.swervedrivespecialties.swervelib.SwerveModule;

import edu.wpi.first.math.controller.PIDController;
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
import edu.wpi.first.math.MathUtil;

public class DrivetrainSubsystem extends SubsystemBase {

        private PIDController rotationPIDController = new PIDController(DriveConstants.RotationPID_Kp,
                        DriveConstants.RotationPID_Ki,
                        DriveConstants.RotationPID_Kd);
        private double pidtolerance = DriveConstants.AlignPIDTolerance;
        private static final double MAX_VOLTAGE = 12.0;

        public static final double MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND = DriveConstants.RotationSpeedKp
                        * DriveConstants.MAX_VELOCITY_METERS_PER_SECOND /
                        Math.hypot(DriveConstants.DRIVETRAIN_TRACKWIDTH_METERS / 2.0,
                                        DriveConstants.DRIVETRAIN_WHEELBASE_METERS / 2.0);

        private final SwerveModule frontLeftModule;
        private final SwerveModule frontRightModule;
        private final SwerveModule backLeftModule;
        private final SwerveModule backRightModule;

        private final AHRS gyroscope = new AHRS(DriveConstants.DRIVETRAIN_NAVX_ID, (byte) 200);

        private final SwerveDriveKinematics kinematics = new SwerveDriveKinematics(
                        // Front Left
                        new Translation2d(DriveConstants.DRIVETRAIN_TRACKWIDTH_METERS / 2.0,
                                        DriveConstants.DRIVETRAIN_WHEELBASE_METERS / 2.0),
                        // Front Right
                        new Translation2d(DriveConstants.DRIVETRAIN_TRACKWIDTH_METERS / 2.0,
                                        -DriveConstants.DRIVETRAIN_WHEELBASE_METERS / 2.0),
                        // Back Left
                        new Translation2d(-DriveConstants.DRIVETRAIN_TRACKWIDTH_METERS / 2.0,
                                        DriveConstants.DRIVETRAIN_WHEELBASE_METERS / 2.0),
                        // Back Right
                        new Translation2d(-DriveConstants.DRIVETRAIN_TRACKWIDTH_METERS / 2.0,
                                        -DriveConstants.DRIVETRAIN_WHEELBASE_METERS / 2.0));

        private final SwerveDriveOdometry odometry;

        private ChassisSpeeds chassisSpeeds = new ChassisSpeeds(0.0, 0.0, 0.0);

        public DrivetrainSubsystem() {
                System.out.println("---------------DrivetrainSubsystem - CONSTURCTOR---------------");
                rotationPIDController.setTolerance(pidtolerance);
                ShuffleboardTab driveTrainShuffleboardTab = Shuffleboard.getTab("Drivetrain");
                ShuffleboardTab driverShuffleboardTab = Shuffleboard.getTab("Driver");

                System.out.println("---------------DrivetrainSubsystem - STARTING frontLeftModule---------------");
                frontLeftModule = Mk4SwerveModuleHelper.createFalcon500(
                                driveTrainShuffleboardTab.getLayout("Front Left Module", BuiltInLayouts.kList)
                                                .withSize(2, 4)
                                                .withPosition(0, 0),
                                Mk4SwerveModuleHelper.GearRatio.L2,
                                DriveConstants.FRONT_LEFT_MODULE_DRIVE_MOTOR,
                                DriveConstants.FRONT_LEFT_MODULE_STEER_MOTOR,
                                DriveConstants.FRONT_LEFT_MODULE_STEER_ENCODER,
                                DriveConstants.FRONT_LEFT_MODULE_STEER_OFFSET);
                System.out.println("---------------DrivetrainSubsystem - STARTING frontRightModule---------------");
                frontRightModule = Mk4SwerveModuleHelper.createFalcon500(
                                driveTrainShuffleboardTab.getLayout("Front Right Module", BuiltInLayouts.kList)
                                                .withSize(2, 4)
                                                .withPosition(2, 0),
                                Mk4SwerveModuleHelper.GearRatio.L2,
                                DriveConstants.FRONT_RIGHT_MODULE_DRIVE_MOTOR,
                                DriveConstants.FRONT_RIGHT_MODULE_STEER_MOTOR,
                                DriveConstants.FRONT_RIGHT_MODULE_STEER_ENCODER,
                                DriveConstants.FRONT_RIGHT_MODULE_STEER_OFFSET);
                System.out.println("---------------DrivetrainSubsystem - STARTING backLeftModule---------------");
                backLeftModule = Mk4SwerveModuleHelper.createFalcon500(
                                driveTrainShuffleboardTab.getLayout("Back Left Module", BuiltInLayouts.kList)
                                                .withSize(2, 4)
                                                .withPosition(4, 0),
                                Mk4SwerveModuleHelper.GearRatio.L2,
                                DriveConstants.BACK_LEFT_MODULE_DRIVE_MOTOR,
                                DriveConstants.BACK_LEFT_MODULE_STEER_MOTOR,
                                DriveConstants.BACK_LEFT_MODULE_STEER_ENCODER,
                                DriveConstants.BACK_LEFT_MODULE_STEER_OFFSET);
                System.out.println("---------------DrivetrainSubsystem - STARTING backRightModule---------------");
                backRightModule = Mk4SwerveModuleHelper.createFalcon500(
                                driveTrainShuffleboardTab.getLayout("Back Right Module", BuiltInLayouts.kList)
                                                .withSize(2, 4)
                                                .withPosition(6, 0),
                                Mk4SwerveModuleHelper.GearRatio.L2,
                                DriveConstants.BACK_RIGHT_MODULE_DRIVE_MOTOR,
                                DriveConstants.BACK_RIGHT_MODULE_STEER_MOTOR,
                                DriveConstants.BACK_RIGHT_MODULE_STEER_ENCODER,
                                DriveConstants.BACK_RIGHT_MODULE_STEER_OFFSET);

                odometry = new SwerveDriveOdometry(kinematics,
                                Rotation2d.fromDegrees(-(gyroscope.getFusedHeading())), new SwerveModulePosition[] {
                                                new SwerveModulePosition(frontLeftModule.getDriveVelocity(),
                                                                new Rotation2d(frontLeftModule.getSteerAngle())),
                                                new SwerveModulePosition(frontRightModule.getDriveVelocity(),
                                                                new Rotation2d(frontRightModule.getSteerAngle())),
                                                new SwerveModulePosition(backLeftModule.getDriveVelocity(),
                                                                new Rotation2d(backLeftModule.getSteerAngle())),
                                                new SwerveModulePosition(backRightModule.getDriveVelocity(),
                                                                new Rotation2d(backRightModule.getSteerAngle()))
                                });
                driveTrainShuffleboardTab.add("rotation PID pid", rotationPIDController);
                driveTrainShuffleboardTab.addNumber("Gyroscope Angle", () -> getRotation().getDegrees());
                driveTrainShuffleboardTab.addNumber("Pose X", () -> odometry.getPoseMeters().getX());
                driveTrainShuffleboardTab.addNumber("Pose Y", () -> getDistance());
                driverShuffleboardTab.addNumber("Pitch", () -> getPitch()).withPosition(0, 3);
                System.out.println("---------------DrivetrainSubsystem - DONE---------------");
        }

        public void zeroGyroscope() {
                odometry.resetPosition(
                                Rotation2d.fromDegrees(-gyroscope.getFusedHeading()),
                                new SwerveModulePosition[] {
                                                new SwerveModulePosition(frontLeftModule.getDriveVelocity(),
                                                                new Rotation2d(frontLeftModule.getSteerAngle())),
                                                new SwerveModulePosition(frontRightModule.getDriveVelocity(),
                                                                new Rotation2d(frontRightModule.getSteerAngle())),
                                                new SwerveModulePosition(backLeftModule.getDriveVelocity(),
                                                                new Rotation2d(backLeftModule.getSteerAngle())),
                                                new SwerveModulePosition(backRightModule.getDriveVelocity(),
                                                                new Rotation2d(backRightModule.getSteerAngle()))
                                },
                                new Pose2d(odometry.getPoseMeters().getTranslation(), Rotation2d.fromDegrees(0.0)));
        }

        public Rotation2d getRotation() {
                return (odometry.getPoseMeters().getRotation());
        }

        public double getDistance() {
                return odometry.getPoseMeters().getY();
        }

        public Pose2d getPose() {
                return odometry.getPoseMeters();
        }
        
        public void drive(ChassisSpeeds chassisSpeeds) {
                this.chassisSpeeds = chassisSpeeds;
        }

        public double GetAngle() {
                return gyroscope.getFusedHeading();
        }

        public double getPitch() {
                return gyroscope.getPitch();
        }

        @Override
        public void periodic() {
                odometry.update(Rotation2d.fromDegrees(-gyroscope.getFusedHeading()), new SwerveModulePosition[] {
                                new SwerveModulePosition(frontLeftModule.getDriveVelocity(),
                                                new Rotation2d(frontLeftModule.getSteerAngle())),
                                new SwerveModulePosition(frontRightModule.getDriveVelocity(),
                                                new Rotation2d(frontRightModule.getSteerAngle())),
                                new SwerveModulePosition(backLeftModule.getDriveVelocity(),
                                                new Rotation2d(backLeftModule.getSteerAngle())),
                                new SwerveModulePosition(backRightModule.getDriveVelocity(),
                                                new Rotation2d(backRightModule.getSteerAngle())) });

                SwerveModuleState[] states = kinematics.toSwerveModuleStates(chassisSpeeds);

                frontLeftModule.set(
                                states[0].speedMetersPerSecond / DriveConstants.MAX_VELOCITY_METERS_PER_SECOND
                                                * MAX_VOLTAGE,
                                states[0].angle.getRadians());
                frontRightModule.set(
                                states[1].speedMetersPerSecond / DriveConstants.MAX_VELOCITY_METERS_PER_SECOND
                                                * MAX_VOLTAGE,
                                states[1].angle.getRadians());
                backLeftModule.set(
                                states[2].speedMetersPerSecond / DriveConstants.MAX_VELOCITY_METERS_PER_SECOND
                                                * MAX_VOLTAGE,
                                states[2].angle.getRadians());
                backRightModule.set(
                                states[3].speedMetersPerSecond / DriveConstants.MAX_VELOCITY_METERS_PER_SECOND
                                                * MAX_VOLTAGE,
                                states[3].angle.getRadians());
        }

        public float GetNavxYaw() {
                return gyroscope.getYaw();
        }

        public boolean AlignPIDAtSetpoint() {
                return rotationPIDController.atSetpoint();
        }

        public double SnapToAngle(double angle) {
                return rotationPIDController.calculate(MathUtil.clamp(
                                rotationPIDController.calculate(GetAngle(), angle), -0.5, 0.5));
        }

        //// FUNCTIONS BELOW FOR AUTONOMUS ////
        public SwerveDriveKinematics getKinematics() {
                return kinematics;
        }

        public void resetOdometry(Pose2d pose) {
                odometry.resetPosition(
                                Rotation2d.fromDegrees(-gyroscope.getFusedHeading()), new SwerveModulePosition[] {
                                                new SwerveModulePosition(frontLeftModule.getDriveVelocity(),
                                                                new Rotation2d(frontLeftModule.getSteerAngle())),
                                                new SwerveModulePosition(frontRightModule.getDriveVelocity(),
                                                                new Rotation2d(frontRightModule.getSteerAngle())),
                                                new SwerveModulePosition(backLeftModule.getDriveVelocity(),
                                                                new Rotation2d(backLeftModule.getSteerAngle())),
                                                new SwerveModulePosition(backRightModule.getDriveVelocity(),
                                                                new Rotation2d(backRightModule.getSteerAngle())) },
                                pose);
        }

        public void updateOdometry(){
                odometry.update(Rotation2d.fromDegrees(-gyroscope.getFusedHeading()), new SwerveModulePosition[] {
                        new SwerveModulePosition(frontLeftModule.getDriveVelocity(),
                                        new Rotation2d(frontLeftModule.getSteerAngle())),
                        new SwerveModulePosition(frontRightModule.getDriveVelocity(),
                                        new Rotation2d(frontRightModule.getSteerAngle())),
                        new SwerveModulePosition(backLeftModule.getDriveVelocity(),
                                        new Rotation2d(backLeftModule.getSteerAngle())),
                        new SwerveModulePosition(backRightModule.getDriveVelocity(),
                                        new Rotation2d(backRightModule.getSteerAngle())) });
        }

        public void stopModules() {
                // also guess do something to stop modules
                frontLeftModule.set(0, 0);
                frontRightModule.set(0, 0);
                backLeftModule.set(0, 0);
                backRightModule.set(0, 0);
        }

        public void setModuleStates(SwerveModuleState[] desiredStates) {
                // or delete next line and just use desiredStates
                // SwerveModuleState[] states = kinematics.toSwerveModuleStates(chassisSpeeds);
                frontLeftModule.set(
                                desiredStates[0].speedMetersPerSecond / DriveConstants.MAX_VELOCITY_METERS_PER_SECOND
                                                * MAX_VOLTAGE,
                                desiredStates[0].angle.getRadians());
                frontRightModule.set(
                                desiredStates[1].speedMetersPerSecond / DriveConstants.MAX_VELOCITY_METERS_PER_SECOND
                                                * MAX_VOLTAGE,
                                desiredStates[1].angle.getRadians());
                backLeftModule.set(
                                desiredStates[2].speedMetersPerSecond / DriveConstants.MAX_VELOCITY_METERS_PER_SECOND
                                                * MAX_VOLTAGE,
                                desiredStates[2].angle.getRadians());
                backRightModule.set(
                                desiredStates[3].speedMetersPerSecond / DriveConstants.MAX_VELOCITY_METERS_PER_SECOND
                                                * MAX_VOLTAGE,
                                desiredStates[3].angle.getRadians());
        }
}
