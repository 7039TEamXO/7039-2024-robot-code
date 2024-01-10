package frc.robot.auto;

import edu.wpi.first.math.geometry.Pose2d;
import frc.robot.subsystems.RobotState;

public class AutoPoint {
    private final Pose2d pose;
    private final RobotState action;
    private float distanceToExecuteAction = 0;

    public AutoPoint(Pose2d pose, RobotState action) {
        this.pose = pose;
        this.action = action;
    }

    public AutoPoint(Pose2d pose, RobotState action, float distanceToExecuteAction) {
        this.pose = pose;
        this.action = action;
        this.distanceToExecuteAction = distanceToExecuteAction;
    }

    public Pose2d getWantedPose(){
        return pose;
    }

    public RobotState getAction(){
        return action;
    }

    public float getDistanceToExecuteAction(){
        return distanceToExecuteAction;
    }
}