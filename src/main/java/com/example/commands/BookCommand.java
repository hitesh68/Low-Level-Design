package com.example.commands;

import java.time.LocalTime;
import java.util.List;

import com.example.entities.vehicles.VehicleType;
import com.example.exceptions.InvalidEntryTimeException;
import com.example.exceptions.RacetrackFullException;
import com.example.exceptions.TrackNotFoundException;
import com.example.services.userService.IUserService;

public class BookCommand implements ICommand{

    private final IUserService userService;
    private final String successMessage = "SUCCESS";

    public BookCommand(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(List<String> tokens) {
        // TODO Auto-generated method stub

        final int vehicleTypeOrdinal = 1;
        final int vehicleNumberOrdinal = 2;
        final int entryTimeOrdinal = 3;

        VehicleType vehicleType = VehicleType.valueOf(tokens.get(vehicleTypeOrdinal));
        String vehicleNumber = tokens.get(vehicleNumberOrdinal);
        LocalTime entryTimeInHhMm = LocalTime.parse(tokens.get(entryTimeOrdinal));

        String message = this.successMessage;

        try {
            
            userService.bookRacetrack(vehicleType, vehicleNumber, entryTimeInHhMm);

        } catch (InvalidEntryTimeException invalidEntryTimeException) {
            // TODO: handle exception

            message = invalidEntryTimeException.getMessage();

        } catch(TrackNotFoundException trackNotFoundException) {

            message = trackNotFoundException.getMessage();

        } catch(RacetrackFullException racetrackFullException) {

            message = racetrackFullException.getMessage();

        } finally {

            System.out.println(message);
            
        }

    }
    
}
