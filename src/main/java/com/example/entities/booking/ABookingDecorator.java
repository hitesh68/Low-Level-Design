package com.example.entities.booking;

import java.time.LocalTime;

import com.example.entities.racetrack.TrackType;
import com.example.entities.vehicles.VehicleType; 

public abstract class ABookingDecorator implements IBooking{
    
    private final IBooking baseBooking;

    public ABookingDecorator(IBooking baseBooking) {
        this.baseBooking = baseBooking;
    }

    @Override
    public final TrackType getTrackType() {
        return this.baseBooking.getTrackType();
    }

    @Override
    public final VehicleType getVehicleType() {
        return this.baseBooking.getVehicleType();
    }

    @Override
    public final String getVehicleNumber() {
        return this.baseBooking.getVehicleNumber();
    }

    @Override
    public final LocalTime getEntryTime() {
        return this.baseBooking.getEntryTime();
    }

}
