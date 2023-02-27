package com.example.services;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

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
public class AdminServiceTest {
    
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Mock
    private IRevenueService revenueServiceMock;

    @InjectMocks
    private AdminService adminService;

    @BeforeEach
    public void setup() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }
    
    @Test
    public void ShouldPrint_TotalRevenue(){

        // Arrange
        String expectedOutput = "1800 900";

        when(revenueServiceMock.getTotalAmount(TrackType.REGULAR)).thenReturn(1800f);
        when(revenueServiceMock.getTotalAmount(TrackType.VIP)).thenReturn(900f);
        
        // Act
        adminService.printTotalRevenue();

        // Assert
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
        verify(revenueServiceMock).getTotalAmount(TrackType.REGULAR);
        verify(revenueServiceMock).getTotalAmount(TrackType.VIP);

    }

    @AfterEach
    public void reset() {
        System.setOut(standardOut);
    }

}
