package com.example.services.bookingService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.example.entities.booking.IBooking;
import com.example.exceptions.BookingNotFoundException;

public class BookingManager implements IBookingService{
    
    private final Map<String,IBooking> bookingMap;

    // *** PUBLIC

    public BookingManager(HashMap<String,IBooking> bookingMap){
        this.bookingMap = bookingMap;
    }

    public BookingManager(){
        this.bookingMap = new HashMap<>();
    }

    @Override
    public void addBooking(IBooking booking) {
        // TODO Auto-generated method stub

        bookingMap.put(booking.getVehicleNumber(), booking);
    }

    @Override
    public IBooking getBooking(String vehicleNumber) throws BookingNotFoundException {
        // TODO Auto-generated method stub

        Optional<IBooking> optionalBooking = Optional.ofNullable(bookingMap.get(vehicleNumber));
        return optionalBooking.orElseThrow(
            () ->
                new BookingNotFoundException("BOOKING_NOT_FOUND for Vehicle :" + vehicleNumber)
        );

    }

    @Override
    public void updateBooking(IBooking booking) {
        // TODO Auto-generated method stub

        // if the mapping exist, then it will replace, otherwise it will do nothing and return null
        bookingMap.replace(booking.getVehicleNumber(), booking);
    }

}
