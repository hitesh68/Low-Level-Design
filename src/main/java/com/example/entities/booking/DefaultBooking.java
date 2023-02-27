package com.example.entities.booking;

import java.time.LocalTime;

import com.example.entities.racetrack.TrackType;
import com.example.entities.timeslot.ITimeslot;
import com.example.entities.vehicles.VehicleType;

public class DefaultBooking implements IBooking{
    
    private static final BookingType BOOKING_TYPE = BookingType.DEFAULT;
    private final TrackType trackType;
    private final VehicleType vehicleType;
    private final String vehicleNumber;
    private final ITimeslot bookedSlot;

    // *** PUBLIC

    public DefaultBooking(
        TrackType trackType,
            VehicleType vehicleType,
                String vehicleNumber,
                    ITimeslot bookedSlot
    ) {

        this.trackType = trackType;
        this.vehicleType = vehicleType;
        this.vehicleNumber = vehicleNumber;
        this.bookedSlot = bookedSlot;    
    }


    @Override
    public BookingType getBookingType() {
        // TODO Auto-generated method stub

        return BOOKING_TYPE;
    }

    @Override
    public TrackType getTrackType() {
        // TODO Auto-generated method stub

        return this.trackType;
    }

    @Override
    public VehicleType getVehicleType() {
        // TODO Auto-generated method stub

        return this.vehicleType;
    }

    @Override
    public String getVehicleNumber() {
        // TODO Auto-generated method stub

        return this.vehicleNumber;
    }

    @Override
    public LocalTime getEntryTime() {
        // TODO Auto-generated method stub

        return this.bookedSlot.getEntryTime();
    }

    @Override
    public LocalTime getExitTime() {
        // TODO Auto-generated method stub

        return this.bookedSlot.getExitTime();
    }

    @Override
    public int getDurationInMins() {
        // TODO Auto-generated method stub

        return this.bookedSlot.getDurationInMins();
    }

}
