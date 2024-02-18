package frc.robot.LED;

public enum LedState {
    GREEN(1,1,0);
    private double red;
    private double green;
    private double blue;
    LedState(double red, double green, double blue){
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public double getRed() {
        return red;
    }
    
    public double getGreen() {
        return green;
    }
    
    public double getBlue() {
        return blue;
    }
}
