package frc.robot.auto;

import frc.robot.Constants.AutonomousConstants;

public enum Autos {
    DONT_MOVE(AutonomousConstants.dontMove),
    MIDDLE_RED(AutonomousConstants.middle_red),
    MIDDLE_BLUE(AutonomousConstants.middle_blue),
    SIDE_RED(AutonomousConstants.side_red),
    SIDE_BLUE(AutonomousConstants.side_blue),
    STEAL_RED(AutonomousConstants.steal_side_red),
    STEAL_BLUE(AutonomousConstants.steal_side_blue);

    private AutoPoint[] points;

    Autos(AutoPoint[] points) {
        this.points = points;
    }

    public AutoPoint[] getPoints() {
        return points;
    }
}
