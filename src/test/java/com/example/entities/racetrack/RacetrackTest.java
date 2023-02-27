package com.example.entities.racetrack;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.entities.timeslot.ITimeslot;
import com.example.entities.timeslot.Timeslot;
import com.example.exceptions.RacetrackFullException;

@ExtendWith(MockitoExtension.class)
public class RacetrackTest {

    private List<ITimeslot> bookedSlotsList;
    
    private Racetrack racetrack;
    
    @Test
    public void bookSlot_shouldReturn_bookedSlot_whenVehicleLimit_isOne_And_SlotIsAvailable() throws RacetrackFullException {

        // Arrange
        
        bookedSlotsList = new ArrayList<>();
        bookedSlotsList.add(
            new Timeslot(LocalTime.parse("14:00"), LocalTime.parse("17:00"))
        );
        racetrack = new Racetrack(bookedSlotsList, 1, 60f);

        LocalTime entryTime = LocalTime.parse("17:00");
        LocalTime exitTime = LocalTime.parse("20:00");

        // Act
        ITimeslot actualBookedSlot = racetrack.bookSlot(entryTime , exitTime );

        // Assert
        Assertions.assertEquals(entryTime, actualBookedSlot.getEntryTime());
        Assertions.assertEquals(exitTime, actualBookedSlot.getExitTime());
    }

    @Test
    public void bookSlot_shouldReturn_bookedSlot_whenVehicleLimit_isFour_And_SlotIsAvailable() throws RacetrackFullException {
        
        // Arrange
        
        bookedSlotsList = new ArrayList<>();
        bookedSlotsList.add(new Timeslot(LocalTime.parse("14:00"), LocalTime.parse("17:00")));
        bookedSlotsList.add(new Timeslot(LocalTime.parse("13:00"), LocalTime.parse("16:00")));
        bookedSlotsList.add(new Timeslot(LocalTime.parse("15:00"), LocalTime.parse("18:00")));
        bookedSlotsList.add(new Timeslot(LocalTime.parse("18:00"), LocalTime.parse("19:00")));
        Collections.sort(bookedSlotsList);

        racetrack = new Racetrack(bookedSlotsList, 4, 60f);

        LocalTime entryTime = LocalTime.parse("14:30");
        LocalTime exitTime = LocalTime.parse("17:30");

        // Act
        ITimeslot actualBookedSlot = racetrack.bookSlot(entryTime , exitTime );

        // Assert
        Assertions.assertEquals(entryTime, actualBookedSlot.getEntryTime());
        Assertions.assertEquals(exitTime, actualBookedSlot.getExitTime());
    }

    @Test
    public void bookSlot_shouldThrow_RacetrackFullException_whenVehicleLimit_isOne_And_SlotIsNotAvailable() throws RacetrackFullException {
        
        // Arrange
        
        bookedSlotsList = new ArrayList<>();
        bookedSlotsList.add(
            new Timeslot(LocalTime.parse("14:00"), LocalTime.parse("17:00"))
        );
        racetrack = new Racetrack(bookedSlotsList, 1, 60f);

        LocalTime entryTime = LocalTime.parse("15:00");
        LocalTime exitTime = LocalTime.parse("18:00");

        // Act & Assert
        Assertions.assertThrows(RacetrackFullException.class,
        () -> racetrack.bookSlot(entryTime , exitTime ));

    }

    @Test
    public void bookSlot_shouldThrow_RacetrackFullException_whenVehicleLimit_isFour_And_SlotIsNotAvailable() throws RacetrackFullException {
        
        // Arrange
        
        bookedSlotsList = new ArrayList<>();
        bookedSlotsList.add(new Timeslot(LocalTime.parse("14:00"), LocalTime.parse("17:00")));
        bookedSlotsList.add(new Timeslot(LocalTime.parse("13:00"), LocalTime.parse("16:00")));
        bookedSlotsList.add(new Timeslot(LocalTime.parse("15:00"), LocalTime.parse("18:00")));
        bookedSlotsList.add(new Timeslot(LocalTime.parse("13:30"), LocalTime.parse("18:30")));
        Collections.sort(bookedSlotsList);

        racetrack = new Racetrack(bookedSlotsList, 4, 60f);

        LocalTime entryTime = LocalTime.parse("13:00");
        LocalTime exitTime = LocalTime.parse("16:00");

        // Act & Assert
        Assertions.assertThrows(RacetrackFullException.class,
        () -> racetrack.bookSlot(entryTime , exitTime ));
    }

    @Test
    public void getCostPerHour_ShouldReturn_CostPerHour(){
        
        // Arrange
        float expectedCostPerHour = 60f;
        racetrack = new Racetrack(4, expectedCostPerHour);

        // Act & Assert
        Assertions.assertEquals(expectedCostPerHour, racetrack.getCostPerHour());
    }
}
