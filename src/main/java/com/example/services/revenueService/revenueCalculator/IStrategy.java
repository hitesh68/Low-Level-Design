package com.example.services.revenueService.revenueCalculator;

import java.util.concurrent.TimeUnit;

import com.example.entities.booking.IBooking;
import com.example.exceptions.TrackNotFoundException;

public interface IStrategy {
    
    int MINUTES_IN_HOUR = (int) TimeUnit.HOURS.toMinutes(1);
    
    float execute(IBooking booking) throws TrackNotFoundException;
}
