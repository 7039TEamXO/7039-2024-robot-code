// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.DriveConstants;
import frc.robot.commands.DriveCommands.DefaultDriveCommand;
import frc.robot.subsystems.DrivetrainSubsystem;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.MjpegServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

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

  // The robot's subsystems and commands are defined here...
  // private UsbCamera usbCamera = new UsbCamera("USB Camera 0", 0);
  // private MjpegServer mjpegServer = new MjpegServer("serve_USB Camera 0",
  // 1181);
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

  private SlewRateLimiter joystickRateLimiterY = new SlewRateLimiter(2.9); // 2.9
  private SlewRateLimiter joystickRateLimiterX = new SlewRateLimiter(2.9); // 2.9

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // cameraSetup();
  
    // Configure the trigger bindings
    configureBindings();
  }

  private void configureBindings() {

    // DRIVER Controller //
    JoystickButton DRIVER_SQUARE = new JoystickButton(m_driverController, PS4Controller.Button.kSquare.value);
    JoystickButton DRIVER_CIRCLE = new JoystickButton(m_driverController, PS4Controller.Button.kCircle.value);
    JoystickButton DRIVER_CROSS = new JoystickButton(m_driverController, PS4Controller.Button.kCross.value);
    JoystickButton DRIVER_TRIANGLE = new JoystickButton(m_driverController, PS4Controller.Button.kTriangle.value);
    JoystickButton DRIVER_R1_BUMPER = new JoystickButton(m_driverController, PS4Controller.Button.kR1.value);
    JoystickButton DRIVER_R3_STICK = new JoystickButton(m_driverController, PS4Controller.Button.kR3.value);
    JoystickButton DRIVER_L1_BUMPER = new JoystickButton(m_driverController, PS4Controller.Button.kL1.value);
    JoystickButton DRIVER_L3_STICK = new JoystickButton(m_driverController, PS4Controller.Button.kL3.value);
    JoystickButton DRIVER_SHARE = new JoystickButton(m_driverController, PS4Controller.Button.kShare.value);
    JoystickButton DRIVER_OPTIONS = new JoystickButton(m_driverController, PS4Controller.Button.kOptions.value);
    Trigger DRIVER_POV_UP = new Trigger(() -> getPS4POV(m_driverController) == 0);
    Trigger DRIVER_POV_RIGHT = new Trigger(() -> getPS4POV(m_driverController) == 90);
    Trigger DRIVER_POV_DOWN = new Trigger(() -> getPS4POV(m_driverController) == 180);
    Trigger DRIVER_POV_LEFT = new Trigger(() -> getPS4POV(m_driverController) == 270);
    Trigger DRIVER_LEFT_TRIGGER = new Trigger(() -> m_driverController.getL2Axis() > 0.1);
    Trigger DRIVER_RIGHT_TRIGGER = new Trigger(() -> m_driverController.getR2Axis() > 0.1);

    // OPERATOR Controller //
    // JoystickButton OPERATOR_SQUARE = new JoystickButton(m_operatorController,
    // PS4Controller.Button.kSquare.value);
    // JoystickButton OPERATOR_CIRCLE = new JoystickButton(m_operatorController,
    // PS4Controller.Button.kCircle.value);
    // JoystickButton OPERATOR_CROSS = new JoystickButton(m_operatorController,
    // PS4Controller.Button.kCross.value);
    // JoystickButton OPERATOR_TRIANGLE = new JoystickButton(m_operatorController,
    // PS4Controller.Button.kTriangle.value);
    // JoystickButton OPERATOR_R1_BUMPER = new JoystickButton(m_operatorController,
    // PS4Controller.Button.kR1.value);
    // JoystickButton OPERATOR_R3_STICK = new JoystickButton(m_operatorController,
    // PS4Controller.Button.kR3.value);
    // JoystickButton OPERATOR_L1_BUMPER = new JoystickButton(m_operatorController,
    // PS4Controller.Button.kL1.value);
    // JoystickButton OPERATOR_L3_STICK = new JoystickButton(m_operatorController,
    // PS4Controller.Button.kL3.value);
    // JoystickButton OPERATOR_SHARE = new JoystickButton(m_operatorController,
    // PS4Controller.Button.kShare.value);
    // JoystickButton OPERATOR_OPTIONS = new JoystickButton(m_operatorController,
    // PS4Controller.Button.kOptions.value);
    // Trigger OPERATOR_POV_UP = new Trigger(() -> getPS4POV(m_operatorController)
    // == 0);
    // Trigger OPERATOR_POV_RIGHT = new Trigger(() ->
    // getPS4POV(m_operatorController) == 90);
    // Trigger OPERATOR_POV_DOWN = new Trigger(() -> getPS4POV(m_operatorController)
    // == 180);
    // Trigger OPERATOR_POV_LEFT = new Trigger(() -> getPS4POV(m_operatorController)
    // == 270);
    // Trigger OPERATOR_LEFT_TRIGGER = new Trigger(() ->
    // m_driverController.getL2Axis() > 0.1);
    // Trigger OPERATOR_RIGHT_TRIGGER = new Trigger(() ->
    // m_driverController.getR2Axis() > 0.1);

    // COMMAND TRIGGER //
   

    // OPERATOR Buttons //
    // OPERATOR_R1_BUMPER.onTrue(m_startArmIntakeCommand);
    // OPERATOR_R3_STICK.onTrue(m_openArmIntakeCommand);
    // OPERATOR_L1_BUMPER.onTrue(m_stopArmIntakeCommand);
    // OPERATOR_L3_STICK.onTrue(m_closeArmIntakeCommand);
    // OPERATOR_CIRCLE.onTrue(m_stopFloorIntakeCommand);
    // OPERATOR_SQUARE.onTrue(m_startFloorIntakeCommand);
    // OPERATOR_CROSS.onTrue(m_openFloorIntakeCommand);
    // OPERATOR_TRIANGLE.onTrue(m_closeFloorIntakeCommand);
    // OPERATOR_POV_UP.onTrue(m_armCommandMax);
    // OPERATOR_POV_RIGHT.onTrue(m_armCommandBack);
    // OPERATOR_POV_DOWN.onTrue(m_armCommandMin);
    // OPERATOR_POV_LEFT.onTrue(m_armCommandFront);
    // OPERATOR_SHARE.onTrue(m_oppositeFloorIntakeCommand);
    // OPERATOR_OPTIONS.onTrue(m_oppositeArmIntakeCommand);

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

  private int getPS4POV(PS4Controller controller) {
    return controller.getPOV();
  }

  /*
   * public void cameraSetup() {
   * // USB CAMERA //
   * try {
   * mjpegServer.setSource(usbCamera);
   * usbCamera = CameraServer.startAutomaticCapture();
   * usbCamera.setPixelFormat(PixelFormat.kMJPEG);
   * usbCamera.setResolution(320, 240);
   * usbCamera.setFPS(30);
   * usbCamera.setWhiteBalanceAuto();
   * usbCamera.setExposureAuto();
   * } catch (Exception e) {
   * System.out.println("--------------- CameraSetup ERROR ---------------");
   * }
   * Shuffleboard.getTab("Driver").add("Driver Camera", usbCamera).withSize(4,
   * 4).withPosition(6, 0);
   * }
   */

}
