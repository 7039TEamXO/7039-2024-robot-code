package frc.robot.auto;

import frc.robot.Constants.AutonomousConstants;

public enum Autos {
    DONT_MOVE(AutonomousConstants.dontMove),
    MIDDLE_THREE(AutonomousConstants.middle_three),
    RIGHT_ONE(AutonomousConstants.right_one),
    LEFT_ONE(AutonomousConstants.left_one),
    SIDE_RED(AutonomousConstants.side_red),
    SIDE_BLUE(AutonomousConstants.side_blue);

    private AutoPoint[] points;

    Autos(AutoPoint[] points) {
        this.points = points;
    }

    public AutoPoint[] getPoints() {
        return points;
    }
}
