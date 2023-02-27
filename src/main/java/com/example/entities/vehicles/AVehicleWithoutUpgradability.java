package com.example.entities.vehicles;

import java.time.LocalTime;

import com.example.entities.booking.DefaultBooking;
import com.example.entities.booking.ExtendedBooking;
import com.example.entities.booking.IBooking;
import com.example.entities.racetrack.TrackType;
import com.example.entities.timeslot.ITimeslot;
import com.example.exceptions.RacetrackFullException;
import com.example.exceptions.TrackNotFoundException;
import com.example.services.trackService.ITrackService;

public abstract class AVehicleWithoutUpgradability implements IVehicle {
    
    private final VehicleType vehicleType;
    private final String vehicleNumber;
    private final ITrackService regularTrackService;

    public AVehicleWithoutUpgradability(
        VehicleType vehicleType, 
            String vehicleNumber, 
                ITrackService regularTrackService) {

                    this.vehicleType = vehicleType;
                    this.vehicleNumber = vehicleNumber;
                    this.regularTrackService = regularTrackService;
    }
    
    @Override
    public final IBooking bookSlot(LocalTime entryTime, LocalTime exitTime)
            throws RacetrackFullException, TrackNotFoundException {
        
                ITimeslot bookedSlot = regularTrackService.bookSlot(getVehicleType(), entryTime, exitTime);

                IBooking defaultBooking = new DefaultBooking(
                    TrackType.REGULAR, getVehicleType(), getVehicleNumber(), bookedSlot);

                return defaultBooking;
    }

    @Override
    public final IBooking extendSlot(
        IBooking defaultBooking,
                LocalTime exitTime) 
                        throws RacetrackFullException, TrackNotFoundException {
                            
                            ITimeslot extendedSlot = regularTrackService.bookSlot(getVehicleType(), defaultBooking.getExitTime(), exitTime);
                            IBooking extendedBooking = new ExtendedBooking(defaultBooking, extendedSlot);
                            return extendedBooking;     
    }

    public VehicleType getVehicleType() {
        return this.vehicleType;
    }

    public String getVehicleNumber() {
        return this.vehicleNumber;
    }
}
