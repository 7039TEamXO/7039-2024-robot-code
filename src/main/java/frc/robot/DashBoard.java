package frc.robot;

import edu.wpi.first.cscore.HttpCamera;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.subsystems.Intake.Intake;
import frc.robot.subsystems.Shooter.Shooter;

public class DashBoard {
    private static HttpCamera limelightcamera = new HttpCamera("limelight", "http://10.70.39.11:5800");
    private static ShuffleboardTab driver = Shuffleboard.getTab("Driver");
    public static ShuffleboardTab data = Shuffleboard.getTab("Data");
    private static String m_autoSelected = "";
    private final static SendableChooser<String> m_chooser = new SendableChooser<>();
    private static GenericEntry auto_time_delay_input = driver.add("Auto delay", 0).withPosition(6, 0).withSize(2, 1).getEntry();

    public static void init() {
        m_chooser.setDefaultOption("DONT_MOVE", "DONT_MOVE");
        m_chooser.addOption("ONE_METER", "ONE_METER");
        m_chooser.addOption("MIDDLE_ONE", "MIDDLE_ONE");
        m_chooser.addOption("MIDDLE_THREE", "MIDDLE_THREE");
        m_chooser.addOption("RIGHT_ONE", "RIGHT_ONE");
        m_chooser.addOption("LEFT_ONE", "LEFT_ONE");
        driver.add("Auto choices", m_chooser).withPosition(0, 0).withSize(2, 1);
        driver.addBoolean("Is Game Piece In", () -> Intake.isGamePieceIn()).withPosition(2, 0).withSize(2, 1);
        driver.addString("State", () -> Robot.robotState.name()).withPosition(4, 0).withSize(2, 1);
        driver.add("LimeLight Camera", limelightcamera).withPosition(1, 1).withSize(3, 5);
        data.addNumber("Shooter Vel (M)", () -> Shooter.getShooterMasterVelocity()).withPosition(0, 1).withSize(2, 1);
        data.addNumber("Shooter Curr (M)", () -> Shooter.getShooterMasterCurrent()).withPosition(2, 1).withSize(2, 1);
        data.addNumber("Shooter Vel (S)", () -> Shooter.getShooterSlaveVelocity()).withPosition(4, 1).withSize(2, 1);
        data.addNumber("Shooter Curr (S)", () -> Shooter.getShooterSlaveCurrent()).withPosition(6, 1).withSize(2, 1);

    }

    public static Autos getSelected() {
        if (m_chooser.getSelected() != null) {
            m_autoSelected = m_chooser.getSelected();
        }
        switch (m_autoSelected) {
            case "DONT_MOVE":
                return Autos.DONT_MOVE;
            case "ONE_METER":
                return Autos.ONE_METER;
            case "MIDDLE_ONE":
                return Autos.MIDDLE_ONE;
            case "MIDDLE_THREE":
                return Autos.MIDDLE_THREE;
            case "RIGHT_ONE":
                return Autos.RIGHT_ONE;
            case "LEFT_ONE":
                return Autos.LEFT_ONE;
            default:
                return null;
        }
    }

    public static double getAutoTimeDelay(){
        return auto_time_delay_input.getDouble(0) * 1000;
    }
}
