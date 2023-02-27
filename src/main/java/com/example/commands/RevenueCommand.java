package com.example.commands;

import java.util.List;

import com.example.services.adminService.IAdminService;

public class RevenueCommand implements ICommand{

    private final IAdminService adminService;

    public RevenueCommand(IAdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public void execute(List<String> tokens) {
        // TODO Auto-generated method stub

        adminService.printTotalRevenue();
        
    }
    
}
