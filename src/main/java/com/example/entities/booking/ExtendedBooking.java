package com.example.entities.booking;

import java.time.LocalTime;

import com.example.entities.timeslot.ITimeslot;

public class ExtendedBooking extends ABookingDecorator{
    
    private static final BookingType BOOKING_TYPE = BookingType.EXTENDED;
    private final ITimeslot extendedSlot;

    // *** PUBLIC

    public ExtendedBooking(IBooking baseBooking, ITimeslot extendedSlot) {
        
        super(baseBooking);
        this.extendedSlot = extendedSlot;
    }

    @Override
    public BookingType getBookingType() {
        // TODO Auto-generated method stub
        
        return BOOKING_TYPE;
    }

    @Override
    public LocalTime getExitTime() {
        // TODO Auto-generated method stub

        return this.extendedSlot.getExitTime();
    }

    @Override
    public int getDurationInMins() {
        // TODO Auto-generated method stub

        return this.extendedSlot.getDurationInMins();
    }

    
}
