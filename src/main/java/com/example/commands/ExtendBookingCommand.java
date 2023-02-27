package com.example.commands;

import java.time.LocalTime;
import java.util.List;

import com.example.exceptions.BookingNotFoundException;
import com.example.exceptions.InvalidExitTimeException;
import com.example.exceptions.RacetrackFullException;
import com.example.exceptions.TrackNotFoundException;
import com.example.services.userService.IUserService;

public class ExtendBookingCommand implements ICommand{

    private final IUserService userService;
    private final String successMessage = "SUCCESS";

    public ExtendBookingCommand(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(List<String> tokens) {
        // TODO Auto-generated method stub
        
        String vehicleNumber = tokens.get(1);
        String exitTimeString = tokens.get(2);

        LocalTime exitTimeInHhMm = LocalTime.parse(exitTimeString);

        try {

            userService.extendBooking(vehicleNumber, exitTimeInHhMm);
            System.out.println(this.successMessage);

        } catch (InvalidExitTimeException invalidExitTimeException) {
            // TODO: handle exception

            System.out.println(invalidExitTimeException.getMessage());

        } catch(BookingNotFoundException bookingNotFoundException) {

            System.out.println(bookingNotFoundException.getMessage());


        }catch(TrackNotFoundException trackNotFoundException) {

            System.out.println(trackNotFoundException.getMessage());


        } catch(RacetrackFullException racetrackFullException) {

            System.out.println(racetrackFullException.getMessage());
        } 

    }
    
}
