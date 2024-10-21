package frc.robot.auto;

import frc.robot.Constants.AutonomousConstants;

public enum Autos {
    DONT_MOVE(AutonomousConstants.dontMove),
    MIDDLE_RED(AutonomousConstants.middle_red),
    MIDDLE_BLUE(AutonomousConstants.middle_blue),
    TEST(AutonomousConstants.test),
    SIDE_RED(AutonomousConstants.side_red),
    SIDE_BLUE(AutonomousConstants.side_blue),
    STEAL_RED(AutonomousConstants.steal_side_red),
    STEAL_BLUE(AutonomousConstants.steal_side_blue),
    SIDE_BLUE_ONE(AutonomousConstants.side_blue_one),
    SIDE_RED_ONE(AutonomousConstants.side_red_one);

    private AutoPoint[] points;

    Autos(AutoPoint[] points) {
        this.points = points;
    }

    public AutoPoint[] getPoints() {
        return points;
    }
}
