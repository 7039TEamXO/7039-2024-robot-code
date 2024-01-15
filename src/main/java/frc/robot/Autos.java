package frc.robot;

import frc.robot.Constants.AutonomousConstants;
import frc.robot.auto.AutoPoint;

public enum Autos {
    DONT_MOVE(AutonomousConstants.dontMove);

   private AutoPoint[] points;

    Autos(AutoPoint[] points){
        this.points = points;
    }

    public AutoPoint[] getPoints() {
        return points;
    }
}
