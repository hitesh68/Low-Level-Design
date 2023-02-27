package com.example.services.revenueService;

import com.example.entities.booking.IBooking;
import com.example.entities.racetrack.TrackType;
import com.example.exceptions.TrackNotFoundException;

public interface IRevenueService {
    
    void collectRevenue(IBooking booking) throws TrackNotFoundException;
    float getTotalAmount(TrackType trackType);
}
