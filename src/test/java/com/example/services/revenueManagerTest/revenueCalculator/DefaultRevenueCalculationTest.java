package com.example.services.revenueManagerTest.revenueCalculator;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.entities.booking.IBooking;
import com.example.entities.racetrack.TrackType;
import com.example.entities.vehicles.VehicleType;
import com.example.exceptions.TrackNotFoundException;
import com.example.services.revenueService.revenueCalculator.DefaultRevenueCalculation;
import com.example.services.trackService.ITrackService;

@ExtendWith(MockitoExtension.class)
public class DefaultRevenueCalculationTest {
    
    private ITrackService regularTrackServiceMock;
    private ITrackService vipTrackServiceMock;
    private DefaultRevenueCalculation defaultRevenueCalculation;

    @Mock
    private IBooking bookingMock;

    public DefaultRevenueCalculationTest() {
        this.regularTrackServiceMock = Mockito.mock(ITrackService.class);
        this.vipTrackServiceMock = Mockito.mock(ITrackService.class);
        this.defaultRevenueCalculation = new DefaultRevenueCalculation(regularTrackServiceMock, vipTrackServiceMock);
    }

    @Test
    public void execute_WillReturnAmount_ByUsingRegularTracksService() throws TrackNotFoundException {
        
        // Arrange
        TrackType trackType = TrackType.REGULAR;
        VehicleType vehicleType = VehicleType.BIKE;

        int durationInMins = 180;
        float tracksCostPerHour = 60f;
        float expectedAmount = 180f;

        when(bookingMock.getTrackType()).thenReturn(trackType);
        when(bookingMock.getVehicleType()).thenReturn(vehicleType);
        when(bookingMock.getDurationInMins()).thenReturn(durationInMins);
        when(regularTrackServiceMock.getCostPerHour(vehicleType)).thenReturn(tracksCostPerHour);

        // Act
        float actualAmount = defaultRevenueCalculation.execute(bookingMock);

        // Assert
        Assertions.assertEquals(expectedAmount, actualAmount);
        verify(bookingMock).getTrackType();
        verify(bookingMock).getVehicleType();
        verify(regularTrackServiceMock).getCostPerHour(vehicleType);
        verify(bookingMock).getDurationInMins();
    }

    @Test
    public void execute_WillReturnAmount_ByUsingVipTracksService() throws TrackNotFoundException {
        
        // Arrange
        TrackType trackType = TrackType.VIP;
        VehicleType vehicleType = VehicleType.CAR;

        int durationInMins = 180;
        float tracksCostPerHour = 120f;
        float expectedAmount = 360f;

        when(bookingMock.getTrackType()).thenReturn(trackType);
        when(bookingMock.getVehicleType()).thenReturn(vehicleType);
        when(bookingMock.getDurationInMins()).thenReturn(durationInMins);
        when(vipTrackServiceMock.getCostPerHour(vehicleType)).thenReturn(tracksCostPerHour);

        // Act
        float actualAmount = defaultRevenueCalculation.execute(bookingMock);

        // Assert
        Assertions.assertEquals(expectedAmount, actualAmount);
        verify(bookingMock).getTrackType();
        verify(bookingMock).getVehicleType();
        verify(vipTrackServiceMock).getCostPerHour(vehicleType);
        verify(bookingMock).getDurationInMins();
    }

    @Test
    public void execute_WillThrow_TrackNotFoundException_OfRegularTracksService() throws TrackNotFoundException {
        
        // Arrange
        TrackType trackType = TrackType.REGULAR;
        VehicleType vehicleType = VehicleType.CAR;
        
        when(bookingMock.getTrackType()).thenReturn(trackType);
        when(bookingMock.getVehicleType()).thenReturn(vehicleType);
        when(regularTrackServiceMock.getCostPerHour(vehicleType)).
            thenThrow(TrackNotFoundException.class);

        // Act & Assert
        Assertions.assertThrows(TrackNotFoundException.class, 
            () -> defaultRevenueCalculation.execute(bookingMock));

        verify(bookingMock).getTrackType();
        verify(bookingMock).getVehicleType();
        verify(regularTrackServiceMock).getCostPerHour(vehicleType);
    }

    @Test
    public void execute_WillThrow_TrackNotFoundException_OfVipTracksService() throws TrackNotFoundException {
        
        // Arrange
        TrackType trackType = TrackType.VIP;
        VehicleType vehicleType = VehicleType.CAR;
        
        when(bookingMock.getTrackType()).thenReturn(trackType);
        when(bookingMock.getVehicleType()).thenReturn(vehicleType);
        when(vipTrackServiceMock.getCostPerHour(vehicleType)).
            thenThrow(TrackNotFoundException.class);

        // Act & Assert
        Assertions.assertThrows(TrackNotFoundException.class, 
            () -> defaultRevenueCalculation.execute(bookingMock));

        verify(bookingMock).getTrackType();
        verify(bookingMock).getVehicleType();
        verify(vipTrackServiceMock).getCostPerHour(vehicleType);
    }
}
