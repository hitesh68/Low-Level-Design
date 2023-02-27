package com.example.services;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalTime;
import java.util.HashMap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.entities.racetrack.ITrack;
import com.example.entities.racetrack.TrackType;
import com.example.entities.timeslot.ITimeslot;
import com.example.entities.timeslot.Timeslot;
import com.example.entities.vehicles.VehicleType;
import com.example.exceptions.RacetrackFullException;
import com.example.exceptions.TrackNotFoundException;
import com.example.services.trackService.RacetrackManager;

@ExtendWith(MockitoExtension.class)
public class RacetrackManagerTest {

    private HashMap<VehicleType,ITrack> trackMapMock;
    private RacetrackManager racetrackManager;
    
    public RacetrackManagerTest() {
        this.trackMapMock = Mockito.mock(HashMap.class);
        this.racetrackManager = new RacetrackManager(TrackType.REGULAR, trackMapMock);
    }
    @Test
    public void bookSlot_ShouldThrow_TrackNotFoundException() throws Exception {
        
        // Arrange
        VehicleType vehicleType = VehicleType.BIKE;
        LocalTime entryTime = LocalTime.parse("13:00");
        LocalTime exitTime = LocalTime.parse("16:00");

        when(trackMapMock.get(vehicleType)).thenReturn(null);

        // Act & Assert
        Assertions.assertThrows(TrackNotFoundException.class, 
            () -> racetrackManager.bookSlot(vehicleType, entryTime, exitTime));
        
        verify(trackMapMock).get(vehicleType);
    }

    @Test
    public void bookSlot_ShouldThrow_RaceTrackFullException() throws Exception {
        
        // Arrange
        VehicleType vehicleType = VehicleType.BIKE;
        LocalTime entryTime = LocalTime.parse("13:00");
        LocalTime exitTime = LocalTime.parse("16:00");

        ITrack raceTrackMock = Mockito.mock(ITrack.class);

        when(trackMapMock.get(vehicleType)).thenReturn(raceTrackMock);
        when(raceTrackMock.bookSlot(entryTime, exitTime)).
            thenThrow(RacetrackFullException.class);

        // Act & Assert
        Assertions.assertThrows(RacetrackFullException.class, 
            () -> racetrackManager.bookSlot(vehicleType, entryTime, exitTime));
        
        InOrder bookSlotExecutionOrder = Mockito.inOrder(trackMapMock, raceTrackMock);
        bookSlotExecutionOrder.verify(trackMapMock).get(vehicleType);
        bookSlotExecutionOrder.verify(raceTrackMock).bookSlot(entryTime, exitTime);
    }

    @Test
    public void bookSlot_ShouldReturn_bookedSlot() throws Exception {
        
        // Arrange
        VehicleType vehicleType = VehicleType.BIKE;
        LocalTime entryTime = LocalTime.parse("13:00");
        LocalTime exitTime = LocalTime.parse("16:00");

        ITrack raceTrackMock = Mockito.mock(ITrack.class);
        ITimeslot expectedSlot = new Timeslot(entryTime, exitTime);

        when(trackMapMock.get(vehicleType)).thenReturn(raceTrackMock);
        when(raceTrackMock.bookSlot(entryTime, exitTime)).thenReturn(expectedSlot);

        // Act 
        ITimeslot actualSlot = racetrackManager.bookSlot(vehicleType, entryTime, exitTime);
        
        // Assert
        InOrder bookSlotExecutionOrder = Mockito.inOrder(trackMapMock, raceTrackMock);
        bookSlotExecutionOrder.verify(trackMapMock).get(vehicleType);
        bookSlotExecutionOrder.verify(raceTrackMock).bookSlot(entryTime, exitTime);
        Assertions.assertEquals(expectedSlot, actualSlot);

    }

    @Test
    public void getCostPerHour_ShouldThrow_TrackNotFoundException() throws Exception {
        
        // Arrange
        VehicleType vehicleType = VehicleType.BIKE;

        when(trackMapMock.get(vehicleType)).thenReturn(null);

        // Act & Assert
        Assertions.assertThrows(TrackNotFoundException.class, 
            () -> racetrackManager.getCostPerHour(vehicleType));
        verify(trackMapMock).get(vehicleType);
    }

    @Test
    public void getCostPerHour_ShouldReturn_CostPerHour() throws Exception {
        
        // Arrange
        VehicleType vehicleType = VehicleType.BIKE;
        ITrack raceTrackMock = Mockito.mock(ITrack.class);
        float expectedCostPerHour = 60f;

        when(trackMapMock.get(vehicleType)).thenReturn(raceTrackMock);
        when(raceTrackMock.getCostPerHour()).thenReturn(expectedCostPerHour);

        // Act
        float actualCostPerHour = racetrackManager.getCostPerHour(vehicleType);

        // Assert
        InOrder getCostPerHourExecutionOrder = Mockito.inOrder(trackMapMock, raceTrackMock);
        getCostPerHourExecutionOrder.verify(trackMapMock).get(vehicleType);
        getCostPerHourExecutionOrder.verify(raceTrackMock).getCostPerHour();

        Assertions.assertEquals(expectedCostPerHour, actualCostPerHour);
    }

    @Test
    public void registerTrack_Should_Register() {
        
        // Arrange
        ITrack raceTrackMock = Mockito.mock(ITrack.class);

        // Act
        racetrackManager.registerTrack(VehicleType.BIKE, raceTrackMock);

        // Assert
        verify(trackMapMock).put(VehicleType.BIKE, raceTrackMock);
    }
}