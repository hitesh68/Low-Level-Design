package com.example.services.timingController;

import java.time.LocalTime;

import com.example.exceptions.InvalidEntryTimeException;
import com.example.exceptions.InvalidExitTimeException;

public class TimingSpecs implements ITimingController{
    
    private final LocalTime minEntryTimeInHhMm;  
    private final LocalTime maxExitTimeInHhMm;
    private final int defaultBookingPeriodInMins;
    private final LocalTime lastBookingTime;

    // *** PUBLIC 

    public TimingSpecs(
        LocalTime minEntryTimeInHhMm, 
            LocalTime maxExitTimeInHhMm,
                int defaultBookingPeriodInMins
    ) {

        this.minEntryTimeInHhMm = minEntryTimeInHhMm;  
        this.maxExitTimeInHhMm = maxExitTimeInHhMm;
        this.defaultBookingPeriodInMins = defaultBookingPeriodInMins;
        this.lastBookingTime = computeLastBookingTime(maxExitTimeInHhMm, defaultBookingPeriodInMins);
    }

    @Override
    public LocalTime computeExitTime(LocalTime entryTimeInHhMm) throws InvalidEntryTimeException {
        // TODO Auto-generated method stub

        validateEntryTime(entryTimeInHhMm);

        LocalTime exitTimeInHhMm = entryTimeInHhMm.plusMinutes(getDefaultBookingPeriodInMins());
        return exitTimeInHhMm;
    }

    @Override
    public void validateExitTime(LocalTime exitTimeInHhMm) throws InvalidExitTimeException {
        // TODO Auto-generated method stub

        if(isValidExitTime(exitTimeInHhMm)){
            // NOTHING
        } else {
            throw new InvalidExitTimeException("INVALID_EXIT_TIME");
        }
        
    }

    // *** PRIVATE

    private LocalTime computeLastBookingTime(
        LocalTime maxExitTimeInHhMm, 
            int defaultBookingPeriodInMins) {
            
                LocalTime lastBookingTime = maxExitTimeInHhMm.minusMinutes(defaultBookingPeriodInMins);
                return lastBookingTime;
    } 

    private void validateEntryTime(LocalTime entryTimeInHhMm) throws InvalidEntryTimeException {

        if(isValidEntryTime(entryTimeInHhMm)) {
            // NOTHING
        } else {
            throw new InvalidEntryTimeException("INVALID_ENTRY_TIME");
        }
    }

    private boolean isValidEntryTime(LocalTime entryTimeInHhMm) {

        LocalTime minEntryTime = getMinEntryTime();
        LocalTime lastBookingTime = getLastBookingTime();

        return (
            ((entryTimeInHhMm.equals(minEntryTime) || entryTimeInHhMm.isAfter(minEntryTime)) 
                && 
            (entryTimeInHhMm.isBefore(lastBookingTime) || entryTimeInHhMm.equals(lastBookingTime)))
        );
    }

    private boolean isValidExitTime(LocalTime exitTimeInHhMm) {

        LocalTime maxExitTime = getMaxExitTime();

        return (
            (exitTimeInHhMm.isAfter(getMinEntryTime()) 
            && 
            (exitTimeInHhMm.isBefore(maxExitTime) || exitTimeInHhMm.equals(maxExitTime)))
        );
        
    }

    // *** GETTERS

    private LocalTime getMinEntryTime() {
        return this.minEntryTimeInHhMm;
    }

    private LocalTime getMaxExitTime() {
        return this.maxExitTimeInHhMm;
    }

    private int getDefaultBookingPeriodInMins() {
        return this.defaultBookingPeriodInMins;
    }

    private LocalTime getLastBookingTime() {
        return this.lastBookingTime;
    }

}
