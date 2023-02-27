package com.example.entities.racetrack;

import java.util.List;

import com.example.entities.timeslot.ITimeslot;
import com.example.entities.timeslot.TimeslotComparator;

class RacetrackAlgo {

    private static final TimeslotComparator TIME_SLOT_COMPARATOR = new TimeslotComparator();
    private int maxVehiclesCount = 0;
    
    RacetrackAlgo(){}

    public int computeVehiclesDrivingSimultaneously(ITimeslot newTimeSlot, List<ITimeslot> bookedSlots) {

        List<ITimeslot> intersectedIntervalList = TIME_SLOT_COMPARATOR.computeIntersectedIntervals(newTimeSlot, bookedSlots);

        for(int index=0; index<intersectedIntervalList.size(); index++) {

            ITimeslot slot = intersectedIntervalList.get(index);

            List<ITimeslot> overlappedIntervalList = TIME_SLOT_COMPARATOR.computeIntersectedIntervals(
                slot, bookedSlots.subList(index, intersectedIntervalList.size()));

            updateMaxVehiclesCount(overlappedIntervalList.size());
        }

        return getMaxVehiclesCount();
    }

    private void updateMaxVehiclesCount(final int vehiclesCount){

        if(isVehiclesCountGreater(vehiclesCount)){
            this.maxVehiclesCount = vehiclesCount;
        }
    }

    private int getMaxVehiclesCount() {

        return this.maxVehiclesCount;
    }

    private boolean isVehiclesCountGreater(final int vehiclesCount){

        return vehiclesCount > getMaxVehiclesCount();
    }

}

