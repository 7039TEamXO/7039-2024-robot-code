package com.swervedrivespecialties.swervelib.ctre;

public class CanCoderAbsoluteConfiguration {
    private final int id;
    private final double offset;
    private final String canbus;

    public CanCoderAbsoluteConfiguration(int id, double offset, String canbus) {
        this.id = id;
        this.offset = offset;
        this.canbus = canbus;
    }

    public CanCoderAbsoluteConfiguration(int id, double offset) {
        this(id, offset, "");
    }

    public int getId() {
        return id;
    }

    public double getOffset() {
        return offset;
    }

    public String getCanbus() {
        return canbus;
    }
}
