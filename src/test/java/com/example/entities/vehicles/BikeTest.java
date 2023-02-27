package com.example.entities.vehicles;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.entities.booking.DefaultBooking;
import com.example.entities.booking.ExtendedBooking;
import com.example.entities.booking.IBooking;
import com.example.entities.racetrack.TrackType;
import com.example.entities.timeslot.ITimeslot;
import com.example.entities.timeslot.Timeslot;
import com.example.exceptions.RacetrackFullException;
import com.example.exceptions.TrackNotFoundException;
import com.example.services.trackService.ITrackService;

@ExtendWith(MockitoExtension.class)
public class BikeTest {
    
    private String vehicleNumber = "M40";
    private VehicleType vehicleType = VehicleType.BIKE;

    private ITrackService regularTrackServiceMock;
    private Bike bike;

    private LocalTime entryTime = LocalTime.parse("13:00");
    private LocalTime exitTime = LocalTime.parse("16:00");
    private ITimeslot bookedSlot = new Timeslot(entryTime, exitTime);

    private LocalTime extendedBookingEntryTime = exitTime;
    private LocalTime extendedBookingExitTime = LocalTime.parse("17:00");
    private ITimeslot extendedBookedSlot = new Timeslot(exitTime,extendedBookingExitTime);

    public BikeTest() {

        this.regularTrackServiceMock = Mockito.mock(ITrackService.class);
        this.bike = new Bike(vehicleNumber, regularTrackServiceMock);
    }
    
    @Test
    public void bookSlot_ShouldThrow_TrackNotFoundException() throws Exception {
        
        // Arrange

        when(regularTrackServiceMock.bookSlot(vehicleType, entryTime, exitTime)).
            thenThrow(TrackNotFoundException.class);

        // Act & Assert
        Assertions.assertThrows(TrackNotFoundException.class, 
        () -> bike.bookSlot(entryTime, exitTime));

        verify(regularTrackServiceMock).bookSlot(vehicleType, entryTime, exitTime);
    }

    @Test
    public void bookSlot_ShouldThrow_RaceTrackFullException() throws Exception {
        
        when(regularTrackServiceMock.bookSlot(vehicleType, entryTime, exitTime)).
            thenThrow(RacetrackFullException.class);

        // Act & Assert
        Assertions.assertThrows(RacetrackFullException.class, 
        () -> bike.bookSlot(entryTime, exitTime));

        verify(regularTrackServiceMock).bookSlot(vehicleType, entryTime, exitTime);
    }

    @Test
    public void bookSlot_ShouldReturn_defaultBooking() throws Exception {
        
        // Arrange

        when(regularTrackServiceMock.bookSlot(vehicleType, entryTime, exitTime)).thenReturn(bookedSlot);

        // Act
        IBooking actualBooking =  bike.bookSlot(entryTime, exitTime);

        // Assert
        Assertions.assertEquals(TrackType.REGULAR, actualBooking.getTrackType());
        Assertions.assertEquals(vehicleType, actualBooking.getVehicleType());
        Assertions.assertEquals(vehicleNumber, actualBooking.getVehicleNumber());
        Assertions.assertEquals(entryTime, actualBooking.getEntryTime());
        Assertions.assertEquals(exitTime, actualBooking.getExitTime());

        verify(regularTrackServiceMock).bookSlot(vehicleType, entryTime, exitTime);
        Assertions.assertTrue(actualBooking instanceof DefaultBooking);

    }

    @Test
    public void extendSlot_ShouldThrow_TrackNotFoundException() throws Exception {
        
        // Arrange
        TrackType trackType = TrackType.REGULAR;
        DefaultBooking defaultBooking = new DefaultBooking(trackType, vehicleType, vehicleNumber, bookedSlot);

        when(regularTrackServiceMock.bookSlot(vehicleType, extendedBookingEntryTime, extendedBookingExitTime)).
            thenThrow(TrackNotFoundException.class);

        // Act & Assert
        Assertions.assertThrows(TrackNotFoundException.class,
        () -> bike.extendSlot(defaultBooking, extendedBookingExitTime));

        verify(regularTrackServiceMock).
            bookSlot(vehicleType, extendedBookingEntryTime, extendedBookingExitTime);
    }

    @Test
    public void extendSlot_ShouldThrow_RaceTrackFullException() throws Exception {
        
        // Arrange
        TrackType trackType = TrackType.REGULAR;
        DefaultBooking defaultBooking = new DefaultBooking(trackType, vehicleType, vehicleNumber, bookedSlot);

        when(regularTrackServiceMock.bookSlot(vehicleType, extendedBookingEntryTime, extendedBookingExitTime)).
            thenThrow(RacetrackFullException.class);

        // Act & Assert
        Assertions.assertThrows(RacetrackFullException.class,
        () -> bike.extendSlot(defaultBooking, extendedBookingExitTime));

        verify(regularTrackServiceMock).
            bookSlot(vehicleType, extendedBookingEntryTime, extendedBookingExitTime);
    }

    @Test
    public void extendSlot_ShouldReturn_extendedBooking() throws Exception {
        
        // Arrange
        TrackType trackType = TrackType.REGULAR;
        DefaultBooking defaultBooking = new DefaultBooking(trackType, vehicleType, vehicleNumber, bookedSlot);

        when(regularTrackServiceMock.bookSlot(vehicleType, extendedBookingEntryTime, extendedBookingExitTime)).
            thenReturn(extendedBookedSlot);

        // Act
        IBooking actualBooking =  bike.extendSlot(defaultBooking, extendedBookingExitTime);

        // Assert
        Assertions.assertEquals(TrackType.REGULAR, actualBooking.getTrackType());
        Assertions.assertEquals(vehicleType, actualBooking.getVehicleType());
        Assertions.assertEquals(vehicleNumber, actualBooking.getVehicleNumber());
        Assertions.assertEquals(entryTime, actualBooking.getEntryTime());
        Assertions.assertEquals(extendedBookingExitTime, actualBooking.getExitTime());
        Assertions.assertEquals(60, actualBooking.getDurationInMins());

        verify(regularTrackServiceMock).bookSlot(vehicleType, extendedBookingEntryTime, extendedBookingExitTime);
        Assertions.assertTrue(actualBooking instanceof ExtendedBooking);
    }

    @Test
    public void getVehicleType_ShouldReturn_VehicleType() {
        
        // Arrange Act Assert
        Assertions.assertEquals(vehicleType, bike.getVehicleType());
    }

    @Test
    public void getVehicleNumber_ShouldReturn_VehicleNumber() {
        
        // Arrange Act Assert
        Assertions.assertEquals(vehicleNumber, bike.getVehicleNumber());
    }
}
