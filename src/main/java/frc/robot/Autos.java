package frc.robot;

import frc.robot.auto.AutoPoint;

public enum Autos {
    BUMP(Constants.AutonomousConstants.bump), CLIMB(Constants.AutonomousConstants.bump), BLUE(Constants.AutonomousConstants.bumpBlue);

   private AutoPoint[] points;

    Autos(AutoPoint[] points){
        this.points = points;
    }

    public AutoPoint[] getPoints() {
        return points;
    }
}
