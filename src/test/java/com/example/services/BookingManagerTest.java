package com.example.services;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.entities.booking.DefaultBooking;
import com.example.entities.booking.ExtendedBooking;
import com.example.entities.booking.IBooking;
import com.example.entities.timeslot.ITimeslot;
import com.example.exceptions.BookingNotFoundException;
import com.example.services.bookingService.BookingManager;

@ExtendWith(MockitoExtension.class)
public class BookingManagerTest {

    private HashMap<String,IBooking> bookingMap = new HashMap<>();
    private BookingManager bookingManager = new BookingManager(bookingMap);

    @Mock
    private IBooking bookingMock;

    @Test
    public void addBooking_Should_AddTheBooking() {
        
        // Arrange
        String vehicleNumber = "Mock1";
        when(bookingMock.getVehicleNumber()).thenReturn(vehicleNumber);

        // Act
        bookingManager.addBooking(bookingMock);

        // Assert
        Assertions.assertEquals(bookingMap.get(vehicleNumber), bookingMock);
    }

    @Test
    public void getBooking_ShouldGet_AndReturnTheBooking() throws BookingNotFoundException {
        
        // Arrange
        String vehicleNumber = "Mock2";

        IBooking expectedBooking = Mockito.mock(IBooking.class);
        bookingMap.put(vehicleNumber, expectedBooking);

        // Act
        IBooking actualBooking = bookingManager.getBooking(vehicleNumber);

        // Assert
        Assertions.assertEquals(expectedBooking, actualBooking);
    }

    @Test
    public void getBooking_ShouldThrow_BookingNotFoundException() throws BookingNotFoundException {
        
        // Arrange
        String vehicleNumber = "Mock2";

        // Act & Assert
        Assertions.assertThrows(BookingNotFoundException.class,
            () -> bookingManager.getBooking(vehicleNumber));
    }

    @Test
    public void updateBooking_ShouldUpdate_TheBooking() {
        
        // Arrange
        String vehicleNumber = "Mock2";

        IBooking defaultBookingMock = Mockito.mock(DefaultBooking.class);
        when(defaultBookingMock.getVehicleNumber()).thenReturn(vehicleNumber);
        bookingMap.put(vehicleNumber, defaultBookingMock);

        IBooking extendedBooking = new ExtendedBooking(defaultBookingMock, mock(ITimeslot.class));

        // Act
        bookingManager.updateBooking(extendedBooking);

        // Assert
        Assertions.assertEquals(extendedBooking, bookingMap.get(vehicleNumber));
        verify(defaultBookingMock).getVehicleNumber();
    }
}
