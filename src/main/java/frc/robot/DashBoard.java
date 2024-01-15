package frc.robot;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.WidgetType;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DashBoard {
    private static ShuffleboardTab driver = Shuffleboard.getTab("Driver");
    private static final String kDefaultAuto = "Default";
    private static final String kCustomAuto = "My Auto";
    private static final String kBlue = "Blue";
    private static String m_autoSelected = kDefaultAuto;
    private final static SendableChooser<String> m_chooser = new SendableChooser<>();

    public static void init() {
        // driver.addBoolean("Is Game Piece In x", () -> Grab.isGamePieceIn()).withPosition(5, 0).withSize(5, 4);
        m_chooser.setDefaultOption("1 piece", kDefaultAuto);
        m_chooser.addOption("Red", kCustomAuto);
        m_chooser.addOption("Blue", kBlue);
        driver.add("Auto choices", m_chooser);
    }

    public static Autos getAuto(){
        if(m_chooser.getSelected() != null){
            m_autoSelected = m_chooser.getSelected();
        }
        /*  switch (m_autoSelected) {
            case kCustomAuto:
            return Autos.BUMP;
            case kDefaultAuto:
            return Autos.CLIMB;
            case kBlue:
            return Autos.BLUE;
            default:

          }*/
        return null;
    }

    public static void update() {

    }
}
