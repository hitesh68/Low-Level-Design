package com.example.services;

import java.time.LocalTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.exceptions.InvalidEntryTimeException;
import com.example.exceptions.InvalidExitTimeException;
import com.example.services.timingController.TimingSpecs;

@ExtendWith(MockitoExtension.class)
public class TimingSpecsTest {

    private final TimingSpecs timingSpecs = new TimingSpecs(LocalTime.parse("13:00"), LocalTime.parse("20:00"), 180);
    
    @Test
    public void computeExitTime_ShouldThrow_InvalidEntryTimeException() throws Exception {

        // Arrange
        LocalTime entryTime = LocalTime.parse("12:00");

        // Act & Assert
        Assertions.assertThrows(InvalidEntryTimeException.class, 
            () -> timingSpecs.computeExitTime(entryTime));
    }

    @Test
    public void computeExitTime_ShouldReturn_ExitTime() throws Exception {
        
        // Arrange
        LocalTime entryTime = LocalTime.parse("13:00");
        LocalTime expectedExitTime = LocalTime.parse("16:00");

        // Act 
        LocalTime actualExitTime = timingSpecs.computeExitTime(entryTime);

        // Assert
        Assertions.assertEquals(expectedExitTime, actualExitTime);
    }

    @Test
    public void validateExitTime_ShouldThrow_InvalidExitTimeException() throws Exception {

        // Arrange
        LocalTime exitTime = LocalTime.parse("21:00");

        // Act & Assert
        Assertions.assertThrows(InvalidExitTimeException.class, 
            () -> timingSpecs.validateExitTime(exitTime));
    }

    @Test
    public void validateExitTime_ShouldNotThrow_InvalidExitTimeException() throws Exception {
        
        // Arrange
        LocalTime exitTime = LocalTime.parse("19:00");

        // Act & Assert
        Assertions.assertDoesNotThrow(
            () -> timingSpecs.validateExitTime(exitTime)
        );
    }
}
