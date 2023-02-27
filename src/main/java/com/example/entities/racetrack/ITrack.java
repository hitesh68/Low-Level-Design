package com.example.entities.racetrack;

import java.time.LocalTime;

import com.example.entities.timeslot.ITimeslot;
import com.example.exceptions.RacetrackFullException;

public interface ITrack {

    ITimeslot bookSlot(LocalTime entryTime, LocalTime exitTime) throws RacetrackFullException;
    float getCostPerHour();
}
