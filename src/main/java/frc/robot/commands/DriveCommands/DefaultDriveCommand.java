package frc.robot.commands.DriveCommands;

import frc.robot.LimeLight;
import frc.robot.Constants.DriveConstants;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.SubSystemManager;
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
        double translationXPercent = translationXSupplier.getAsDouble();
        double translationYPercent = translationYSupplier.getAsDouble();
        double rotationPercent = (rotationSupplier.getAsDouble()
                + (SubSystemManager.joyPs4Controller.getR2Button() ? (-LimeLight.getTx() * 0.01) : 0)) * -0.4;
        drivetrain.drive(
                ChassisSpeeds.fromFieldRelativeSpeeds(
                        translationXPercent * DriveConstants.MAX_VELOCITY_METERS_PER_SECOND,
                        translationYPercent * DriveConstants.MAX_VELOCITY_METERS_PER_SECOND,
                        rotationPercent * DrivetrainSubsystem.MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND,
                        drivetrain.getRotation()));
    }

    @Override
    public void end(boolean interrupted) {
        // Stop the drivetrain
        drivetrain.drive(new ChassisSpeeds(0.0, 0.0, 0.0));
    }
}
