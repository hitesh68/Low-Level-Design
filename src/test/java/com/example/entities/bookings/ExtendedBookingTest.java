package com.example.entities.bookings;

import java.time.LocalTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.entities.booking.BookingType;
import com.example.entities.booking.DefaultBooking;
import com.example.entities.booking.ExtendedBooking;
import com.example.entities.racetrack.TrackType;
import com.example.entities.timeslot.ITimeslot;
import com.example.entities.timeslot.Timeslot;
import com.example.entities.vehicles.VehicleType;

@ExtendWith(MockitoExtension.class)
public class ExtendedBookingTest {
    
    private TrackType trackType = TrackType.REGULAR;
    private VehicleType vehicleType = VehicleType.CAR;
    private String vehicleNumber = "M40";

    private LocalTime entryTime = LocalTime.parse("13:00");
    private LocalTime exitTime = LocalTime.parse("16:00");
    private ITimeslot bookedSlot = new Timeslot(entryTime, exitTime);

    private ITimeslot extendedSlot = new Timeslot(entryTime, exitTime);

    private DefaultBooking defaultBooking = new DefaultBooking(trackType, vehicleType, vehicleNumber, bookedSlot);
    private ExtendedBooking extendedBooking = new ExtendedBooking(defaultBooking,extendedSlot);

    @Test
    public void getBookingType_ShouldReturn_BookingType(){
        
        // Arrange, Act & Assert
        Assertions.assertEquals(BookingType.EXTENDED, extendedBooking.getBookingType());
    }

    @Test
    public void getTrackType_ShouldReturn_TrackType(){
        
        // Arrange, Act & Assert
        Assertions.assertEquals(defaultBooking.getTrackType(), extendedBooking.getTrackType());

    }

    @Test
    public void getVehicleType_ShouldReturn_VehicleType(){
        
        // Arrange, Act & Assert
        Assertions.assertEquals(defaultBooking.getVehicleType(), extendedBooking.getVehicleType());
    }

    @Test
    public void getVehicleNumber_ShouldReturn_VehicleNumber(){
        
        // Arrange, Act & Assert
        Assertions.assertEquals(defaultBooking.getVehicleNumber(), extendedBooking.getVehicleNumber());
    }

    @Test
    public void getEntryTime_ShouldReturn_EntryTime(){
        
        // Arrange, Act & Assert
        Assertions.assertEquals(defaultBooking.getEntryTime(), extendedBooking.getEntryTime());
    }

    @Test
    public void getExitTime_ShouldReturn_ExitTime(){
        
        // Arrange, Act & Assert
        Assertions.assertEquals(extendedSlot.getExitTime(), extendedBooking.getExitTime());
    }

    @Test
    public void getDurationInMins_ShouldReturn_DurationInMins(){
        
        // Arrange, Act & Assert
        Assertions.assertEquals(extendedSlot.getDurationInMins(), extendedBooking.getDurationInMins());
    }
}
