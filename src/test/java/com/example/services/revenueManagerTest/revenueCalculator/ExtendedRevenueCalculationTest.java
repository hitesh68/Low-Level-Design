package com.example.services.revenueManagerTest.revenueCalculator;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.entities.booking.IBooking;
import com.example.exceptions.TrackNotFoundException;
import com.example.services.revenueService.revenueCalculator.ExtendedRevenueCalculation;

@ExtendWith(MockitoExtension.class)
public class ExtendedRevenueCalculationTest {

    @Mock
    private IBooking bookingMock;

    private ExtendedRevenueCalculation extendedRevenueCalculation = new ExtendedRevenueCalculation(50f, 15);
    
    @Test
    public void execute_ShouldNotCharge_WhenExtraTime_isLessThanGiveAwayDuration()throws TrackNotFoundException {
        
        // Arrange
        int durationInMins = 10;
        float expectedAmount = 0;

        when(bookingMock.getDurationInMins()).thenReturn(durationInMins);

        // Act
        float actualAmount = extendedRevenueCalculation.execute(bookingMock);

        // Assert
        Assertions.assertEquals(expectedAmount, actualAmount);
        verify(bookingMock).getDurationInMins();
    }

    @Test
    public void execute_ShouldNotCharge_WhenExtraTime_EqualsGiveAwayDuration()throws TrackNotFoundException {
        
        // Arrange
        int durationInMins = 15;
        float expectedAmount = 0;

        when(bookingMock.getDurationInMins()).thenReturn(durationInMins);

        // Act
        float actualAmount = extendedRevenueCalculation.execute(bookingMock);

        // Assert
        Assertions.assertEquals(expectedAmount, actualAmount);
        verify(bookingMock).getDurationInMins();
    }

    @Test
    public void execute_WillCharge_ForTheWholeHour_WhenExtraTime_isMoreThanGiveAwayDuration_AndLessThanOneHour()throws TrackNotFoundException {
        
        // Arrange
        int durationInMins = 20;
        float expectedAmount = 50;

        when(bookingMock.getDurationInMins()).thenReturn(durationInMins);

        // Act
        float actualAmount = extendedRevenueCalculation.execute(bookingMock);

        // Assert
        Assertions.assertEquals(expectedAmount, actualAmount);
        verify(bookingMock).getDurationInMins();
    }

    @Test
    public void execute_WillCharge_ForTheWholeHour_WhenExtraTime_isMoreThanAnHour()throws TrackNotFoundException {
        
        // Arrange
        int durationInMins = 65;
        float expectedAmount = 100;

        when(bookingMock.getDurationInMins()).thenReturn(durationInMins);

        // Act
        float actualAmount = extendedRevenueCalculation.execute(bookingMock);

        // Assert
        Assertions.assertEquals(expectedAmount, actualAmount);
        verify(bookingMock).getDurationInMins();
    }

    @Test
    public void execute_WillCharge_WhenExtraTime_isEqualsHour()throws TrackNotFoundException {
        
        // Arrange
        int durationInMins = 60;
        float expectedAmount = 50;

        when(bookingMock.getDurationInMins()).thenReturn(durationInMins);

        // Act
        float actualAmount = extendedRevenueCalculation.execute(bookingMock);

        // Assert
        Assertions.assertEquals(expectedAmount, actualAmount);
        verify(bookingMock).getDurationInMins();
    }
}
