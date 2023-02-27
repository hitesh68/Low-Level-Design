package com.example.commands;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.entities.booking.IBooking;
import com.example.entities.vehicles.VehicleType;
import com.example.exceptions.InvalidEntryTimeException;
import com.example.exceptions.RacetrackFullException;
import com.example.exceptions.TrackNotFoundException;
import com.example.services.userService.IUserService;

@ExtendWith(MockitoExtension.class)
public class BookCommandTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Mock
    private IBooking defaultBooking;

    @Mock
    private IUserService userServiceMock;

    @InjectMocks
    private BookCommand bookCommand;

    @BeforeEach
    public void setup() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }
    
    @Test
    public void execute_ShouldPrintErrorMessage_InvalidEntryTime() throws Exception { 

        // Arrange
        List<String> tokens = Arrays.asList("BOOK","BIKE","M40","12:00");
        String expectedOutput = "INVALID_ENTRY_TIME";

        doThrow(new InvalidEntryTimeException("INVALID_ENTRY_TIME")).when(userServiceMock).
            bookRacetrack(VehicleType.BIKE, "M40", LocalTime.parse("12:00"));

        // Act
        bookCommand.execute(tokens);

        // Assert
        Assertions.assertThrows(InvalidEntryTimeException.class,
            () -> userServiceMock.bookRacetrack(VehicleType.BIKE, "M40", LocalTime.parse("12:00")));
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
    }

    @Test
    public void execute_ShouldPrintErrorMessage_TrackNotFound() throws Exception {
        
        // Arrange
        List<String> tokens = Arrays.asList("BOOK","CAR","M40","13:00");
        String expectedOutput = "TRACK_NOT_FOUND";

        doThrow(new TrackNotFoundException(expectedOutput)).when(userServiceMock).
            bookRacetrack(VehicleType.CAR, "M40", LocalTime.parse("13:00"));

        // Act
        bookCommand.execute(tokens);

        // Assert
        Assertions.assertThrows(TrackNotFoundException.class,
            () -> userServiceMock.bookRacetrack(VehicleType.CAR, "M40", LocalTime.parse("13:00")));
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
    }

    @Test
    public void execute_ShouldPrintErrorMessage_RacetrackFull() throws Exception {
        
        // Arrange
        List<String> tokens = Arrays.asList("BOOK","SUV","M40","13:00");
        String expectedOutput = "RACETRACK_FULL";

        doThrow(new RacetrackFullException(expectedOutput)).when(userServiceMock).
            bookRacetrack(VehicleType.SUV, "M40", LocalTime.parse("13:00"));

        // Act
        bookCommand.execute(tokens);

        // Assert
        Assertions.assertThrows(RacetrackFullException.class,
            () -> userServiceMock.bookRacetrack(VehicleType.SUV, "M40", LocalTime.parse("13:00")));
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
    }

    @Test
    public void execute_ShouldPrint_SuccesMessage() throws Exception {
        
        // Arrange
        List<String> tokens = Arrays.asList("BOOK","BIKE","M40","13:00");
        String expectedOutput = "SUCCESS";

        // Act
        bookCommand.execute(tokens);

        // Assert
        verify(userServiceMock).bookRacetrack(VehicleType.BIKE, "M40", LocalTime.parse("13:00"));
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
    }

    @AfterEach
    public void reset() {
        System.setOut(standardOut);
    }
}
