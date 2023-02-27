package com.example.entities.revenue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RevenueTest {
    
    @Test
    public void getTotalAmount_ShouldReturn_TotalAmount(){
        
        // Arrange
        float totalAmount = 180f;
        IRevenue revenue = new Revenue(totalAmount);

        // Act & Assert
        Assertions.assertEquals(totalAmount, revenue.getTotalAmount());

    }

    @Test
    public void addAmount_ShouldAdd_AndReturn_TotalAmount(){
        
        // Arrange
        float totalAmount = 180f;
        IRevenue revenue = new Revenue(totalAmount);

        // Act & Assert
        revenue.addAmount(100f);
        Assertions.assertEquals(280f, revenue.getTotalAmount());
    }
}
