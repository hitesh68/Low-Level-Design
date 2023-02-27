package com.example.entities.timeslot;

import java.time.LocalTime;

public interface ITimeslot extends Comparable<ITimeslot>{
    
    LocalTime getEntryTime();
    LocalTime getExitTime();
    int getDurationInMins();

    public boolean isBefore(ITimeslot otherTimeslot);
    public boolean isAfter(ITimeslot otherTimeslot);
}
