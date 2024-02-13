package frc.robot.LED;

import edu.wpi.first.wpilibj.motorcontrol.Spark;

public class LED {
    private static LedState ledState = LedState.ORANGE;
    private static Spark m_blinkin;
    private static int pwmPort = 0;

    public static void init() {
        m_blinkin = new Spark(pwmPort);
    }

    public static void setLedState() {
        if(false){

        }else{
            ledState = LedState.ORANGE;
        }
        m_blinkin.set(ledState.getPower());
    }

}
