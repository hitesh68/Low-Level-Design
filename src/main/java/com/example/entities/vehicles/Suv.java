package com.example.entities.vehicles;

import com.example.services.trackService.ITrackService;

public class Suv extends AVehicleWithUpgradability{
    
    private static final VehicleType VEHICLE_TYPE = VehicleType.SUV;

    // *** PUBLIC

    public Suv(
        String vehicleNumber, 
            ITrackService regularTrackService, 
                ITrackService vipTrackService
    ) {

        super(VEHICLE_TYPE, vehicleNumber, regularTrackService, vipTrackService);
    }
}
