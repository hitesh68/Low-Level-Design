package com.example.services.revenueService.revenueCalculator;

import com.example.entities.booking.IBooking;
import com.example.exceptions.TrackNotFoundException;

public interface IRevenueCalculator {
    
    float computeAmount(IBooking booking) throws TrackNotFoundException;
}
