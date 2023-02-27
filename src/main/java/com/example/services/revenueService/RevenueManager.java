package com.example.services.revenueService;

import java.util.HashMap;
import java.util.Map;

import com.example.entities.booking.IBooking;
import com.example.entities.racetrack.TrackType;
import com.example.entities.revenue.IRevenue;
import com.example.entities.revenue.Revenue;
import com.example.exceptions.TrackNotFoundException;
import com.example.services.revenueService.revenueCalculator.IRevenueCalculator;

public class RevenueManager implements IRevenueService{
    
    private final Map<TrackType,IRevenue> revenueMap;
    private final IRevenueCalculator revenueCalculator;

    // *** PUBLIC

    public RevenueManager(HashMap<TrackType,IRevenue> revenueMap, 
        IRevenueCalculator revenueCalculator) {

            this.revenueMap = revenueMap;
            this.revenueCalculator = revenueCalculator;
    }

    public RevenueManager(IRevenueCalculator revenueCalculator) {

        final IRevenue regularTrackRevenue = new Revenue();
        final IRevenue vipTrackRevenue = new Revenue();

        this.revenueMap = new HashMap<>();
        this.revenueMap.put(TrackType.REGULAR, regularTrackRevenue);
        this.revenueMap.put(TrackType.VIP, vipTrackRevenue);

        this.revenueCalculator = revenueCalculator;
    }

    @Override
    public void collectRevenue(IBooking booking) throws TrackNotFoundException{
        // TODO Auto-generated method stub

        float amount = revenueCalculator.computeAmount(booking);
        TrackType trackType = booking.getTrackType();

        addAmount(trackType, amount);
    }

    @Override
    public float getTotalAmount(TrackType trackType) {
        // TODO Auto-generated method stub

        IRevenue revenue = getRevenue(trackType);
        return revenue.getTotalAmount();

    }

    // *** PRIVATE

    private void addAmount(TrackType trackType, float amount) {

        IRevenue revenue = getRevenue(trackType);
        revenue.addAmount(amount);
    }

    private IRevenue getRevenue(TrackType trackType) {

        return this.revenueMap.get(trackType);
    }
}
