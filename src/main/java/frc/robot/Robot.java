// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.ArrayList;
import java.util.List;


import edu.wpi.first.hal.AllianceStationID;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.auto.AutoPoint;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.RobotState;
import frc.robot.subsystems.SubSystemManager;
import frc.robot.subsystems.Conveyor.Conveyor;
import frc.robot.subsystems.Intake.Intake;
import frc.robot.subsystems.Shooter.Shooter;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the
 * name of this class or
 * the package after creating this project, you must also update the
 * build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;
  private RobotContainer m_robotContainer;
  private float driveKp = 1.95f;
  private float headingKp = 1.7f;
  public static RobotState robotState = RobotState.TRAVEL;
  Alliance alliance = Alliance.Red;

  public static Autos currentAuto = Autos.DONT_MOVE;
  // This will load the file "Example Path.path" and generate it with a max
  // velocity of 4 m/s and a max acceleration of 3 m/s^2

  // This trajectory can then be passed to a path follower such as a
  // PPSwerveControllerCommand
  // Or the path can be sampled at a given point in time for custom path following

  // Sample the state of the path at 1.2 seconds

  // private final DrivetrainSubsystem m_robotContainer.m_drivetrainSubsystem =
  // new DrivetrainSubsystem();

  // Slew rate limiters to make joystick inputs more gentle; 1/3 sec from 0 to 1.

  // The Ramsete Controller to follow the trajectory.
  private final RamseteController m_ramseteController = new RamseteController();

  // The timer to use during the autonomous period.
  public static Timer m_timer;
  /**
   * This function is run when the robot is first started up and should be used
   * for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    m_timer = new Timer();
    // Instantiate our RobotContainer. This will perform all our button bindings,
    // and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();
    DashBoard.init();
    Shooter.init();
    Intake.init();
    Conveyor.init();
  }
  
  /**
   * This function is called every 20 ms, no matter the mode. Use this for items
   * like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler. This is responsible for polling buttons, adding
    // newly-scheduled
    // commands, running already-scheduled commands, removing finished or
    // interrupted commands,
    // and running subsystem periodic() methods. This must be called from the
    // robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();

  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
    currentAuto = DashBoard.getAuto(); 
  }

  /**
   * This autonomous runs the autonomous command selected by your
   * {@link RobotContainer} class.
   */
  private AutoPoint[] points;

  @Override
  public void autonomousInit() {
    points = currentAuto.getPoints();
    GlobalData.auto = true;
    // Initialize the timer.
    m_timer.start();
    // * points = realAutoPoints
    // Reset the drivetrain's odometry to the starting pose of the trajectory.
    m_robotContainer.m_drivetrainSubsystem.resetOdometry(points[0].getWantedPose());
  }
  
  boolean firstTime = true;
  private int currentPointIndex = 1;

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    // Update odometry.
    m_robotContainer.m_drivetrainSubsystem.updateOdometry();
    double armTime = 0;
    double placeTime = 0;
    robotState = RobotState.TRAVEL;
   //here goes first time logic
    firstTime = false;

    if (currentPointIndex < points.length) {

      // Get the reference chassis speeds from the Ramsete controller.
      var error = points[currentPointIndex].getWantedPose().getTranslation()
          .minus(m_robotContainer.m_drivetrainSubsystem.getPose().getTranslation());
      var angleError = points[currentPointIndex].getWantedPose().getRotation()
          .minus(m_robotContainer.m_drivetrainSubsystem.getPose().getRotation());
      var refChassisSpeeds = new ChassisSpeeds();
      refChassisSpeeds.vxMetersPerSecond = Math.min(error.getX() * driveKp, 1.5);
      refChassisSpeeds.vyMetersPerSecond = Math.min(error.getY() * driveKp, 1.5);
      refChassisSpeeds.omegaRadiansPerSecond = (angleError.getRadians() * headingKp);
      // Set the linear and angular speeds.
      m_robotContainer.m_drivetrainSubsystem.drive(refChassisSpeeds);
      if (!points[currentPointIndex].getAction().equals(null)) {
        robotState = points[currentPointIndex].getAction();
      }
      if (error.getNorm() < Constants.AutonomousConstants.distanceToletrance
          && error.getAngle().getRadians() < Constants.AutonomousConstants.angleToletrance) {
            currentPointIndex++;
      }
    } else {
      // when finished path stop
      m_robotContainer.m_drivetrainSubsystem.stopModules();
    }
    SubSystemManager.operate();
  }

  @Override
  public void teleopInit() {
    if(GlobalData.auto){
      m_timer.reset();
      GlobalData.auto = false;
    }else{
      m_timer.start();
    }
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    robotState = SubSystemManager.getRobotStateFromWantedAndActual();
    if (SubSystemManager.joyPs4Controller.getPSButtonPressed()) {
      m_robotContainer.m_drivetrainSubsystem.zeroGyroscope();
    }
    m_robotContainer.m_drivetrainSubsystem.updateOdometry();

    SubSystemManager.operate();
    LimeLight.update();
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
  }

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {
  }

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {
  }
}
