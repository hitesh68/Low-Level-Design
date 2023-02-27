package com.example.entities.revenue;

public class Revenue implements IRevenue{

    private float totalAmount; 

    // *** PUBLIC

    public Revenue(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Revenue() {}

    @Override
    public float getTotalAmount() {
        // TODO Auto-generated method stub
        
        return this.totalAmount;
    }

    @Override
    public float addAmount(float amount) {
        // TODO Auto-generated method stub

        return updateAmount(amount);
        
    }

    private float updateAmount(float amount) {

        this.totalAmount += amount;    
        return this.totalAmount;    
    }
    
}
