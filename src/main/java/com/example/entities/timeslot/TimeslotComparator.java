package com.example.entities.timeslot;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TimeslotComparator implements Comparator<ITimeslot> {

    public int compare(ITimeslot newTimeslot, ITimeslot oldTimeslot) {
    
        if(newTimeslot.isBefore(oldTimeslot)) {
            
            return -1;

        } else if(newTimeslot.isAfter(oldTimeslot)) {
            
            return 1;

        } else {

            // Overlapped condition
            // if(
                // newTimeslotEntryTime.isBefore(oldTimeslotExitTime) 
                    // && newTimeslotExitTime.isAfter(oldTimeslotEntryTime)
                // ) {
            // 
            // }

            return 0;
        }
    }

    public List<ITimeslot> computeIntersectedIntervals(ITimeslot givenTimeSlot, List<ITimeslot> slotsList) {

        List<ITimeslot> intersectedIntervalList = new ArrayList<>();

        for(ITimeslot currentTimeSlot : slotsList) {

            collectIntersectedIntervals(givenTimeSlot, currentTimeSlot, intersectedIntervalList);
        }

        return intersectedIntervalList;
    }

    private boolean isgivenTimeSlotOverlapped(ITimeslot givenTimeSlot, ITimeslot otherTimeSlot){

        return (compare(givenTimeSlot, otherTimeSlot) == 0);
    }

    private void collectIntersectedIntervals(ITimeslot givenTimeSlot, ITimeslot currentTimeSlot,List<ITimeslot> intersectedIntervalList){
        
        if(isgivenTimeSlotOverlapped(givenTimeSlot,currentTimeSlot)){
            intersectedIntervalList.add(currentTimeSlot);
        }
    }
}