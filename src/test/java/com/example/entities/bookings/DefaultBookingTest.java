package com.example.entities.bookings;

import java.time.LocalTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.entities.booking.BookingType;
import com.example.entities.booking.DefaultBooking;
import com.example.entities.racetrack.TrackType;
import com.example.entities.timeslot.ITimeslot;
import com.example.entities.timeslot.Timeslot;
import com.example.entities.vehicles.VehicleType;

@ExtendWith(MockitoExtension.class)
public class DefaultBookingTest {

    private TrackType trackType = TrackType.REGULAR;
    private VehicleType vehicleType = VehicleType.CAR;
    private String vehicleNumber = "M40";

    private LocalTime entryTime = LocalTime.parse("13:00");
    private LocalTime exitTime = LocalTime.parse("16:00");
    private ITimeslot bookedSlot = new Timeslot(entryTime, exitTime);

    private DefaultBooking defaultBooking = new DefaultBooking(trackType, vehicleType, vehicleNumber, bookedSlot);

    @Test
    public void getBookingType_ShouldReturn_BookingType(){
        
        // Arrange, Act & Assert
        Assertions.assertEquals(BookingType.DEFAULT, defaultBooking.getBookingType());
    }

    @Test
    public void getTrackType_ShouldReturn_TrackType(){
        
        // Arrange, Act & Assert
        Assertions.assertEquals(trackType, defaultBooking.getTrackType());

    }

    @Test
    public void getVehicleType_ShouldReturn_VehicleType(){
        
        // Arrange, Act & Assert
        Assertions.assertEquals(vehicleType, defaultBooking.getVehicleType());
    }

    @Test
    public void getVehicleNumber_ShouldReturn_VehicleNumber(){
        
        // Arrange, Act & Assert
        Assertions.assertEquals(vehicleNumber, defaultBooking.getVehicleNumber());
    }

    @Test
    public void getEntryTime_ShouldReturn_EntryTime(){
        
        // Arrange, Act & Assert
        Assertions.assertEquals(entryTime, defaultBooking.getEntryTime());
    }

    @Test
    public void getExitTime_ShouldReturn_ExitTime(){
        
        // Arrange, Act & Assert
        Assertions.assertEquals(exitTime, defaultBooking.getExitTime());
    }

    @Test
    public void getDurationInMins_ShouldReturn_DurationInMins(){

        // Arrange, Act & Assert
        Assertions.assertEquals(180, defaultBooking.getDurationInMins());
    }
}
