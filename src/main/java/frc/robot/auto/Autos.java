package frc.robot.auto;

import frc.robot.Constants.AutonomousConstants;

public enum Autos {
    DONT_MOVE(AutonomousConstants.dontMove),
    MIDDLE_THREE(AutonomousConstants.middle_three),
    RIGHT_ONE(AutonomousConstants.right_one),
    LEFT_ONE(AutonomousConstants.left_one),
    FEEDER_RED(AutonomousConstants.feeder_red), FEEDER_BLUE(AutonomousConstants.feeder_blue);

    private AutoPoint[] points;

    Autos(AutoPoint[] points) {
        this.points = points;
    }

    public AutoPoint[] getPoints() {
        return points;
    }
}
