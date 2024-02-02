package com.swervedrivespecialties.swervelib.ctre;

import com.ctre.phoenix6.StatusCode;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotBase;

public final class CtreUtils {
    private CtreUtils() {}

    public static void checkCtreError(StatusCode code, String message) {
        if (RobotBase.isReal() && code != StatusCode.OK) {
            DriverStation.reportError(String.format("%s: %s", message, code.toString()), false);
        }
    }
}
