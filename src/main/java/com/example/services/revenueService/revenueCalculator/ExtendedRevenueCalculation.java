package com.example.services.revenueService.revenueCalculator;

import com.example.entities.booking.IBooking;
import com.example.exceptions.TrackNotFoundException;

public class ExtendedRevenueCalculation implements IStrategy{
    
    private final float costPerHour;
    private final int giveAwayDurationInMins;

    // *** PUBLIC

    public ExtendedRevenueCalculation(
        float costPerHour,
            int giveAwayDurationInMins
    ) {
        this.costPerHour = costPerHour;
        this.giveAwayDurationInMins = giveAwayDurationInMins;
    }

    @Override
    public float execute(IBooking booking) throws TrackNotFoundException{
        // TODO Auto-generated method stub

        final int totalHour = computeHour(booking.getDurationInMins());
        final float amount = totalHour * getCostPerHour();

        return amount;
    } 

    // *** PRIVATE

    private int computeHour(int durationInMins) {

        if(shouldGiveAway(durationInMins)) {
            return 0;
        }
        
        return (int) Math.ceil((float)durationInMins / MINUTES_IN_HOUR);
    }
    
    private int getGiveAwayDurationInMins() {
        return this.giveAwayDurationInMins;
    }

    private float getCostPerHour() {
        return this.costPerHour;
    }

    private boolean shouldGiveAway(int durationInMins){

        return (durationInMins < getGiveAwayDurationInMins() || durationInMins == getGiveAwayDurationInMins());
    }
}
