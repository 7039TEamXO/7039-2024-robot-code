package frc.robot.LED;

public enum LedState {
    ORANGE(0.65),LIME(0.73);
    private double power;
    LedState(double power){
        this.power = power;
    }

    public double getPower() {
        return power;
    }
}
