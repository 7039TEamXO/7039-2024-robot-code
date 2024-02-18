package frc.robot.LED;

import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.motorcontrol.Spark;

public class LED {
    private static LedState ledState = LedState.GREEN;
    private static PWM channel = new PWM(7);

    public static void init() {

    }

    public static void setLedState() {
        // if(false){

        // }else{
            ledState = LedState.GREEN;
        // }
        channel.setSpeed(-1);

    }

}
