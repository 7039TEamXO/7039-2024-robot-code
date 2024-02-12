package frc.robot.subsystems;

public enum RobotState {
    TRAVEL(false), INTAKE(false), AMP(true), SUBWOOFER(true), PODIUM(true), CLIMB(false), DEPLETE(false);

    private boolean score;

    private RobotState(boolean score) {
        this.score = score;
    }
    
    public boolean isScoring(){
        return score;
    }
}
