package com.example.entities.timeslot;

import java.time.LocalTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TimeslotTest {
    
    private LocalTime entryTime = LocalTime.parse("13:00");
    private LocalTime exitTime = LocalTime.parse("16:00");
    private ITimeslot timeslot = new Timeslot(entryTime, exitTime);

    // @Test
    // public void compareTo_shouldReturn_minusOne_whenEntryTime_IsBeforeComparedTimeslotsEntryTime() {

    // }

    // @Test
    // public void compareTo_shouldReturn_plusOne_whenEntryTime_IsAfterComparedTimeslotsEntryTime() {
        
    // }

    // @Test
    // public void compareTo_shouldReturn_Zero_whenEntryTime_IsOverlappedWithComparedTimeslotsEntryTime() {
        
    // }

    @Test
    public void getEntryTime_ShouldReturn_EntryTime(){
        
        // Arrange, Act & Assert
        Assertions.assertEquals(entryTime, timeslot.getEntryTime());
    }

    @Test
    public void getExitTime_ShouldReturn_ExitTime(){
        
        // Arrange, Act & Assert
        Assertions.assertEquals(exitTime, timeslot.getExitTime());
    }

    @Test
    public void getDurationInMins_ShouldReturn_DurationInMins(){

        // Arrange, Act & Assert
        Assertions.assertEquals(180, timeslot.getDurationInMins());
    }

    // @Test
    // public void isBefore_ShouldReturn_True_WhenTimeSlotIsBefore_ComparedTimeslot(){
        
    // }

    // @Test
    // public void isBefore_ShouldReturn_False_WhenTimeSlotIsNotBefore_ComparedTimeslot(){
        
    // }

    // @Test
    // public void isAfter_ShouldReturn_True_WhenTimeslotisAfter_ComparedTimeslot(){
        
    // }

    // @Test
    // public void isAfter_ShouldReturn_False_WhenTimeSlotIsNotAfter_ComparedTimeslot(){
        
    // }
    
}
