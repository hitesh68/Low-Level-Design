package com.example.entities.racetrack;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.example.entities.timeslot.ITimeslot;
import com.example.entities.timeslot.Timeslot;
import com.example.exceptions.RacetrackFullException;

public class Racetrack implements ITrack{
    
    private final List<ITimeslot> bookedSlots;
    private final int vehiclesLimit;
    private final float costPerHour;

    // *** PUBLIC
    
    public Racetrack(int vehiclesLimit, float costPerHour) {

        this.bookedSlots = new ArrayList<>();
        this.vehiclesLimit = vehiclesLimit;
        this.costPerHour = costPerHour;
    }

    public Racetrack(List<ITimeslot> bookedSlots, int vehiclesLimit, float costPerHour) {

        this.bookedSlots = bookedSlots;
        this.vehiclesLimit = vehiclesLimit;
        this.costPerHour = costPerHour;
    }

    @Override
    public ITimeslot bookSlot(LocalTime entryTime, LocalTime exitTime) throws RacetrackFullException {
        // TODO Auto-generated method stub

        ITimeslot newTimeslot = new Timeslot(entryTime, exitTime);
        if(checkIfSlotAvailable(newTimeslot)) {

            return addSlot(newTimeslot);
        }

        throw new RacetrackFullException("RACETRACK_FULL");
        
    }

    @Override
    public float getCostPerHour() {
        // TODO Auto-generated method stub

        return this.costPerHour;
    }

    // *** PRIVATE

    private boolean checkIfSlotAvailable(ITimeslot newTimeslot) {

        RacetrackAlgo algo = new RacetrackAlgo();

        int vehiclesCount = algo.computeVehiclesDrivingSimultaneously(
            newTimeslot, getBookedSlotsList());

        return isVehiclesCountValid(vehiclesCount);
        
    }

    private List<ITimeslot> getBookedSlotsList() {

        // VERY IMPORTANT
        // Collectors.toList() for unmodifiable list  
        
        return this.bookedSlots.stream().collect(Collectors.toList());

    }

    private int getVehiclesLimit() {
        
        return this.vehiclesLimit;
    }

    private ITimeslot addSlot(ITimeslot newTimeslot) {

        this.bookedSlots.add(newTimeslot);
        Collections.sort(this.bookedSlots);
        return newTimeslot;
    }

    private boolean isVehiclesCountValid(final int vehiclesCount) {

        return vehiclesCount < getVehiclesLimit();
    }

}
