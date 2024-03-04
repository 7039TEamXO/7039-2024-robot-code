package frc.robot.LED;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.Constants;
import frc.robot.LimeLight;
import frc.robot.Robot;
import frc.robot.subsystems.RobotState;
import frc.robot.subsystems.Intake.Intake;

public class LED {
    private static AddressableLEDBuffer buffer = new AddressableLEDBuffer(2000);
    private static Color color = Color.kOrangeRed;
    private static AddressableLED channel = new AddressableLED(8);

    public static void init() {
        channel.setLength(buffer.getLength());
        channel.setData(buffer);
        channel.start();
    }

    public static void setLedState() {
        if (Math.abs(LimeLight.getTy()) > Constants.tyTolerance + Constants.wantedTY
                && Robot.robotState.equals(RobotState.PODIUM)) {
            color = Color.kRed;

        } else if (Math.abs(LimeLight.getTy()) < -Constants.tyTolerance + Constants.wantedTY
                && Robot.robotState.equals(RobotState.PODIUM)) {
            color = Color.kWhite;
        } else if ((Math.abs(Math.abs(LimeLight.getTy()) - Constants.wantedTY) < Constants.tyTolerance
                || LimeLight.getTy() == 0)
                && Robot.robotState.equals(RobotState.PODIUM)) {
            color = Color.kGreen;
        } else if (Intake.isGamePieceIn()) {

            color = Color.kCyan;
        } else {
            color = Color.kOrangeRed;
        }

        for (int i = 0; i < buffer.getLength(); i++) {

            buffer.setLED(i, color);
        }
        channel.setData(buffer);
    }

}
