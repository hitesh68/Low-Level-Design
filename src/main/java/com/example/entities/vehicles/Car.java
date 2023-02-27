package com.example.entities.vehicles;

import com.example.services.trackService.ITrackService;

public class Car extends AVehicleWithUpgradability{
    
    private static final VehicleType VEHICLE_TYPE = VehicleType.CAR;

    // *** PUBLIC

    public Car(
        String vehicleNumber, 
            ITrackService regularTrackService, 
                ITrackService vipTrackService
    ) {

        super(VEHICLE_TYPE, vehicleNumber, regularTrackService, vipTrackService);
    }
}