package frc.robot;

import frc.robot.Constants.AutonomousConstants;
import frc.robot.auto.AutoPoint;

public enum Autos {
    DONT_MOVE(AutonomousConstants.dontMove),
    ONE_METER(AutonomousConstants.oneMeter),
    MIDDLE_ONE(AutonomousConstants.middle_one),
    MIDDLE_THREE(AutonomousConstants.middle_three);

   private AutoPoint[] points;

    Autos(AutoPoint[] points){
        this.points = points;
    }

    public AutoPoint[] getPoints() {
        return points;
    }
}
