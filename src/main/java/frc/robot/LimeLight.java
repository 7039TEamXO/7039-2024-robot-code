package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class LimeLight {
    private static NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    private static NetworkTableEntry tx = table.getEntry("tx");
    private static double x = 0;
    private static NetworkTableEntry ty = table.getEntry("ty");
    private static double y = 0;

    public static void update() {
        x = tx.getDouble(0.0);
        y = ty.getDouble(0);
    }

    public static double getTx() {
        return x;

    }

    public static double getTy() {
        return y;
    }

    public static boolean isReadyToShoot() {
        return (Math.abs(Math.abs(LimeLight.getTy()) - Constants.wantedTY) < Constants.tyTolerance);
    }
}
