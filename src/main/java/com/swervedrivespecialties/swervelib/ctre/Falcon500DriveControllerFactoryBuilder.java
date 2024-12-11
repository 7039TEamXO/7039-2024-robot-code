package com.swervedrivespecialties.swervelib.ctre;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.swervedrivespecialties.swervelib.DriveController;
import com.swervedrivespecialties.swervelib.DriveControllerFactory;
import com.swervedrivespecialties.swervelib.MechanicalConfiguration;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;

public final class Falcon500DriveControllerFactoryBuilder {
    // private static final double TICKS_PER_ROTATION = 2048.0;

    private static final double CAN_TIMEOUT_SEC = 0.25;
    private static final int STATUS_FRAME_GENERAL_PERIOD_MS = 250;

    private double nominalVoltage = Double.NaN;
    private double currentLimit = 45;

    public Falcon500DriveControllerFactoryBuilder withVoltageCompensation(double nominalVoltage) {
        this.nominalVoltage = nominalVoltage;
        return this;
    }

    public boolean hasVoltageCompensation() {
        return Double.isFinite(nominalVoltage);
    }

    public DriveControllerFactory<ControllerImplementation, Integer> build() {
        return new FactoryImplementation();
    }

    public Falcon500DriveControllerFactoryBuilder withCurrentLimit(double currentLimit) {
        this.currentLimit = currentLimit;
        return this;
    }

    public boolean hasCurrentLimit() {
        
        return Double.isFinite(currentLimit);
    }

    private class FactoryImplementation implements DriveControllerFactory<ControllerImplementation, Integer> {
        @Override
        public ControllerImplementation create(Integer id, String canbus, MechanicalConfiguration mechConfiguration) {
            TalonFXConfiguration motorConfiguration = new TalonFXConfiguration();

            double sensorPositionCoefficient = Math.PI * mechConfiguration.getWheelDiameter() * mechConfiguration.getDriveReduction(); // / TICKS_PER_ROTATION;

            // if (hasVoltageCompensation()) {
            //     motorConfiguration.voltageCompSaturation = nominalVoltage;
            // }

            if (hasCurrentLimit()) {
                motorConfiguration.CurrentLimits.SupplyCurrentLimit = currentLimit;
                motorConfiguration.CurrentLimits.SupplyCurrentLimitEnable = true;
            }

            // motorConfiguration.Feedback.SensorToMechanismRatio = -1; // TODO: make this sensorPositionCoefficient

            motorConfiguration.MotorOutput.Inverted = mechConfiguration.isDriveInverted() ? InvertedValue.Clockwise_Positive : InvertedValue.CounterClockwise_Positive;

            TalonFX motor = new TalonFX(id, canbus);
            CtreUtils.checkCtreError(motor.getConfigurator().apply(motorConfiguration), "Failed to configure Falcon 500");

            // if (hasVoltageCompensation()) {
            //     // Enable voltage compensation
            //     motor.enableVoltageCompensation(true);
            // }
            
            motor.setNeutralMode(NeutralModeValue.Brake);
            // motor.setSensorPhase(true); // moved above to SensorToMechanismRatio

            // Reduce CAN status frame rates
            CtreUtils.checkCtreError(
                    motor.getPosition().setUpdateFrequency(
                            1000.0 / STATUS_FRAME_GENERAL_PERIOD_MS, // Hz
                            CAN_TIMEOUT_SEC
                    ),
                    "Failed to configure Falcon status frame period"
            );
            CtreUtils.checkCtreError(
                    motor.getVelocity().setUpdateFrequency(
                            1000.0 / STATUS_FRAME_GENERAL_PERIOD_MS, // Hz
                            CAN_TIMEOUT_SEC
                    ),
                    "Failed to configure Falcon status frame period"
            );

            return new ControllerImplementation(motor, sensorPositionCoefficient);
        }
    }

    private class ControllerImplementation implements DriveController {
        private final TalonFX motor;
        private final double sensorPositionCoefficient;
        // private final double nominalVoltage = hasVoltageCompensation() ? Falcon500DriveControllerFactoryBuilder.this.nominalVoltage : 12.0;

        private ControllerImplementation(TalonFX motor, double sensorPositionCoefficient) {
            this.motor = motor;
            this.sensorPositionCoefficient = sensorPositionCoefficient;
        }

        @Override
        public MotorController getDriveMotor() {
            return this.motor;
        }

        @Override
        public void setReferenceVoltage(double voltage) {
            // motor.set(TalonFXControlMode.PercentOutput, voltage / nominalVoltage);
            motor.setVoltage(voltage);
        }

        @Override
        public double getStateVelocity() {
            // Multiply to 10 to convert from m/100ms to m/s
            // return motor.getSelectedSensorVelocity() * sensorPositionCoefficient * 10.0;
            return motor.getVelocity().getValue() * sensorPositionCoefficient;
        }

        @Override
        public double getStateDistance() {
            // return motor.getSelectedSensorPosition() * sensorPositionCoefficient;
            return motor.getPosition().getValue() * sensorPositionCoefficient;
        }
    }
}
