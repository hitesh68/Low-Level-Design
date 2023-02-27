package com.example.commands;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
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

import com.example.entities.racetrack.TrackType;
import com.example.services.adminService.AdminService;
import com.example.services.revenueService.IRevenueService;

@ExtendWith(MockitoExtension.class)
public class RevenueCommandTest {
    
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Mock
    private IRevenueService revenueManagerMock;

    @InjectMocks
    private AdminService adminService;

    @BeforeEach
    public void setup() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    public void execute_ShouldPrint_TotalRevenue(){

        // Arrange
        List<String> tokens = Arrays.asList("REVENUE");
        String expectedOutput = "180 360";
        RevenueCommand revenueCommand = new RevenueCommand(adminService);

        when(revenueManagerMock.getTotalAmount(TrackType.REGULAR)).thenReturn(180f);
        when(revenueManagerMock.getTotalAmount(TrackType.VIP)).thenReturn(360f);

        // Act
        revenueCommand.execute(tokens);

        // Assert
        verify(revenueManagerMock,times(2)).getTotalAmount(any(TrackType.class));
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
    }

    @AfterEach
    public void reset() {
        System.setOut(standardOut);
    }
}
