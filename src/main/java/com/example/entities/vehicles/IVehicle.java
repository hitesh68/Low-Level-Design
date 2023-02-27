package com.example.entities.vehicles;

import java.time.LocalTime;

import com.example.entities.booking.IBooking;
import com.example.exceptions.RacetrackFullException;
import com.example.exceptions.TrackNotFoundException;

public interface IVehicle {
    
    IBooking bookSlot(LocalTime entryTime, LocalTime exitTime) throws RacetrackFullException, TrackNotFoundException;
    
    IBooking extendSlot(
        IBooking defaultBooking, 
                LocalTime exitTime) 
                    throws RacetrackFullException, TrackNotFoundException;

    public VehicleType getVehicleType();
    public String getVehicleNumber();
}
