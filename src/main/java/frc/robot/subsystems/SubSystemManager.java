package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.GamePiece;
import frc.robot.GlobalData;
import frc.robot.Robot;
import frc.robot.subsystems.RobotState;
import frc.robot.subsystems.SubSystemManager;
public class SubSystemManager {
    private static RobotState lastState = RobotState.TRAVEL;
    public static final PS4Controller joyPs4Controller = new PS4Controller(0);
    public static double placeTime = 0;

    public static RobotState getRobotStateFromWantedAndActual() {
        final RobotState wantedState = joyPs4Controller.getSquareButtonPressed() ? RobotState.INTAKE_CUBE
                : joyPs4Controller.getCrossButtonPressed() ? RobotState.INTAKE_CONE
                        : joyPs4Controller.getCircleButtonPressed() ? RobotState.TRAVEL
                                : joyPs4Controller.getTriangleButtonPressed() ? RobotState.SINGLE_PLACE
                                        : joyPs4Controller.getL1ButtonPressed() ? RobotState.LOW_PLACE
                                                : joyPs4Controller.getR1ButtonPressed() ? RobotState.MID_PLACE
                                                        : lastState;

        switch (wantedState) {
            case FEEDER_INTAKE:
                return RobotState.FEEDER_INTAKE;

            case INTAKE_CONE:
                return RobotState.INTAKE_CONE;

            case INTAKE_CUBE:
                return RobotState.INTAKE_CUBE;

            case LOW_PLACE:
                return RobotState.LOW_PLACE;

            case MID_PLACE:
                return RobotState.MID_PLACE;

            case SINGLE_PLACE:
                return RobotState.SINGLE_PLACE;

            case TRAVEL:
            default:
                return RobotState.TRAVEL;

        }
    }

    public static void operate() {
        switch (Robot.robotState) {
            case TRAVEL:
                break;
        }

        // Intake.operate();
        // Arm.operate();
        // Grab.operate();
        lastState = Robot.robotState;
    }
}
