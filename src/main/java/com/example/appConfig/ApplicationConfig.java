package com.example.appConfig;

import java.time.LocalTime;

import com.example.commands.BookCommand;
import com.example.commands.CommandInvoker;
import com.example.commands.CommandName;
import com.example.commands.ExtendBookingCommand;
import com.example.commands.RevenueCommand;
import com.example.entities.racetrack.Racetrack;
import com.example.entities.racetrack.TrackType;
import com.example.entities.vehicles.VehicleType;
import com.example.services.adminService.AdminService;
import com.example.services.bookingService.BookingManager;
import com.example.services.revenueService.RevenueManager;
import com.example.services.revenueService.revenueCalculator.DefaultRevenueCalculation;
import com.example.services.revenueService.revenueCalculator.ExtendedRevenueCalculation;
import com.example.services.revenueService.revenueCalculator.RevenueCalculator;
import com.example.services.timingController.TimingSpecs;
import com.example.services.trackService.RacetrackManager;
import com.example.services.userService.UserService;
import com.example.services.vehicleFactory.VehicleFactory;

public class ApplicationConfig {

    private final RacetrackManager regularTracksManager = setupRegularTracksManager();
    private final RacetrackManager vipTracksManager = setupVipTracksManager();

    private final TimingSpecs timingSpecs = setupTimingSpecs();
    private final VehicleFactory vehicleFactory = new VehicleFactory(regularTracksManager, vipTracksManager);

    private final RevenueCalculator revenueCalculator = setupRevenueCalculator();
    private final RevenueManager revenueManager = new RevenueManager(revenueCalculator);
    private final BookingManager bookingManager = new BookingManager();

    private final UserService userService = new UserService(timingSpecs, vehicleFactory, revenueManager, bookingManager);
    private final AdminService adminService = new AdminService(revenueManager);

    private final BookCommand bookCommand = new BookCommand(userService);
    private final ExtendBookingCommand extendBookingCommand = new ExtendBookingCommand(userService);
    private final RevenueCommand revenueCommand = new RevenueCommand(adminService);

    private final CommandInvoker commandInvoker = new CommandInvoker();

    // *** PUBLIC

    public CommandInvoker getCommandInvoker() {

        this.commandInvoker.registerCommand(CommandName.BOOK, bookCommand);
        this.commandInvoker.registerCommand(CommandName.ADDITIONAL, extendBookingCommand);
        this.commandInvoker.registerCommand(CommandName.REVENUE, revenueCommand);

        return commandInvoker;
    }

    // *** PRIVATE

    private TimingSpecs setupTimingSpecs() {

        String minEntryTimeString = "13:00";
        LocalTime minEntryTimeInHhMm = LocalTime.parse(minEntryTimeString);

        String maxExitTimeString = "20:00";
        LocalTime maxExitTimeInHhMm = LocalTime.parse(maxExitTimeString);
        
        final int defaultBookingPeriodInMins = 180;

        return new TimingSpecs(minEntryTimeInHhMm, maxExitTimeInHhMm, defaultBookingPeriodInMins);
    }

    private RacetrackManager setupRegularTracksManager(){

        Racetrack regularBikeTrack = new Racetrack(4,60);
        Racetrack regularCarTrack = new Racetrack(2,120);
        Racetrack regularSuvTrack = new Racetrack(2,200);

        RacetrackManager regularTracksManager = new RacetrackManager(TrackType.REGULAR);

        regularTracksManager.registerTrack(VehicleType.BIKE, regularBikeTrack);
        regularTracksManager.registerTrack(VehicleType.CAR, regularCarTrack);
        regularTracksManager.registerTrack(VehicleType.SUV, regularSuvTrack);

        return regularTracksManager;
    }

    private RacetrackManager setupVipTracksManager() {

        Racetrack vipCarTrack = new Racetrack(1,250);
        Racetrack vipSuvTrack = new Racetrack(1,300);
        
        RacetrackManager vipTracksManager = new RacetrackManager(TrackType.VIP);
        
        vipTracksManager.registerTrack(VehicleType.CAR, vipCarTrack);
        vipTracksManager.registerTrack(VehicleType.SUV, vipSuvTrack);

        return vipTracksManager;
    }

    private RevenueCalculator setupRevenueCalculator() {

        DefaultRevenueCalculation defaultRevenueCalculation = new DefaultRevenueCalculation(regularTracksManager, vipTracksManager);
        
        final float extendedBookingCostPerHour = 50;
        final int giveAwayDurationInMins = 15;

        ExtendedRevenueCalculation extendedRevenueCalculation = new ExtendedRevenueCalculation(extendedBookingCostPerHour, giveAwayDurationInMins);

        RevenueCalculator revenueCalculator = new RevenueCalculator(defaultRevenueCalculation, extendedRevenueCalculation);

        return revenueCalculator;
    }
}
