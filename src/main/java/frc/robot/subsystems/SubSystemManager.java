package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.GlobalData;
import frc.robot.Robot;
import frc.robot.subsystems.RobotState;
import frc.robot.subsystems.SubSystemManager;
import frc.robot.subsystems.Conveyor.Conveyor;
import frc.robot.subsystems.Conveyor.ConveyorState;
import frc.robot.subsystems.Intake.Intake;
import frc.robot.subsystems.Intake.IntakeState;
import frc.robot.subsystems.Shooter.Shooter;
import frc.robot.subsystems.Shooter.ShooterState;

public class SubSystemManager {
    private static RobotState lastState = RobotState.TRAVEL;
    public static final PS4Controller joyPs4Controller = new PS4Controller(0);
    public static double placeTime = 0;
    private static ShooterState shooterState = ShooterState.STOP;
    private static IntakeState intakeState = IntakeState.STOP;
    private static ConveyorState conveyorState = ConveyorState.STOP;

    public static RobotState getRobotStateFromWantedAndActual() {
        final RobotState wantedState = joyPs4Controller.getCircleButtonPressed() ? RobotState.TRAVEL
                : joyPs4Controller.getCrossButtonPressed() ? RobotState.INTAKE
                        : joyPs4Controller.getSquareButtonPressed() ? RobotState.PODIUM
                                : joyPs4Controller.getTriangleButtonPressed() ? RobotState.SUBWOOFER
                                        : joyPs4Controller.getL1ButtonPressed() ? RobotState.AMP
                                                : lastState;

        switch (wantedState) {
            case TRAVEL:
            default:
                return RobotState.TRAVEL;

        }
    }

    public static void operate() {
        switch (Robot.robotState) {
            case TRAVEL:
                shooterState = ShooterState.STOP;
                intakeState = IntakeState.STOP;
                conveyorState = ConveyorState.STOP;
                break;
            case AMP:
                shooterState = ShooterState.AMP_SHOOTING;
                intakeState = Shooter.readyToShoot() ? IntakeState.COLLECT : IntakeState.STOP;
                conveyorState = Shooter.readyToShoot() ? ConveyorState.HIGH_SHOOTER : ConveyorState.STOP;
                break;
            case INTAKE:
                shooterState = ShooterState.STOP;
                intakeState = IntakeState.COLLECT;
                conveyorState = ConveyorState.STOP;
                break;
            case PODIUM:
                shooterState = ShooterState.PODIUM_SHOOTING;
                intakeState = Shooter.readyToShoot() ? IntakeState.COLLECT : IntakeState.STOP;
                conveyorState = Shooter.readyToShoot() ? ConveyorState.LOW_SHOOTER : ConveyorState.STOP;
                break;
            case SUBWOOFER:
                shooterState = ShooterState.SUBWOOFER_SHOOTING;
                intakeState = Shooter.readyToShoot() ? IntakeState.COLLECT : IntakeState.STOP;
                conveyorState = Shooter.readyToShoot() ? ConveyorState.HIGH_SHOOTER : ConveyorState.STOP;
                break;
        }
        Intake.operate(intakeState);
        Shooter.operate(shooterState);
        Conveyor.operate(conveyorState);
        lastState = Robot.robotState;
    }
}
