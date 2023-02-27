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
import com.example.exceptions.BookingNotFoundException;
import com.example.exceptions.InvalidExitTimeException;
import com.example.exceptions.RacetrackFullException;
import com.example.exceptions.TrackNotFoundException;
import com.example.services.userService.IUserService;

@ExtendWith(MockitoExtension.class)
public class ExtendBookingCommandTest {
    
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Mock
    private IBooking extendedBooking;

    @Mock
    private IUserService userServiceMock;

    @InjectMocks
    private ExtendBookingCommand extendBookingCommand;

    @BeforeEach
    public void setup() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }


    @Test
    public void execute_ShouldPrintErrorMessage_InvalidExitTime() throws Exception {

        // Arrange
        List<String> tokens = Arrays.asList("ADDITIONAL","M40","21:00");
        String expectedOutput = "INVALID_EXIT_TIME";

        doThrow(new InvalidExitTimeException(expectedOutput)).when(userServiceMock).
            extendBooking("M40", LocalTime.parse("21:00"));

        // Act
        extendBookingCommand.execute(tokens);

        // Assert
        Assertions.assertThrows(InvalidExitTimeException.class,
            () -> userServiceMock.extendBooking("M40", LocalTime.parse("21:00")));
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
    }

    @Test
    public void execute_ShouldPrintErrorMessage_BookingNotFound() throws Exception {
        
        // Arrange
        List<String> tokens = Arrays.asList("ADDITIONAL","M40","18:00");
        String expectedOutput = "BOOKING_NOT_FOUND";

        doThrow(new BookingNotFoundException(expectedOutput)).when(userServiceMock).
            extendBooking("M40", LocalTime.parse("18:00"));

        // Act
        extendBookingCommand.execute(tokens);

        // Assert
        Assertions.assertThrows(BookingNotFoundException.class,
            () -> userServiceMock.extendBooking("M40", LocalTime.parse("18:00")));
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
    }

    @Test
    public void execute_ShouldPrintErrorMessage_TrackNotFound() throws Exception {
        
        // Arrange
        List<String> tokens = Arrays.asList("ADDITIONAL","M40","18:00");
        String expectedOutput = "TRACK_NOT_FOUND";

        doThrow(new TrackNotFoundException(expectedOutput)).when(userServiceMock).
            extendBooking("M40", LocalTime.parse("18:00"));

        // Act
        extendBookingCommand.execute(tokens);

        // Assert
        Assertions.assertThrows(TrackNotFoundException.class,
            () -> userServiceMock.extendBooking("M40", LocalTime.parse("18:00")));
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
    }

    @Test
    public void execute_ShouldPrintErrorMessage_RacetrackFull() throws Exception {
        
        // Arrange
        List<String> tokens = Arrays.asList("ADDITIONAL","M40","18:00");
        String expectedOutput = "RACETRACK_FULL";

        doThrow(new RacetrackFullException(expectedOutput)).when(userServiceMock).
            extendBooking("M40", LocalTime.parse("18:00"));

        // Act
        extendBookingCommand.execute(tokens);

        // Assert
        Assertions.assertThrows(RacetrackFullException.class,
            () -> userServiceMock.extendBooking("M40", LocalTime.parse("18:00")));
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
    }

    @Test
    public void execute_ShouldPrint_SuccesMessage() throws Exception {
        
        // Arrange
        List<String> tokens = Arrays.asList("ADDITIONAL","M40","18:00");
        String expectedOutput = "SUCCESS";

        // Act
        extendBookingCommand.execute(tokens);

        // Assert
        verify(userServiceMock).extendBooking("M40", LocalTime.parse("18:00"));
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
    }

    @AfterEach
    public void reset() {
        System.setOut(standardOut);
    }
}
