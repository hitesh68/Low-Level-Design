package com.example.entities.vehicles;

import com.example.services.trackService.ITrackService;

public class Bike extends AVehicleWithoutUpgradability{
    
    private static final VehicleType VEHICLE_TYPE = VehicleType.BIKE;

    // *** PUBLIC

    public Bike(String vehicleNumber, ITrackService regularTrackService) {

        super(VEHICLE_TYPE, vehicleNumber, regularTrackService);
    }
}
