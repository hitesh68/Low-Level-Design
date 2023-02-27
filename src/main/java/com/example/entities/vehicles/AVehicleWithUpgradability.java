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

public abstract class AVehicleWithUpgradability implements IVehicle {
    
    private final VehicleType vehicleType;
    private final String vehicleNumber;
    private final ITrackService regularTrackService;
    private final ITrackService vipTrackService;

    public AVehicleWithUpgradability(
        VehicleType vehicleType, 
            String vehicleNumber, 
                ITrackService regularTrackService,
                    ITrackService vipTrackService) {

                    this.vehicleType = vehicleType;
                    this.vehicleNumber = vehicleNumber;
                    this.regularTrackService = regularTrackService;
                    this.vipTrackService = vipTrackService;
    }

    @Override
    public final IBooking bookSlot(LocalTime entryTime, LocalTime exitTime) 
        throws RacetrackFullException, TrackNotFoundException {
        
            TrackType trackType = TrackType.REGULAR;
            ITimeslot bookedSlot;

            try{

                bookedSlot = getbookedSlot(trackType, entryTime, exitTime);
                
            }catch(RacetrackFullException exception) {

                trackType = TrackType.VIP;
                bookedSlot = getbookedSlot(trackType, entryTime, exitTime);
            }

            IBooking defaultBooking = new DefaultBooking(
                    trackType, getVehicleType(), getVehicleNumber() , bookedSlot);

            return defaultBooking;
    }
    
    @Override
    public final IBooking extendSlot(
        IBooking defaultBooking, 
                LocalTime exitTime) 
                    throws RacetrackFullException, TrackNotFoundException {

                        ITimeslot extendedSlot = getbookedSlot(defaultBooking.getTrackType(), 
                            defaultBooking.getExitTime(), exitTime);
                        IBooking extendedBooking = new ExtendedBooking(defaultBooking, extendedSlot);
                        return extendedBooking;
    }

    public final VehicleType getVehicleType() {
        return this.vehicleType;
    }

    public final String getVehicleNumber() {
        return this.vehicleNumber;
    }

    private ITimeslot getbookedSlot(TrackType trackType, LocalTime entryTime, LocalTime exitTime)
        throws RacetrackFullException, TrackNotFoundException {

        ITrackService trackService;

        if(trackType.isRegular()){
            trackService = regularTrackService;
        } else {
            trackService = vipTrackService;
        }

        return trackService.bookSlot(getVehicleType(), entryTime, exitTime);
    }
}
