package frc.robot.auto;

import edu.wpi.first.math.geometry.Pose2d;
import frc.robot.Constants;
import frc.robot.subsystems.RobotState;

public class AutoPoint {
    private final Pose2d pose;
    private final RobotState action;
    private float maxVel = Constants.maxVelAuto;

    public AutoPoint(Pose2d pose, RobotState action) {
        this.pose = pose;
        this.action = action;
    }

    public AutoPoint(Pose2d pose, RobotState action, float maxVel) {
        this.pose = pose;
        this.action = action;
        this.maxVel = maxVel;
    }

    public Pose2d getWantedPose(){
        return pose;
    }

    public RobotState getAction(){
        return action;
    }

    public float getMaxVel(){
        return maxVel;
    }
}