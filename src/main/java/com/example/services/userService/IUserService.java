package com.example.services.userService;

import java.time.LocalTime;

import com.example.entities.vehicles.VehicleType;
import com.example.exceptions.BookingNotFoundException;
import com.example.exceptions.InvalidEntryTimeException;
import com.example.exceptions.InvalidExitTimeException;
import com.example.exceptions.RacetrackFullException;
import com.example.exceptions.TrackNotFoundException;

public interface IUserService {
    
    void bookRacetrack(VehicleType vehicleType,
        String vehicleNumber, 
            LocalTime entryTimeInHhMm) 
                    throws InvalidEntryTimeException, TrackNotFoundException, RacetrackFullException;

    void extendBooking(String vehicleNumber,
        LocalTime exitTimeInHhMm)
                throws InvalidExitTimeException, BookingNotFoundException, TrackNotFoundException, RacetrackFullException;
                
}
