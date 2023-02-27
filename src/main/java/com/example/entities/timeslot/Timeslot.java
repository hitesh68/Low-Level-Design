package com.example.entities.timeslot;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class Timeslot implements ITimeslot{
    
    private final LocalTime entryTimeInHhMm;
    private final LocalTime exitTimeInHhMm;
    private final int durationInMins;

    // *** PUBLIC

    public Timeslot(
        LocalTime entryTimeInHhMm,
            LocalTime exitTimeInHhMm
    ) {

        this.entryTimeInHhMm = entryTimeInHhMm;
        this.exitTimeInHhMm = exitTimeInHhMm;
        this.durationInMins = computeDurationInMins(entryTimeInHhMm, exitTimeInHhMm);
    }

    @Override
    public int compareTo(ITimeslot otherTimeslot) {
        // TODO Auto-generated method stub

        if(entryTimeInHhMm.isBefore(otherTimeslot.getEntryTime())) {
            return -1;
        } else if(entryTimeInHhMm.isAfter(otherTimeslot.getEntryTime())){
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public LocalTime getEntryTime() {
        // TODO Auto-generated method stub

        return this.entryTimeInHhMm;
    }

    @Override
    public LocalTime getExitTime() {
        // TODO Auto-generated method stub

        return this.exitTimeInHhMm;
    }

    @Override
    public int getDurationInMins() {
        // TODO Auto-generated method stub
        
        return this.durationInMins;
    }

    public boolean isBefore(ITimeslot otherTimeslot){

        return (exitTimeInHhMm.isBefore(otherTimeslot.getEntryTime()) || exitTimeInHhMm.equals(otherTimeslot.getEntryTime()));
    }

    public boolean isAfter(ITimeslot otherTimeslot){

        return (entryTimeInHhMm.isAfter(otherTimeslot.getExitTime()) || entryTimeInHhMm.equals(otherTimeslot.getExitTime()));
    }

    // *** PRIVATE

    private static int computeDurationInMins(LocalTime entryTimeInHhMm, LocalTime exitTimeInHhMm) {

        int durationInMins = (int)entryTimeInHhMm.until(exitTimeInHhMm, ChronoUnit.MINUTES);
        return durationInMins;
    }
    
}
