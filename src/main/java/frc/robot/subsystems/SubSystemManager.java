package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PS4Controller;
import frc.robot.Robot;
import frc.robot.subsystems.SubSystemManager;
import frc.robot.subsystems.Arm.Arm;
import frc.robot.subsystems.Arm.ArmState;
import frc.robot.subsystems.Climb.Climb;
import frc.robot.subsystems.Climb.ClimbState;
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
    private static ClimbState climbState = ClimbState.STOP;
    private static ArmState armState = ArmState.CLOSE;
    private static boolean isGamePiece = false;

    public static RobotState getRobotStateFromWantedAndActual() {
        final RobotState wantedState = joyPs4Controller.getCircleButtonPressed() ? RobotState.TRAVEL
                : joyPs4Controller.getCrossButtonPressed() ? RobotState.INTAKE
                        : joyPs4Controller.getSquareButtonPressed() ? RobotState.PODIUM
                                : joyPs4Controller.getTriangleButtonPressed() ? RobotState.SUBWOOFER
                                        : joyPs4Controller.getL1ButtonPressed() ? RobotState.AMP
                                                : joyPs4Controller.getShareButtonPressed() ? RobotState.CLIMB
                                                        : joyPs4Controller.getR1ButtonPressed() ? RobotState.DEPLETE
                                                                : lastState;

        return wantedState;
    }

    public static void operate() {
        switch (Robot.robotState) {
            case TRAVEL:
                shooterState = ShooterState.STOP;
                intakeState = IntakeState.STOP;
                conveyorState = ConveyorState.STOP;
                climbState = ClimbState.STOP;
                armState = ArmState.CLOSE;
                break;
            case AMP:
                shooterState = ShooterState.AMP_SHOOTING;
                intakeState = Shooter.readyToShoot() && Arm.reached() ? IntakeState.COLLECT : IntakeState.LOADING;
                conveyorState = Shooter.readyToShoot() && Arm.reached() ? ConveyorState.HIGH_SHOOTER
                        : ConveyorState.STOP;
                climbState = ClimbState.STOP;
                armState = ArmState.OPEN;
                break;
            case INTAKE:
                shooterState = ShooterState.STOP;
                intakeState = isGamePiece ? IntakeState.STOP : IntakeState.COLLECT;
                conveyorState = ConveyorState.STOP;
                climbState = ClimbState.STOP;
                armState = ArmState.CLOSE;
                break;
            case PODIUM:
                shooterState = ShooterState.PODIUM_SHOOTING;
                intakeState = Shooter.readyToShoot() ? IntakeState.COLLECT : IntakeState.LOADING;
                conveyorState = Shooter.readyToShoot() ? ConveyorState.LOW_SHOOTER : ConveyorState.STOP;
                climbState = ClimbState.STOP;
                armState = ArmState.CLOSE;
                break;
            case SUBWOOFER:
                shooterState = ShooterState.SUBWOOFER_SHOOTING;
                intakeState = Shooter.readyToShoot() ? IntakeState.COLLECT : IntakeState.LOADING;
                conveyorState = Shooter.readyToShoot() ? ConveyorState.HIGH_SHOOTER : ConveyorState.STOP;
                climbState = ClimbState.STOP;
                armState = ArmState.CLOSE;
                break;
            case CLIMB:
                shooterState = ShooterState.STOP;
                intakeState = IntakeState.STOP;
                conveyorState = ConveyorState.STOP;
                armState = ArmState.CLOSE;
                if (joyPs4Controller.getPOV() == 0) {
                    climbState = ClimbState.UP;
                } else if (joyPs4Controller.getPOV() == 180) {
                    climbState = ClimbState.DOWN;
                } else {
                    climbState = ClimbState.STOP;
                }
                break;
            case DEPLETE:
                intakeState = IntakeState.DEPLETE;
                conveyorState = ConveyorState.STOP;
                shooterState = ShooterState.STOP;
                climbState = ClimbState.STOP;
                armState = ArmState.CLOSE;
                break;
            case DEFLECT:
                shooterState = ShooterState.DEFLECT;
                intakeState =  IntakeState.COLLECT;
                conveyorState =  ConveyorState.LOW_SHOOTER;
                climbState = ClimbState.STOP;
                armState = ArmState.CLOSE;
                break;

        }
        if (Robot.robotState.equals(RobotState.INTAKE)) {
            isGamePiece |= Intake.isGamePieceIn();
        } else {
            isGamePiece = false;
        }
        Intake.operate(intakeState);
        Shooter.operate(shooterState);
        Conveyor.operate(conveyorState);
        Climb.operate(climbState);
        Arm.operate(armState);
        lastState = Robot.robotState;
    }
}
