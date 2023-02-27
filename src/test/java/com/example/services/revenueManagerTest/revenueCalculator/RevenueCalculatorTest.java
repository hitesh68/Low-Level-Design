package com.example.services.revenueManagerTest.revenueCalculator;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.entities.booking.BookingType;
import com.example.entities.booking.IBooking;
import com.example.exceptions.TrackNotFoundException;
import com.example.services.revenueService.revenueCalculator.DefaultRevenueCalculation;
import com.example.services.revenueService.revenueCalculator.ExtendedRevenueCalculation;
import com.example.services.revenueService.revenueCalculator.RevenueCalculator;

@ExtendWith(MockitoExtension.class)
public class RevenueCalculatorTest {

    private DefaultRevenueCalculation defaultRevenueCalculationMock;
    private ExtendedRevenueCalculation extendedRevenueCalculationMock;
    private RevenueCalculator revenueCalculator;

    @Mock
    private IBooking bookingMock;

    public RevenueCalculatorTest() {
        defaultRevenueCalculationMock = Mockito.mock(DefaultRevenueCalculation.class);
        extendedRevenueCalculationMock = Mockito.mock(ExtendedRevenueCalculation.class);
        revenueCalculator = new RevenueCalculator(defaultRevenueCalculationMock, extendedRevenueCalculationMock);
    }
    
    @Test
    public void computeAmount_ShouldUse_defaultRevenueCalculationStrategy_forDefaultBooking_andReturnAmount() throws Exception {
        
        // Arrange
        BookingType bookingType = BookingType.DEFAULT;
        float expectedAmount = 180f;

        when(bookingMock.getBookingType()).thenReturn(bookingType);
        when(defaultRevenueCalculationMock.execute(bookingMock)).thenReturn(expectedAmount);

        // Act
        float actualAmount = revenueCalculator.computeAmount(bookingMock);

        // Assert
        Assertions.assertEquals(expectedAmount, actualAmount);
        verify(bookingMock).getBookingType();
        verify(defaultRevenueCalculationMock).execute(bookingMock);
    }

    @Test
    public void computeAmount_ShouldUse_extendedRevenueCalculationStrategy_forExtendedBooking_andReturnAmount() throws Exception {
        
        // Arrange
        BookingType bookingType = BookingType.EXTENDED;
        float expectedAmount = 100f;

        when(bookingMock.getBookingType()).thenReturn(bookingType);
        when(extendedRevenueCalculationMock.execute(bookingMock)).thenReturn(expectedAmount);

        // Act
        float actualAmount = revenueCalculator.computeAmount(bookingMock);

        // Assert
        Assertions.assertEquals(expectedAmount, actualAmount);
        verify(bookingMock).getBookingType();
        verify(extendedRevenueCalculationMock).execute(bookingMock);
    }

    @Test
    public void computeAmount_ShouldThrow_TrackNotFoundException() throws Exception {

        // Arrange
        BookingType bookingType = BookingType.DEFAULT;

        when(bookingMock.getBookingType()).thenReturn(bookingType);
        when(defaultRevenueCalculationMock.execute(bookingMock)).thenThrow(TrackNotFoundException.class);

        // Act & Assert
        Assertions.assertThrows(TrackNotFoundException.class, 
            () -> revenueCalculator.computeAmount(bookingMock));

        verify(bookingMock).getBookingType();
    }
}