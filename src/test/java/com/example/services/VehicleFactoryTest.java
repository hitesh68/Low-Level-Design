package com.example.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.entities.vehicles.Bike;
import com.example.entities.vehicles.Car;
import com.example.entities.vehicles.IVehicle;
import com.example.entities.vehicles.Suv;
import com.example.entities.vehicles.VehicleType;
import com.example.services.trackService.ITrackService;
import com.example.services.vehicleFactory.VehicleFactory;

@ExtendWith(MockitoExtension.class)
public class VehicleFactoryTest {

    @Mock
    private ITrackService regularTrackService;

    @Mock
    private ITrackService vipTrackService;

    @InjectMocks
    private VehicleFactory vehicleFactory;
    
    @Test
    public void createVehicle_ShouldCreate_Bike() {
        
        // Arrange
        String vehicleNumber = "M40";

        // Act
        IVehicle vehicle = vehicleFactory.createVehicle(VehicleType.BIKE, vehicleNumber);

        // Assert
        Assertions.assertTrue(vehicle instanceof Bike);
        Assertions.assertEquals(vehicle.getVehicleNumber(), vehicleNumber);
    }

    @Test
    public void createVehicle_ShouldCreate_Car() {
        
        // Arrange
        String vehicleNumber = "M40";

        // Act
        IVehicle vehicle = vehicleFactory.createVehicle(VehicleType.CAR, vehicleNumber);

        // Assert
        Assertions.assertTrue(vehicle instanceof Car);
        Assertions.assertEquals(vehicle.getVehicleNumber(), vehicleNumber);
    }

    @Test
    public void createVehicle_ShouldCreate_Suv() {
        
        // Arrange
        String vehicleNumber = "M40";

        // Act
        IVehicle vehicle = vehicleFactory.createVehicle(VehicleType.SUV, vehicleNumber);

        // Assert
        Assertions.assertTrue(vehicle instanceof Suv);
        Assertions.assertEquals(vehicle.getVehicleNumber(), vehicleNumber);
    }
}
