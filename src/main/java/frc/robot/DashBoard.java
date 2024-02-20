package frc.robot;

import edu.wpi.first.cscore.HttpCamera;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.auto.Autos;
import frc.robot.subsystems.Intake.Intake;
import frc.robot.subsystems.Shooter.Shooter;

public class DashBoard {
    private static HttpCamera limelightcamera = new HttpCamera("limelight", "http://10.70.39.11:5800");
    private static ShuffleboardTab driver = Shuffleboard.getTab("Driver");
    public static ShuffleboardTab data = Shuffleboard.getTab("Data");
    private static String m_autoSelected = "";
    private final static SendableChooser<String> m_chooser = new SendableChooser<>();
    private static GenericEntry auto_time_delay_input = driver.add("Auto delay", 0).withPosition(9, 0).withSize(3, 2)
            .getEntry();

    public static void init() {
        m_chooser.setDefaultOption("DONT_MOVE", "DONT_MOVE");
        m_chooser.addOption("MIDDLE_THREE", "MIDDLE_THREE");
        m_chooser.addOption("RIGHT_ONE", "RIGHT_ONE");
        m_chooser.addOption("LEFT_ONE", "LEFT_ONE");
        m_chooser.addOption("FEEDER_RED", "FEEDER_RED");
        m_chooser.addOption("FEEDER_BLUE", "FEEDER_BLUE");
        driver.add("Auto choices", m_chooser).withPosition(0, 0).withSize(3, 2);
        driver.addBoolean("Is Game Piece In", () -> Intake.isGamePieceIn()).withPosition(3, 0).withSize(3, 2);
        driver.addString("State", () -> Robot.robotState.name()).withPosition(12, 0).withSize(3, 2);
        driver.add("LimeLight Camera", limelightcamera).withPosition(0, 2).withSize(3, 2);
        data.addNumber("Shooter Vel (M)", () -> Shooter.getShooterMasterVelocity()).withPosition(0, 1).withSize(2, 1);
        data.addNumber("Shooter Curr (M)", () -> Shooter.getShooterMasterCurrent()).withPosition(2, 1).withSize(2, 1);
        data.addNumber("Shooter Vel (S)", () -> Shooter.getShooterSlaveVelocity()).withPosition(4, 1).withSize(2, 1);
        data.addNumber("Shooter Curr (S)", () -> Shooter.getShooterSlaveCurrent()).withPosition(6, 1).withSize(2, 1);
        driver.addBoolean("Ready to shoot", () -> LimeLight.isReadyToShoot()).withPosition(6, 0).withSize(3, 2);
    }

    public static Autos getSelected() {
        if (m_chooser.getSelected() != null) {
            m_autoSelected = m_chooser.getSelected();
        }
        switch (m_autoSelected) {
            case "DONT_MOVE":
                return Autos.DONT_MOVE;
            case "MIDDLE_THREE":
                return Autos.MIDDLE_THREE;
            case "RIGHT_ONE":
                return Autos.RIGHT_ONE;
            case "LEFT_ONE":
                return Autos.LEFT_ONE;
            case "FEEDER_RED":
                return Autos.FEEDER_RED;
            default:
                return null;
        }
    }

    public static double getAutoTimeDelay() {
        return auto_time_delay_input.getDouble(0);
    }
}
