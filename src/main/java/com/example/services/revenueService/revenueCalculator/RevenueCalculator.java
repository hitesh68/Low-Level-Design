package com.example.services.revenueService.revenueCalculator;

import com.example.entities.booking.BookingType;
import com.example.entities.booking.IBooking;
import com.example.exceptions.TrackNotFoundException;

public class RevenueCalculator implements IRevenueCalculator{
    
    private final IStrategy defaultRevenueCalculation;
    private final IStrategy extendedRevenueCalculation;

    // *** PUBLIC

    public RevenueCalculator(
        IStrategy defaultRevenueCalculation,
            IStrategy extendedRevenueCalculation
    ) {

        this.defaultRevenueCalculation = defaultRevenueCalculation;
        this.extendedRevenueCalculation = extendedRevenueCalculation;
    }

    @Override        
    public float computeAmount(IBooking booking) throws TrackNotFoundException{
        // TODO Auto-generated method stub

        IStrategy revenueCalculationStrategy = getStrategy(booking.getBookingType());
        float amount = revenueCalculationStrategy.execute(booking);

        return amount;
    }

    private IStrategy getStrategy(BookingType bookingType){

        if(bookingType.isDefault()){
            return this.defaultRevenueCalculation;
        } else {
            return this.extendedRevenueCalculation;
        }
    }

}
