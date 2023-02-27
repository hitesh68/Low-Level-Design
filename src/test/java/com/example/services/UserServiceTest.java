package com.example.services;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.entities.booking.ExtendedBooking;
import com.example.entities.booking.IBooking;
import com.example.entities.vehicles.IVehicle;
import com.example.entities.vehicles.VehicleType;
import com.example.exceptions.BookingNotFoundException;
import com.example.exceptions.InvalidEntryTimeException;
import com.example.exceptions.InvalidExitTimeException;
import com.example.exceptions.RacetrackFullException;
import com.example.exceptions.TrackNotFoundException;
import com.example.services.bookingService.IBookingService;
import com.example.services.revenueService.IRevenueService;
import com.example.services.timingController.ITimingController;
import com.example.services.userService.UserService;
import com.example.services.vehicleFactory.IVehicleFactory;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private ITimingController timingControllerMock;

    @Mock
    private IVehicleFactory vehicleFactoryMock;

    @Mock
    private IRevenueService revenueServiceMock;

    @Mock
    private IBookingService bookingServiceMock;

    @Mock
    private IVehicle vehicleMock;

    @Mock
    private IBooking bookingMock;

    @InjectMocks
    private UserService userService;
    
    @Test
    public void bookRacetrack_ShouldThrow_InvalidEntryTimeException() throws Exception {

        // Arrange
        VehicleType vehicleType = VehicleType.BIKE;
        String vehicleNumber = "M40";
        LocalTime entryTime = LocalTime.parse("12:00");
        
        doThrow(new InvalidEntryTimeException("INVALID_ENTRY_TIME")).
            when(timingControllerMock).computeExitTime(entryTime);

        // Act & Assert
        Assertions.assertThrows(InvalidEntryTimeException.class,
            () -> userService.bookRacetrack(vehicleType, vehicleNumber, entryTime));

        verify(timingControllerMock).computeExitTime(entryTime);
        
    }

    @Test
    public void bookRacetrack_ShouldThrow_TrackNotFoundException() throws Exception {
        
        // Arrange
        VehicleType vehicleType = VehicleType.BIKE;
        String vehicleNumber = "M40";
        LocalTime entryTime = LocalTime.parse("13:00");
        LocalTime exitTime = LocalTime.parse("16:00");

        InOrder bookRacetrackExecutionOrder = Mockito.inOrder(
            timingControllerMock, vehicleFactoryMock, vehicleMock);

        when(timingControllerMock.computeExitTime(entryTime)).thenReturn(exitTime);
        when(vehicleFactoryMock.createVehicle(vehicleType, vehicleNumber)).thenReturn(vehicleMock);
        when(vehicleMock.bookSlot(entryTime, exitTime)).
            thenThrow(new TrackNotFoundException("TRACK_NOT_FOUND"));
        
        // Act & Assert
        Assertions.assertThrows(TrackNotFoundException.class,
            () -> userService.bookRacetrack(vehicleType, vehicleNumber, entryTime));

        bookRacetrackExecutionOrder.verify(timingControllerMock).computeExitTime(entryTime);
        bookRacetrackExecutionOrder.verify(vehicleFactoryMock).createVehicle(vehicleType, vehicleNumber);
        bookRacetrackExecutionOrder.verify(vehicleMock).bookSlot(entryTime, exitTime);
    }

    @Test
    public void bookRacetrack_ShouldThrow_RacetrackFullException() throws Exception {
        
        // Arrange
        VehicleType vehicleType = VehicleType.BIKE;
        String vehicleNumber = "M40";
        LocalTime entryTime = LocalTime.parse("13:00");
        LocalTime exitTime = LocalTime.parse("16:00");

        InOrder bookRacetrackExecutionOrder = Mockito.inOrder(
            timingControllerMock, vehicleFactoryMock, vehicleMock);

        when(timingControllerMock.computeExitTime(entryTime)).thenReturn(exitTime);
        when(vehicleFactoryMock.createVehicle(vehicleType, vehicleNumber)).thenReturn(vehicleMock);
        when(vehicleMock.bookSlot(entryTime, exitTime)).
            thenThrow(new RacetrackFullException("RACETRACK_FULL"));
        
        // Act & Assert
        Assertions.assertThrows(RacetrackFullException.class,
            () -> userService.bookRacetrack(vehicleType, vehicleNumber, entryTime));

        bookRacetrackExecutionOrder.verify(timingControllerMock).computeExitTime(entryTime);
        bookRacetrackExecutionOrder.verify(vehicleFactoryMock).createVehicle(vehicleType, vehicleNumber);
        bookRacetrackExecutionOrder.verify(vehicleMock).bookSlot(entryTime, exitTime);
    }

    @Test
    public void bookRacetrack_Should_bookSlot() throws Exception {
        
        // Arrange
        VehicleType vehicleType = VehicleType.BIKE;
        String vehicleNumber = "M40";
        LocalTime entryTime = LocalTime.parse("13:00");
        LocalTime exitTime = LocalTime.parse("16:00");

        InOrder bookRacetrackExecutionOrder = Mockito.inOrder(
            timingControllerMock, vehicleFactoryMock, vehicleMock, revenueServiceMock, bookingServiceMock);

        when(timingControllerMock.computeExitTime(entryTime)).thenReturn(exitTime);
        when(vehicleFactoryMock.createVehicle(vehicleType, vehicleNumber)).thenReturn(vehicleMock);
        when(vehicleMock.bookSlot(entryTime, exitTime)).thenReturn(bookingMock);
        
        // Act
        userService.bookRacetrack(vehicleType, vehicleNumber, entryTime);

        // Assert
        bookRacetrackExecutionOrder.verify(timingControllerMock).computeExitTime(entryTime);
        bookRacetrackExecutionOrder.verify(vehicleFactoryMock).createVehicle(vehicleType, vehicleNumber);
        bookRacetrackExecutionOrder.verify(vehicleMock).bookSlot(entryTime, exitTime);
        bookRacetrackExecutionOrder.verify(revenueServiceMock).collectRevenue(bookingMock);
        bookRacetrackExecutionOrder.verify(bookingServiceMock).addBooking(bookingMock);
        
    }

    // EXTENDED BOOKING
    
    @Test
    public void extendBooking_ShouldThrow_InvalidExitTimeException() throws Exception {

        // Arrange
        String vehicleNumber = "M40";
        LocalTime exitTime = LocalTime.parse("21:00");

        doThrow(new InvalidExitTimeException("INVALID_EXIT_TIME")).
            when(timingControllerMock).validateExitTime(exitTime);
        
        // Act & Assert
        Assertions.assertThrows(InvalidExitTimeException.class, 
            () -> userService.extendBooking(vehicleNumber, exitTime));
        verify(timingControllerMock).validateExitTime(exitTime);
    }

    @Test
    public void extendBooking_ShouldThrow_BookingNotFoundException() throws Exception {
        
        // Arrange
        String vehicleNumber = "M40";
        LocalTime exitTime = LocalTime.parse("20:00");

        InOrder extendBookingExecutionOrder = Mockito.inOrder(
            timingControllerMock, bookingServiceMock);

        doThrow(new BookingNotFoundException("BOOKING_NOT_FOUND")).
            when(bookingServiceMock).getBooking(vehicleNumber);

        // Act & Assert
        Assertions.assertThrows(BookingNotFoundException.class, 
            () -> userService.extendBooking(vehicleNumber, exitTime));

        extendBookingExecutionOrder.verify(timingControllerMock).validateExitTime(exitTime);
        extendBookingExecutionOrder.verify(bookingServiceMock).getBooking(vehicleNumber);

    }

    @Test
    public void extendBooking_ShouldThrow_TrackNotFoundException() throws Exception {
        
        // Arrange
        String vehicleNumber = "M40";
        LocalTime exitTime = LocalTime.parse("21:00");

        InOrder extendBookingExecutionOrder = Mockito.inOrder(
            timingControllerMock, bookingServiceMock, vehicleFactoryMock, vehicleMock);

        when(bookingServiceMock.getBooking(vehicleNumber)).thenReturn(bookingMock);

        when(vehicleFactoryMock.createVehicle(bookingMock.getVehicleType(), vehicleNumber)).
            thenReturn(vehicleMock);

        doThrow(new TrackNotFoundException("TRACK_NOT_FOUND")).
            when(vehicleMock).extendSlot(bookingMock, exitTime);
            
        // Act & Assert

        Assertions.assertThrows(TrackNotFoundException.class, 
            () -> userService.extendBooking(vehicleNumber, exitTime));

        extendBookingExecutionOrder.verify(timingControllerMock).validateExitTime(exitTime);
        extendBookingExecutionOrder.verify(bookingServiceMock).getBooking(vehicleNumber);
        extendBookingExecutionOrder.verify(vehicleFactoryMock).createVehicle(bookingMock.getVehicleType(), vehicleNumber);
        extendBookingExecutionOrder.verify(vehicleMock).extendSlot(bookingMock, exitTime);
    }

    @Test
    public void extendBooking_ShouldThrow_RacetrackFullException() throws Exception {
        
        // Arrange
        String vehicleNumber = "M40";
        LocalTime exitTime = LocalTime.parse("21:00");

        InOrder extendBookingExecutionOrder = Mockito.inOrder(
            timingControllerMock, bookingServiceMock, vehicleFactoryMock, vehicleMock);

        when(bookingServiceMock.getBooking(vehicleNumber)).thenReturn(bookingMock);

        when(vehicleFactoryMock.createVehicle(bookingMock.getVehicleType(), vehicleNumber)).
            thenReturn(vehicleMock);

        doThrow(new RacetrackFullException("RACETRACK_FULL")).
            when(vehicleMock).extendSlot(bookingMock, exitTime);
            
        // Act & Assert

        Assertions.assertThrows(RacetrackFullException.class, 
            () -> userService.extendBooking(vehicleNumber, exitTime));

        extendBookingExecutionOrder.verify(timingControllerMock).validateExitTime(exitTime);
        extendBookingExecutionOrder.verify(bookingServiceMock).getBooking(vehicleNumber);
        extendBookingExecutionOrder.verify(vehicleFactoryMock).createVehicle(bookingMock.getVehicleType(), vehicleNumber);
        extendBookingExecutionOrder.verify(vehicleMock).extendSlot(bookingMock, exitTime);
    }

    @Test
    public void extendBooking_Should_extendBooking() throws Exception {
        
        // Arrange
        String vehicleNumber = "M40";
        LocalTime exitTime = LocalTime.parse("17:00");
        IBooking extendedBookingMock = Mockito.mock(ExtendedBooking.class);

        InOrder extendBookingExecutionOrder = Mockito.inOrder(
            timingControllerMock, bookingServiceMock, vehicleFactoryMock, 
            vehicleMock, revenueServiceMock, bookingServiceMock);

        when(bookingServiceMock.getBooking(vehicleNumber)).thenReturn(bookingMock);
        when(vehicleFactoryMock.createVehicle(bookingMock.getVehicleType(), vehicleNumber)).
            thenReturn(vehicleMock);
        when(vehicleMock.extendSlot(bookingMock, exitTime)).thenReturn(extendedBookingMock);
        
        // Act
        userService.extendBooking(vehicleNumber, exitTime);

        // Assert
        extendBookingExecutionOrder.verify(timingControllerMock).validateExitTime(exitTime);
        extendBookingExecutionOrder.verify(bookingServiceMock).getBooking(vehicleNumber);
        extendBookingExecutionOrder.verify(vehicleFactoryMock).createVehicle(bookingMock.getVehicleType(), vehicleNumber);
        extendBookingExecutionOrder.verify(vehicleMock).extendSlot(bookingMock, exitTime);
        extendBookingExecutionOrder.verify(revenueServiceMock).collectRevenue(extendedBookingMock);
        extendBookingExecutionOrder.verify(bookingServiceMock).updateBooking(extendedBookingMock);
        
    }
}
