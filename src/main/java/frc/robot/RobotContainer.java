// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.DriveConstants;
import frc.robot.commands.DriveCommands.DefaultDriveCommand;
import frc.robot.subsystems.DrivetrainSubsystem;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {

  private SendableChooser<Command> m_autonomous_chooser = new SendableChooser<>();

  // SUBSYSTEMS
  public DrivetrainSubsystem m_drivetrainSubsystem = new DrivetrainSubsystem();

  // COMMANDS

  // Autonomous Commands

  // Triggers
  // close floor intake when arm on the other side of the frame

  // CONTROLLERS
  private final PS4Controller m_driverController = new PS4Controller(0);
  // private final PS4Controller m_operatorController = new PS4Controller(1);

  private SlewRateLimiter joystickRateLimiterY = new SlewRateLimiter(3); // 2.9, 5.5
  private SlewRateLimiter joystickRateLimiterX = new SlewRateLimiter(3); // 2.9, 5.5 

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {

    // Configure the trigger bindings
    // configureBindings();
  }

  public void configureBindings() {

    /// rated drive command ///
    m_drivetrainSubsystem.setDefaultCommand(new DefaultDriveCommand(
        m_drivetrainSubsystem,
        () -> (joystickRateLimiterY.calculate(modifyAxis(-m_driverController.getLeftY()))
            * DriveConstants.MAX_VELOCITY_METERS_PER_SECOND),
        () -> (joystickRateLimiterX.calculate(modifyAxis(-m_driverController.getLeftX()))
            * DriveConstants.MAX_VELOCITY_METERS_PER_SECOND),
        () -> (modifyAxis(-m_driverController.getRightX())
            * DrivetrainSubsystem.MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND)));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return m_autonomous_chooser.getSelected();
  }

  private static double deadband(double value, double deadband) {
    if (Math.abs(value) > deadband) {
      if (value > 0.0) {
        return (value - deadband) / (1.0 - deadband);
      } else {
        return (value + deadband) / (1.0 - deadband);
      }
    } else {
      return 0.0;
    }
  }

  private static double modifyAxis(double joystick_input) {
    double value = deadband(joystick_input, DriveConstants.DRIVE_STICK_DEADBAND);
    double scaled_input = 1 - (1 - Math.abs(value)) * (1 - DriveConstants.DRIVE_MODIFT_AXIS_K);
    return value * scaled_input;
  }
 

}
