package com.example.services.revenueManagerTest;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.entities.booking.IBooking;
import com.example.entities.racetrack.TrackType;
import com.example.entities.revenue.IRevenue;
import com.example.exceptions.TrackNotFoundException;
import com.example.services.revenueService.RevenueManager;
import com.example.services.revenueService.revenueCalculator.IRevenueCalculator;

@ExtendWith(MockitoExtension.class)
public class RevenueManagerTest {
    
    @Mock
    private HashMap<TrackType,IRevenue> revenueMapMock;

    @Mock
    private IRevenueCalculator revenueCalculatorMock;

    @Mock 
    private IBooking bookingMock;

    @Mock
    private IRevenue revenueMock;

    @InjectMocks 
    RevenueManager revenueManager;

    @Test
    public void collectRevenue_ShouldThrow_TrackNotFoundException() throws Exception {
        
        // Arrange
        when(revenueCalculatorMock.computeAmount(bookingMock)).
            thenThrow(TrackNotFoundException.class);

        // Act & Assert
        Assertions.assertThrows(TrackNotFoundException.class, 
            () -> revenueManager.collectRevenue(bookingMock));

        verify(revenueCalculatorMock).computeAmount(bookingMock);
        
    }

    @Test
    public void collectRevenue_ShouldAddAmount_ToRespectiveTracksType_Revenue() throws Exception {
        
        // Arrange
        final float amount = 180;
        TrackType trackType = TrackType.REGULAR;

        when(revenueCalculatorMock.computeAmount(bookingMock)).thenReturn(amount);
        when(bookingMock.getTrackType()).thenReturn(trackType);
        when(revenueMapMock.get(trackType)).thenReturn(revenueMock);

        // Act
        revenueManager.collectRevenue(bookingMock);

        // Assert
        verify(revenueCalculatorMock).computeAmount(bookingMock);
        verify(bookingMock).getTrackType();
        verify(revenueMapMock).get(trackType);
        verify(revenueMock).addAmount(amount);
    }

    @Test
    public void getTotalAmount_ShouldReturn_RespectiveTracksType_TotalAmount(){
        
        // Arrange
        final float expectedAmount = 180;
        TrackType trackType = TrackType.REGULAR;

        when(revenueMapMock.get(trackType)).thenReturn(revenueMock);
        when(revenueMock.getTotalAmount()).thenReturn(expectedAmount);

        // Act
        float actualAmount = revenueManager.getTotalAmount(trackType);

        // Assert
        Assertions.assertEquals(expectedAmount, actualAmount);

        InOrder getTotalAmountExecutionOrder = Mockito.inOrder(revenueMapMock, revenueMock);
        getTotalAmountExecutionOrder.verify(revenueMapMock).get(trackType);
        getTotalAmountExecutionOrder.verify(revenueMock).getTotalAmount();
        
    }

}