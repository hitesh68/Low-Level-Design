package com.example.entities.booking;

import java.time.LocalTime;

import com.example.entities.racetrack.TrackType;
import com.example.entities.vehicles.VehicleType;

public interface IBooking {
    
    BookingType getBookingType();
    TrackType getTrackType();
    VehicleType getVehicleType();
    String getVehicleNumber();
    LocalTime getEntryTime();
    LocalTime getExitTime();
    int getDurationInMins();
}
