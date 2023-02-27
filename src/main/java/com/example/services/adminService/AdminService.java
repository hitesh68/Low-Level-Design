package com.example.services.adminService;

import com.example.entities.racetrack.TrackType;
import com.example.services.revenueService.IRevenueService;

public class AdminService implements IAdminService{
    
    private final IRevenueService revenueService;

    // *** PUBLIC

    public AdminService(IRevenueService revenueService) {
        this.revenueService = revenueService;
    }

    @Override
    public void printTotalRevenue() {
        // TODO Auto-generated method stub

        int regularTracksRevenue = (int)revenueService.getTotalAmount(TrackType.REGULAR);
        int vipTracksRevenue = (int)revenueService.getTotalAmount(TrackType.VIP);

        System.out.println(regularTracksRevenue + " " + vipTracksRevenue);
        
    }

}
