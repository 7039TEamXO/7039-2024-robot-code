package frc.robot;

import edu.wpi.first.cscore.HttpCamera;
import edu.wpi.first.networktables.BooleanEntry;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.SuppliedValueWidget;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.auto.Autos;
import frc.robot.subsystems.Arm.Arm;
import frc.robot.subsystems.Intake.Intake;
import frc.robot.subsystems.Shooter.Shooter;

public class DashBoard {
    private static HttpCamera limelightcamera = new HttpCamera("limelight", "http://10.70.39.11:5800");
    private static ShuffleboardTab driver = Shuffleboard.getTab("Driver");
    private static ShuffleboardTab temperature = Shuffleboard.getTab("Temperature");
    public static ShuffleboardTab data = Shuffleboard.getTab("Data");
    private static String m_autoSelected = "";
    private final static SendableChooser<String> m_chooser = new SendableChooser<>();
    private static GenericEntry auto_time_delay_input = driver.add("Auto delay", 0).withPosition(11, 0).withSize(3, 3)
            .getEntry();
    private static GenericEntry ampOffset = driver.add("amp offset", 0).withPosition(0, 3).withSize(3, 3)
            .getEntry();
    public static  GenericEntry disable_ir_input = driver.add("Disable IR", false).withPosition(12, 3).withSize(3, 3).getEntry();

    public static void init() {
        Autos[] states = Autos.values();
        for (int i = 0; i < states.length; i++) {
            Autos state = states[i];
            String name_state = state.name();
            if (i == 0) {
                m_chooser.setDefaultOption(name_state, name_state);
            }
            m_chooser.addOption(name_state, name_state);
        }
        driver.add("Auto choices", m_chooser).withPosition(0, 0).withSize(5, 3);
        driver.addBoolean("Is Game Piece In", () -> Intake.isGamePieceIn()).withPosition(5, 0).withSize(3, 3);
        temperature.addNumber("temperature", () -> Intake.getTemperature()).withPosition(5, 0).withSize(3, 3);
        driver.addString("State", () -> Robot.robotState.name()).withPosition(14, 0).withSize(3, 3);
        driver.add("LimeLight Camera", limelightcamera).withPosition(17, 0).withSize(3, 3);
        data.addNumber("Shooter Vel (M)", () -> Shooter.getShooterMasterVelocity()).withPosition(0, 4).withSize(4, 3);
        data.addNumber("Shooter Curr (M)", () -> Shooter.getShooterMasterCurrent()).withPosition(4, 4).withSize(4, 3);
        data.addNumber("Shooter Vel (S)", () -> Shooter.getShooterSlaveVelocity()).withPosition(8, 4).withSize(4, 3);
        data.addNumber("Shooter Curr (S)", () -> Shooter.getShooterSlaveCurrent()).withPosition(12, 4).withSize(4, 3);
        driver.addBoolean("Ready to shoot", () -> Shooter.readyToShoot()).withPosition(8, 0).withSize(3, 3);
        driver.addBoolean("arm reached", () -> Arm.reached()).withPosition(9, 3).withSize(3, 3);

    }

    public static Autos getSelected() {
        if (m_chooser.getSelected() != null) {
            m_autoSelected = m_chooser.getSelected();
        }
        return Autos.valueOf(m_autoSelected);
    }

    public static double getAutoTimeDelay() {
        return auto_time_delay_input.getDouble(0);
    }

    public static double getAmpOffset() {
        return -ampOffset.getDouble(0);
    }
}
