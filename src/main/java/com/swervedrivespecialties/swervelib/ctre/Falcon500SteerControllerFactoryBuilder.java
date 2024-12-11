package com.swervedrivespecialties.swervelib.ctre;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.ControlRequest;
import com.ctre.phoenix6.controls.MotionMagicDutyCycle;
import com.ctre.phoenix6.controls.PositionDutyCycle;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.swervedrivespecialties.swervelib.*;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardContainer;

import static com.swervedrivespecialties.swervelib.ctre.CtreUtils.checkCtreError;

import java.util.function.Function;

public final class Falcon500SteerControllerFactoryBuilder {
    private static final double CAN_TIMEOUT_SEC = 0.25;
    private static final int STATUS_FRAME_GENERAL_PERIOD_MS = 250;

    // private static final double TICKS_PER_ROTATION = 2048.0;

    // PID configuration
    private double proportionalConstant = Double.NaN;
    private double integralConstant = Double.NaN;
    private double derivativeConstant = Double.NaN;

    // Motion magic configuration
    private double velocityConstant = Double.NaN;
    private double accelerationConstant = Double.NaN;
    private double staticConstant = Double.NaN;

    private double nominalVoltage = Double.NaN;
    private double currentLimit = 60;

    public Falcon500SteerControllerFactoryBuilder withPidConstants(double proportional, double integral,
            double derivative) {
        this.proportionalConstant = proportional;
        this.integralConstant = integral;
        this.derivativeConstant = derivative;
        return this;
    }

    public boolean hasPidConstants() {
        return Double.isFinite(proportionalConstant) && Double.isFinite(integralConstant)
                && Double.isFinite(derivativeConstant);
    }

    public Falcon500SteerControllerFactoryBuilder withMotionMagic(double velocityConstant, double accelerationConstant,
            double staticConstant) {
        this.velocityConstant = velocityConstant;
        this.accelerationConstant = accelerationConstant;
        this.staticConstant = staticConstant;
        return this;
    }

    public boolean hasMotionMagic() {
        return Double.isFinite(velocityConstant) && Double.isFinite(accelerationConstant)
                && Double.isFinite(staticConstant);
    }

    public Falcon500SteerControllerFactoryBuilder withVoltageCompensation(double nominalVoltage) {
        this.nominalVoltage = nominalVoltage;
        return this;
    }

    public boolean hasVoltageCompensation() {
        return Double.isFinite(nominalVoltage);
    }

    public Falcon500SteerControllerFactoryBuilder withCurrentLimit(double currentLimit) {
        this.currentLimit = currentLimit;
        return this;
    }

    public boolean hasCurrentLimit() {
        return Double.isFinite(currentLimit);
    }

    public <T> SteerControllerFactory<ControllerImplementation, SteerConfiguration<T>> build(
            AbsoluteEncoderFactory<T> absoluteEncoderFactory) {
        return new FactoryImplementation<>(absoluteEncoderFactory);
    }

    private class FactoryImplementation<T>
            implements SteerControllerFactory<ControllerImplementation, SteerConfiguration<T>> {
        private final AbsoluteEncoderFactory<T> encoderFactory;

        private FactoryImplementation(AbsoluteEncoderFactory<T> encoderFactory) {
            this.encoderFactory = encoderFactory;
        }

        @Override
        public void addDashboardEntries(ShuffleboardContainer container, ControllerImplementation controller) {
            SteerControllerFactory.super.addDashboardEntries(container, controller);
            container.addNumber("Absolute Encoder Angle",
                    () -> Math.toDegrees(controller.absoluteEncoder.getAbsoluteAngle()));
        }

        @Override
        public ControllerImplementation create(SteerConfiguration<T> steerConfiguration, String canbus,
                MechanicalConfiguration mechConfiguration) {
            AbsoluteEncoder absoluteEncoder = encoderFactory.create(steerConfiguration.getEncoderConfiguration());

            final double sensorPositionCoefficient = 2.0 * Math.PI // / TICKS_PER_ROTATION
                    * mechConfiguration.getSteerReduction();
            final double sensorVelocityCoefficient = sensorPositionCoefficient; // * 10.0;

            TalonFXConfiguration motorConfiguration = new TalonFXConfiguration();
            if (hasPidConstants()) {
                motorConfiguration.Slot0.kP = proportionalConstant;
                motorConfiguration.Slot0.kI = integralConstant;
                motorConfiguration.Slot0.kD = derivativeConstant;
            }
            if (hasMotionMagic()) {
                // if (hasVoltageCompensation()) {
                //     motorConfiguration.Slot0.kF = (1023.0 * sensorVelocityCoefficient / nominalVoltage)
                //             * velocityConstant;
                // }
                // TODO: What should be done if no nominal voltage is configured? Use a default
                // voltage?

                // TODO: Make motion magic max voltages configurable or dynamically determine
                // optimal values
                motorConfiguration.MotionMagic.MotionMagicCruiseVelocity = 2.0 / velocityConstant / sensorVelocityCoefficient;
                motorConfiguration.MotionMagic.MotionMagicAcceleration = (8.0 - 2.0) / accelerationConstant / sensorVelocityCoefficient;
            }
            // if (hasVoltageCompensation()) {
            //     motorConfiguration.voltageCompSaturation = nominalVoltage;
            // }
            if (hasCurrentLimit()) {
                motorConfiguration.CurrentLimits.SupplyCurrentLimit = currentLimit;
                motorConfiguration.CurrentLimits.SupplyCurrentLimitEnable = true;
            }

            motorConfiguration.Feedback.FeedbackSensorSource = FeedbackSensorSourceValue.RotorSensor;
            // motorConfiguration.Feedback.SensorToMechanismRatio = -1; // TODO: make this sensorPositionCoefficient

            motorConfiguration.MotorOutput.Inverted = mechConfiguration.isSteerInverted() ? InvertedValue.CounterClockwise_Positive : InvertedValue.Clockwise_Positive;

            TalonFX motor = new TalonFX(steerConfiguration.getMotorPort(), canbus);
            checkCtreError(motor.getConfigurator().apply(motorConfiguration, CAN_TIMEOUT_SEC),
                    "Failed to configure Falcon 500 settings");

            // if (hasVoltageCompensation()) {
            //     motor.enableVoltageCompensation(true);
            // }
            // checkCtreError(
            //         motor.(TalonFXFeedbackDevice.IntegratedSensor, 0, CAN_TIMEOUT_MS),
            //         "Failed to set Falcon 500 feedback sensor");
            // motor.setSensorPhase(true);
            motor.setNeutralMode(NeutralModeValue.Brake);

            checkCtreError(
                    motor.setPosition(absoluteEncoder.getAbsoluteAngle() / sensorPositionCoefficient,
                            CAN_TIMEOUT_SEC),
                    "Failed to set Falcon 500 encoder position");

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

            return new ControllerImplementation(motor,
                    sensorPositionCoefficient,
                    sensorVelocityCoefficient,
                    hasMotionMagic() ? MotionMagicDutyCycle::new : PositionDutyCycle::new,
                    absoluteEncoder);
        }
    }

    private static class ControllerImplementation implements SteerController {
        private static final int ENCODER_RESET_ITERATIONS = 500;
        private static final double ENCODER_RESET_MAX_ANGULAR_VELOCITY = Math.toRadians(0.5);

        private final TalonFX motor;
        private final double motorEncoderPositionCoefficient;
        private final double motorEncoderVelocityCoefficient;
        private final Function<Double, ControlRequest> controlRequestFunction;
        private final AbsoluteEncoder absoluteEncoder;

        private double referenceAngleRadians = 0.0;

        private double resetIteration = ENCODER_RESET_ITERATIONS;

        private ControllerImplementation(TalonFX motor,
                double motorEncoderPositionCoefficient,
                double motorEncoderVelocityCoefficient,
                Function<Double, ControlRequest> controlRequestFunction,
                AbsoluteEncoder absoluteEncoder) {
            this.motor = motor;
            this.motorEncoderPositionCoefficient = motorEncoderPositionCoefficient;
            this.motorEncoderVelocityCoefficient = motorEncoderVelocityCoefficient;
            this.controlRequestFunction = controlRequestFunction;
            this.absoluteEncoder = absoluteEncoder;
        }

        @Override
        public MotorController getSteerMotor() {
            return this.motor;
        }

        @Override
        public AbsoluteEncoder getSteerEncoder() {
            return this.absoluteEncoder;
        }

        @Override
        public double getReferenceAngle() {
            return referenceAngleRadians;
        }

        @Override
        public void setReferenceAngle(double referenceAngleRadians) {
            double currentAngleRadians = motor.getPosition().getValue() * motorEncoderPositionCoefficient;

            // Reset the NEO's encoder periodically when the module is not rotating.
            // Sometimes (~5% of the time) when we initialize, the absolute encoder isn't
            // fully set up, and we don't
            // end up getting a good reading. If we reset periodically this won't matter
            // anymore.
            if (motor.getVelocity().getValue()
                    * motorEncoderVelocityCoefficient < ENCODER_RESET_MAX_ANGULAR_VELOCITY) {
                if (++resetIteration >= ENCODER_RESET_ITERATIONS) {
                    resetIteration = 0;
                    double absoluteAngle = absoluteEncoder.getAbsoluteAngle();
                    motor.setPosition(absoluteAngle / motorEncoderPositionCoefficient);
                    currentAngleRadians = absoluteAngle;
                }
            } else {
                resetIteration = 0;
            }

            double currentAngleRadiansMod = currentAngleRadians % (2.0 * Math.PI);
            if (currentAngleRadiansMod < 0.0) {
                currentAngleRadiansMod += 2.0 * Math.PI;
            }

            // The reference angle has the range [0, 2pi) but the Falcon's encoder can go
            // above that
            double adjustedReferenceAngleRadians = referenceAngleRadians + currentAngleRadians - currentAngleRadiansMod;
            if (referenceAngleRadians - currentAngleRadiansMod > Math.PI) {
                adjustedReferenceAngleRadians -= 2.0 * Math.PI;
            } else if (referenceAngleRadians - currentAngleRadiansMod < -Math.PI) {
                adjustedReferenceAngleRadians += 2.0 * Math.PI;
            }

            motor.setControl(controlRequestFunction.apply(adjustedReferenceAngleRadians / motorEncoderPositionCoefficient));

            this.referenceAngleRadians = referenceAngleRadians;
        }

        @Override
        public double getStateAngle() {
            double motorAngleRadians = motor.getPosition().getValue() * motorEncoderPositionCoefficient;
            motorAngleRadians %= 2.0 * Math.PI;
            if (motorAngleRadians < 0.0) {
                motorAngleRadians += 2.0 * Math.PI;
            }

            return motorAngleRadians;
        }

        @Override
        public void resetToAbsolute() {
            resetIteration = 0;
            double absoluteAngle = absoluteEncoder.getAbsoluteAngle();
            motor.setPosition(absoluteAngle / motorEncoderPositionCoefficient);
        }
    }
}
