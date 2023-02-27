package com.example.services.bookingService;

import com.example.entities.booking.IBooking;
import com.example.exceptions.BookingNotFoundException;

public interface IBookingService {

    void addBooking(IBooking booking);
    IBooking getBooking(String vehicleNumber) throws BookingNotFoundException;
    void updateBooking(IBooking booking);
}
