package com.swervedrivespecialties.swervelib.ctre;

import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.signals.AbsoluteSensorRangeValue;
import com.ctre.phoenix6.signals.SensorDirectionValue;
import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.configs.MagnetSensorConfigs;
import com.ctre.phoenix6.hardware.CANcoder;
import com.swervedrivespecialties.swervelib.AbsoluteEncoder;
import com.swervedrivespecialties.swervelib.AbsoluteEncoderFactory;

public class CanCoderFactoryBuilder {
    private Direction direction = Direction.COUNTER_CLOCKWISE;
    private int periodMilliseconds = 10;

    public CanCoderFactoryBuilder withReadingUpdatePeriod(int periodMilliseconds) {
        this.periodMilliseconds = periodMilliseconds;
        return this;
    }

    public CanCoderFactoryBuilder withDirection(Direction direction) {
        this.direction = direction;
        return this;
    }

    public AbsoluteEncoderFactory<CanCoderAbsoluteConfiguration> build() {
        return configuration -> {
            CANcoderConfiguration config = new CANcoderConfiguration()
                .withMagnetSensor(new MagnetSensorConfigs()
                    .withAbsoluteSensorRange(AbsoluteSensorRangeValue.Unsigned_0To1)
                    .withSensorDirection(direction == Direction.CLOCKWISE ? SensorDirectionValue.Clockwise_Positive : SensorDirectionValue.CounterClockwise_Positive)
                    .withMagnetOffset(configuration.getOffset() / (2 * Math.PI)));

            CANcoder encoder = new CANcoder(configuration.getId(), configuration.getCanbus());
            CtreUtils.checkCtreError(encoder.getConfigurator().apply(config, 0.25), "Failed to configure CANCoder");

            CtreUtils.checkCtreError(encoder.getPosition().setUpdateFrequency(1000.0 / periodMilliseconds, 0.25), "Failed to configure CANCoder update rate");

            return new EncoderImplementation(encoder);
        };
    }

    private static class EncoderImplementation implements AbsoluteEncoder {
        private final int ATTEMPTS = 3; // TODO: Allow changing number of tries for getting correct position

        private final CANcoder encoder;

        private EncoderImplementation(CANcoder encoder) {
            this.encoder = encoder;
        }

        @Override
        public double getAbsoluteAngle() {
            StatusSignal<Double> angleCode = encoder.getPosition();

            for (int i = 0; i < ATTEMPTS; i++) {
                if (angleCode.getStatus() == StatusCode.OK) break;
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) { }
                angleCode = encoder.getPosition();
            }

            CtreUtils.checkCtreError(angleCode.getStatus(), "Failed to retrieve CANcoder "+encoder.getDeviceID()+" absolute position after "+ATTEMPTS+" tries");

            double angle = angleCode.getValue() * 2.0 * Math.PI;
            angle %= 2.0 * Math.PI;
            if (angle < 0.0) {
                angle += 2.0 * Math.PI;
            }

            return angle;
        }

        @Override
        public Object getInternal() {
            return this.encoder;
        }
    }

    public enum Direction {
        CLOCKWISE,
        COUNTER_CLOCKWISE
    }
}
