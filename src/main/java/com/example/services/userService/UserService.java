package com.example.services.userService;

import java.time.LocalTime;

import com.example.entities.booking.IBooking;
import com.example.entities.vehicles.IVehicle;
import com.example.entities.vehicles.VehicleType;
import com.example.exceptions.BookingNotFoundException;
import com.example.exceptions.InvalidEntryTimeException;
import com.example.exceptions.InvalidExitTimeException;
import com.example.exceptions.RacetrackFullException;
import com.example.exceptions.TrackNotFoundException;
import com.example.services.bookingService.IBookingService;
import com.example.services.revenueService.IRevenueService;
import com.example.services.timingController.ITimingController;
import com.example.services.vehicleFactory.IVehicleFactory;

public class UserService implements IUserService{
    
    private final ITimingController timingController;
    private final IVehicleFactory vehicleFactory;
    private final IRevenueService revenueService;
    private final IBookingService bookingService;

    // *** PUBLIC 

    public UserService(
        ITimingController timingController,
            IVehicleFactory vehicleFactory,
                IRevenueService revenueService,
                    IBookingService bookingService
    ) {

        this.timingController = timingController;
        this.vehicleFactory = vehicleFactory;
        this.revenueService = revenueService;
        this.bookingService = bookingService;
    }

    @Override
    public void bookRacetrack(VehicleType vehicleType, String vehicleNumber, LocalTime entryTimeInHhMm)
            throws InvalidEntryTimeException, TrackNotFoundException, RacetrackFullException  {
        // TODO Auto-generated method stub

        LocalTime exitTimeInHhMm = timingController.computeExitTime(entryTimeInHhMm);
        IVehicle vehicle = vehicleFactory.createVehicle(vehicleType, vehicleNumber);
        IBooking defaultBooking = vehicle.bookSlot(entryTimeInHhMm, exitTimeInHhMm);

        revenueService.collectRevenue(defaultBooking);
        bookingService.addBooking(defaultBooking);
    }

    @Override
    public void extendBooking(String vehicleNumber, LocalTime exitTimeInHhMm)
            throws InvalidExitTimeException, BookingNotFoundException, TrackNotFoundException, RacetrackFullException  {
        // TODO Auto-generated method stub
        
        timingController.validateExitTime(exitTimeInHhMm);
        IBooking booking = bookingService.getBooking(vehicleNumber);

        IVehicle vehicle = vehicleFactory.createVehicle(booking.getVehicleType(), vehicleNumber);
        IBooking extendedBooking = vehicle.extendSlot(booking, exitTimeInHhMm);
        
        revenueService.collectRevenue(extendedBooking);
        bookingService.updateBooking(extendedBooking);
    }

}
