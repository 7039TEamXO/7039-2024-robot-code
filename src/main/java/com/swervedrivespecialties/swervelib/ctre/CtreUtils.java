package com.swervedrivespecialties.swervelib.ctre;

import com.ctre.phoenix.ErrorCode;
import edu.wpi.first.wpilibj.DriverStation;
public final class CtreUtils {
    private CtreUtils() {
    }

    public static void checkCtreError(ErrorCode errorCode, String message) {
        if (errorCode != ErrorCode.OK) {
            // throw new RuntimeException(String.format("%s: %s", message, errorCode.toString()));
            DriverStation.reportError(String.format("%s: %s", message, errorCode.toString()), false);
        }
    }
}
