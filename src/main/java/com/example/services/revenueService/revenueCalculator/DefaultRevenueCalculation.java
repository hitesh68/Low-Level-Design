package com.example.services.revenueService.revenueCalculator;

import com.example.entities.booking.IBooking;
import com.example.entities.racetrack.TrackType;
import com.example.entities.vehicles.VehicleType;
import com.example.exceptions.TrackNotFoundException;
import com.example.services.trackService.ITrackService;

public class DefaultRevenueCalculation implements IStrategy{
    
    private final ITrackService regularTrackService;
    private final ITrackService vipTrackService;

    // *** PUBLIC

    public DefaultRevenueCalculation(
        ITrackService regularTrackService,
            ITrackService vipTrackService
    ) {
        this.regularTrackService = regularTrackService;
        this.vipTrackService = vipTrackService;
    }

    @Override
    public float execute(IBooking booking) throws TrackNotFoundException{
        // TODO Auto-generated method stub

        TrackType trackType = booking.getTrackType();
        VehicleType vehicleType = booking.getVehicleType();

        int totalHour = computeHour(booking.getDurationInMins());
        float costPerHour = getCostPerHour(trackType,vehicleType);

        float amount = totalHour * costPerHour;
        return amount;
    }

    // *** PRIVATE

    private float getCostPerHour(TrackType trackType, VehicleType vehicleType) throws TrackNotFoundException{

        ITrackService trackService = getTrackService(trackType);
        float costPerHour = trackService.getCostPerHour(vehicleType);

        return costPerHour;
    }

    private int computeHour(int durationInMins) {

        return durationInMins/MINUTES_IN_HOUR;
    }

    private ITrackService getTrackService(TrackType trackType){

        if(trackType.isRegular()){
            return regularTrackService;
        } else {
            return vipTrackService;
        }
    }
    
}
