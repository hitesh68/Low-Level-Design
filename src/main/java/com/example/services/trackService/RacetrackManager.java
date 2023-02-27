package com.example.services.trackService;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.example.entities.racetrack.ITrack;
import com.example.entities.racetrack.TrackType;
import com.example.entities.timeslot.ITimeslot;
import com.example.entities.vehicles.VehicleType;
import com.example.exceptions.RacetrackFullException;
import com.example.exceptions.TrackNotFoundException;

public class RacetrackManager implements ITrackService{
    
    private final Map<VehicleType, ITrack> trackMap;
    private final TrackType tracksType;

    // *** PUBLIC

    public RacetrackManager(TrackType tracksType, HashMap<VehicleType, ITrack> trackMap) {

        this.trackMap = trackMap;
        this.tracksType = tracksType;
    }

    public RacetrackManager(TrackType tracksType) {

        this.trackMap = new HashMap<>();
        this.tracksType = tracksType;
    }

    @Override
    public ITimeslot bookSlot(VehicleType vehicleType, LocalTime entryTime, LocalTime exitTime)
            throws RacetrackFullException, TrackNotFoundException {
        // TODO Auto-generated method stub

        ITrack track = getTrack(vehicleType);
        ITimeslot bookedSlot = track.bookSlot(entryTime, exitTime);

        return bookedSlot;
    }

    @Override
    public float getCostPerHour(VehicleType vehicleType) throws TrackNotFoundException {
        // TODO Auto-generated method stub

        ITrack track = getTrack(vehicleType);
        
        return track.getCostPerHour();
    }

    public void registerTrack(VehicleType vehicleType, ITrack racetrack) {

        this.trackMap.put(vehicleType, racetrack);
    }

    // *** PRIVATE

    private ITrack getTrack(VehicleType vehicleType) throws TrackNotFoundException {

        Optional<ITrack> optionalTrack = Optional.ofNullable(this.trackMap.get(vehicleType));
        ITrack track = optionalTrack.orElseThrow(() -> new TrackNotFoundException(tracksType.toString() +" TRACK_NOT_FOUND"));

        return track;
    }
    
}
