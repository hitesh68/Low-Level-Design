package com.example.services.vehicleFactory;

import com.example.entities.vehicles.Bike;
import com.example.entities.vehicles.Car;
import com.example.entities.vehicles.IVehicle;
import com.example.entities.vehicles.Suv;
import com.example.entities.vehicles.VehicleType;
import com.example.services.trackService.ITrackService;

public class VehicleFactory implements IVehicleFactory{
    
    private final ITrackService regularTrackService;
    private final ITrackService vipTrackService;

    // *** PUBLIC

    public VehicleFactory(
        ITrackService regularTrackService,
            ITrackService vipTrackService
    ) {

        this.regularTrackService = regularTrackService;
        this.vipTrackService = vipTrackService;
    }

    @Override
    public IVehicle createVehicle(VehicleType vehicleType, String vehicleNumber) {
        // TODO Auto-generated method stub

        switch(vehicleType) {

            case BIKE :
                return new Bike(vehicleNumber, regularTrackService);

            case CAR :
                return new Car(vehicleNumber, regularTrackService, vipTrackService);

            default :
                // FOR SUV
                return new Suv(vehicleNumber, regularTrackService, vipTrackService);

        }

    }

}
