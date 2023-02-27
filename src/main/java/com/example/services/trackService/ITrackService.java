package com.example.services.trackService;

import java.time.LocalTime;

import com.example.entities.timeslot.ITimeslot;
import com.example.entities.vehicles.VehicleType;
import com.example.exceptions.RacetrackFullException;
import com.example.exceptions.TrackNotFoundException;

public interface ITrackService {
    
    ITimeslot bookSlot(VehicleType vehicleType,
        LocalTime entryTime,
            LocalTime exitTime)
                    throws RacetrackFullException, TrackNotFoundException;

    float getCostPerHour(VehicleType vehicleType) throws TrackNotFoundException;
}
