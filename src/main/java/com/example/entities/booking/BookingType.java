package com.example.entities.booking;

public enum BookingType {
    DEFAULT,
    EXTENDED;

    public boolean isDefault(){
        return this.equals(DEFAULT);
    }

    public boolean isExtended(){
        return this.equals(EXTENDED);
    }
}
