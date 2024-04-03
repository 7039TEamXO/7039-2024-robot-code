package frc.robot.commands.DriveCommands;

import frc.robot.Constants;
import frc.robot.LimeLight;
import frc.robot.Constants.DriveConstants;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.SubSystemManager;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;

import java.util.function.DoubleSupplier;

public class DefaultDriveCommand extends Command {
    private final DrivetrainSubsystem drivetrain;
    private final DoubleSupplier translationXSupplier;
    private final DoubleSupplier translationYSupplier;
    private final DoubleSupplier rotationSupplier;

    public DefaultDriveCommand(
            DrivetrainSubsystem drivetrain,
            DoubleSupplier translationXSupplier,
            DoubleSupplier translationYSupplier,
            DoubleSupplier rotationSupplier) {
        this.drivetrain = drivetrain;
        this.translationXSupplier = translationXSupplier;
        this.translationYSupplier = translationYSupplier;
        this.rotationSupplier = rotationSupplier;

        addRequirements(drivetrain);
    }

    @Override
    public void execute() {
        boolean driverAssist = SubSystemManager.joyPs4Controller.getR2Button();
        double translationXPercent = translationXSupplier.getAsDouble();
        double translationYPercent = translationYSupplier.getAsDouble();
        double rotationPercent = (rotationSupplier.getAsDouble()) * -0.4;
        drivetrain.drive(
                ChassisSpeeds.fromFieldRelativeSpeeds(
                        translationXPercent * DriveConstants.MAX_VELOCITY_METERS_PER_SECOND,
                        translationYPercent * DriveConstants.MAX_VELOCITY_METERS_PER_SECOND,
                        rotationPercent * DriveConstants.MAX_OMEGA_RADIANS,
                        drivetrain.getRotation())
                        .plus(ChassisSpeeds.fromFieldRelativeSpeeds(driverAssist && LimeLight.getTy() != 0
                                ? (LimeLight.getTy() + Constants.wantedTY) * Constants.distanceKp
                                : 0, 0, (driverAssist ? (LimeLight.getTx() * 0.04) : 0), Rotation2d.fromDegrees(0))));
    }

    @Override
    public void end(boolean interrupted) {
        // Stop the drivetrain
        drivetrain.drive(new ChassisSpeeds(0.0, 0.0, 0.0));
    }
}
