package com.example.services.vehicleFactory;

import com.example.entities.vehicles.IVehicle;
import com.example.entities.vehicles.VehicleType;

public interface IVehicleFactory {
    
    IVehicle createVehicle(VehicleType vehicleType, String vehicleNumber);
}
