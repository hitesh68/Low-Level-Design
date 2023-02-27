package com.example.entities.vehicles;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
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
public class SuvTest {

    private String vehicleNumber = "M40";
    private VehicleType vehicleType = VehicleType.SUV;

    private ITrackService regularTrackServiceMock;
    private ITrackService vipTrackServiceMock;
    private Suv suv;

    private LocalTime entryTime = LocalTime.parse("13:00");
    private LocalTime exitTime = LocalTime.parse("16:00");

    private LocalTime extendedBookingEntryTime = exitTime;
    private LocalTime extendedBookingExitTime = LocalTime.parse("17:00");

    private ITimeslot extendedBookedSlot = new Timeslot(exitTime,extendedBookingExitTime);
    private ITimeslot bookedSlot = new Timeslot(entryTime, exitTime);

    public SuvTest() {

        this.regularTrackServiceMock = Mockito.mock(ITrackService.class);
        this.vipTrackServiceMock = Mockito.mock(ITrackService.class);
        this.suv = new Suv(vehicleNumber, regularTrackServiceMock, vipTrackServiceMock);
    }

    @Mock
    private DefaultBooking defaultBookingMock;
    
    @Test
    public void slotGotBooked_ByTheRegularTrackService_andReturned_defaultbooking() throws Exception {

        // Arrange

        when(regularTrackServiceMock.bookSlot(vehicleType, entryTime, exitTime)).thenReturn(bookedSlot);

        // Act
        IBooking actualBooking =  suv.bookSlot(entryTime, exitTime);

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
    public void slotGotBooked_ByTheVipTrackService_andReturned_defaultbooking_AfterCatching_RaceTrackFullException_OfRegularTrackService() throws Exception {

        // Arrange

        when(regularTrackServiceMock.bookSlot(vehicleType, entryTime, exitTime)).
            thenThrow(RacetrackFullException.class);

        when(vipTrackServiceMock.bookSlot(vehicleType, entryTime, exitTime)).thenReturn(bookedSlot);

        // Act
        IBooking actualBooking =  suv.bookSlot(entryTime, exitTime);

        // Assert
        Assertions.assertEquals(TrackType.VIP, actualBooking.getTrackType());
        Assertions.assertEquals(vehicleType, actualBooking.getVehicleType());
        Assertions.assertEquals(vehicleNumber, actualBooking.getVehicleNumber());
        Assertions.assertEquals(entryTime, actualBooking.getEntryTime());
        Assertions.assertEquals(exitTime, actualBooking.getExitTime());

        Assertions.assertThrows(RacetrackFullException.class, 
        () -> regularTrackServiceMock.bookSlot(vehicleType, entryTime, exitTime));

        Assertions.assertTrue(actualBooking instanceof DefaultBooking);
        verify(vipTrackServiceMock).bookSlot(vehicleType, entryTime, exitTime);
    }

    @Test
    public void bookSlot_ShouldThrow_TrackNotFoundException_OfRegularTrackService() throws Exception {

        // Arrange

        when(regularTrackServiceMock.bookSlot(vehicleType, entryTime, exitTime)).
            thenThrow(TrackNotFoundException.class);

        // Act & Assert
        Assertions.assertThrows(TrackNotFoundException.class, 
        () -> suv.bookSlot(entryTime, exitTime));

        verify(regularTrackServiceMock).bookSlot(vehicleType, entryTime, exitTime);
    }

    @Test
    public void bookSlot_ShouldThrow_TrackNotFoundException_OfVipTrackService() throws Exception {

        // Arrange
        
        when(regularTrackServiceMock.bookSlot(vehicleType, entryTime, exitTime)).
            thenThrow(RacetrackFullException.class);

        when(vipTrackServiceMock.bookSlot(vehicleType, entryTime, exitTime)).
            thenThrow(TrackNotFoundException.class);

        // Act & Assert
        Assertions.assertThrows(TrackNotFoundException.class, 
        () -> suv.bookSlot(entryTime, exitTime));

        InOrder bookSlotExecutionOrder = Mockito.inOrder(regularTrackServiceMock, vipTrackServiceMock);
        
        bookSlotExecutionOrder.verify(regularTrackServiceMock).bookSlot(vehicleType, entryTime, exitTime);
        bookSlotExecutionOrder.verify(vipTrackServiceMock).bookSlot(vehicleType, entryTime, exitTime);
    }

    @Test
    public void bookSlot_ShouldThrow_RaceTrackFullException_OfVipTrackService_AfterCatching_RaceTrackFullException_ThrownByRegularTrackService() throws Exception {

        // Arrange

        when(regularTrackServiceMock.bookSlot(vehicleType, entryTime, exitTime)).
            thenThrow(RacetrackFullException.class);

        when(vipTrackServiceMock.bookSlot(vehicleType, entryTime, exitTime)).
            thenThrow(RacetrackFullException.class);

        // Act & Assert
        Assertions.assertThrows(RacetrackFullException.class, 
        () -> suv.bookSlot(entryTime, exitTime));

        InOrder bookSlotExecutionOrder = Mockito.inOrder(regularTrackServiceMock, vipTrackServiceMock);
        
        bookSlotExecutionOrder.verify(regularTrackServiceMock).bookSlot(vehicleType, entryTime, exitTime);
        bookSlotExecutionOrder.verify(vipTrackServiceMock).bookSlot(vehicleType, entryTime, exitTime);
    }

    @Test
    public void extendSlot_ShouldBeDone_ByTheRegularTrackService_AndReturn_ExtendedBooking() throws Exception {

        // Arrange
        
        TrackType trackType = TrackType.REGULAR;
        DefaultBooking defaultBooking = new DefaultBooking(trackType, vehicleType, vehicleNumber, bookedSlot);

        when(regularTrackServiceMock.bookSlot(vehicleType, extendedBookingEntryTime, extendedBookingExitTime)).
            thenReturn(extendedBookedSlot);

        // Act
        IBooking actualBooking =  suv.extendSlot(defaultBooking, extendedBookingExitTime);

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
    public void extendSlot_ShouldBeDone_ByTheVipTrackService_AndReturn_ExtendedBooking() throws Exception {
        
        // Arrange
        
        TrackType trackType = TrackType.VIP;
        DefaultBooking defaultBooking = new DefaultBooking(trackType, vehicleType, vehicleNumber, bookedSlot);

        when(vipTrackServiceMock.bookSlot(vehicleType, extendedBookingEntryTime, extendedBookingExitTime)).
            thenReturn(extendedBookedSlot);

        // Act
        IBooking actualBooking =  suv.extendSlot(defaultBooking, extendedBookingExitTime);

        // Assert
        Assertions.assertEquals(TrackType.VIP, actualBooking.getTrackType());
        Assertions.assertEquals(vehicleType, actualBooking.getVehicleType());
        Assertions.assertEquals(vehicleNumber, actualBooking.getVehicleNumber());
        Assertions.assertEquals(entryTime, actualBooking.getEntryTime());
        Assertions.assertEquals(extendedBookingExitTime, actualBooking.getExitTime());
        Assertions.assertEquals(60, actualBooking.getDurationInMins());

        verify(vipTrackServiceMock).bookSlot(vehicleType, extendedBookingEntryTime, extendedBookingExitTime);
        Assertions.assertTrue(actualBooking instanceof ExtendedBooking);
    }

    @Test
    public void extendSlot_ShouldThrow_TrackNotFoundException_OfRegularTrackService() throws Exception {

        // Arrange
        
        TrackType trackType = TrackType.REGULAR;
        DefaultBooking defaultBooking = new DefaultBooking(trackType, vehicleType, vehicleNumber, bookedSlot);

        when(regularTrackServiceMock.bookSlot(vehicleType, extendedBookingEntryTime, extendedBookingExitTime)).
            thenThrow(TrackNotFoundException.class);

        // Act & Assert
        Assertions.assertThrows(TrackNotFoundException.class,
        () -> suv.extendSlot(defaultBooking, extendedBookingExitTime));

        verify(regularTrackServiceMock).
            bookSlot(vehicleType, extendedBookingEntryTime, extendedBookingExitTime);
    }

    @Test
    public void extendSlot_ShouldThrow_TrackNotFoundException_OfVipTrackService() throws Exception {

        // Arrange
        
        TrackType trackType = TrackType.VIP;
        DefaultBooking defaultBooking = new DefaultBooking(trackType, vehicleType, vehicleNumber, bookedSlot);

        when(vipTrackServiceMock.bookSlot(vehicleType, extendedBookingEntryTime, extendedBookingExitTime)).
            thenThrow(TrackNotFoundException.class);

        // Act & Assert
        Assertions.assertThrows(TrackNotFoundException.class,
        () -> suv.extendSlot(defaultBooking, extendedBookingExitTime));

        verify(vipTrackServiceMock).
            bookSlot(vehicleType, extendedBookingEntryTime, extendedBookingExitTime);
    }

    @Test
    public void extendSlot_ShouldThrow_RaceTrackFullException_OfRegularTrackService() throws Exception {

        // Arrange
        
        TrackType trackType = TrackType.REGULAR;
        DefaultBooking defaultBooking = new DefaultBooking(trackType, vehicleType, vehicleNumber, bookedSlot);

        when(regularTrackServiceMock.bookSlot(vehicleType, extendedBookingEntryTime, extendedBookingExitTime)).
            thenThrow(RacetrackFullException.class);

        // Act & Assert
        Assertions.assertThrows(RacetrackFullException.class,
        () -> suv.extendSlot(defaultBooking, extendedBookingExitTime));

        verify(regularTrackServiceMock).
            bookSlot(vehicleType, extendedBookingEntryTime, extendedBookingExitTime);
    }

    @Test
    public void extendSlot_ShouldThrow_RaceTrackFullException_OfVipTrackService() throws Exception {

        // Arrange
        
        TrackType trackType = TrackType.VIP;
        DefaultBooking defaultBooking = new DefaultBooking(trackType, vehicleType, vehicleNumber, bookedSlot);

        when(vipTrackServiceMock.bookSlot(vehicleType, extendedBookingEntryTime, extendedBookingExitTime)).
            thenThrow(RacetrackFullException.class);

        // Act & Assert
        Assertions.assertThrows(RacetrackFullException.class,
        () -> suv.extendSlot(defaultBooking, extendedBookingExitTime));

        verify(vipTrackServiceMock).
            bookSlot(vehicleType, extendedBookingEntryTime, extendedBookingExitTime);
    }

    @Test
    public void getVehicleType_ShouldReturn_VehicleType() {
        
        // Arrange Act Assert
        Assertions.assertEquals(vehicleType, suv.getVehicleType());
    }

    @Test
    public void getVehicleNumber_ShouldReturn_VehicleNumber() {
        
        // Arrange Act Assert
        Assertions.assertEquals(vehicleNumber, suv.getVehicleNumber());
    }
}
