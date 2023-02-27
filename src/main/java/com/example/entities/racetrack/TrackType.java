package com.example.entities.racetrack;

public enum TrackType {
    REGULAR,
    VIP;

    public boolean isRegular(){

        return this.equals(REGULAR);
    }

    public boolean isVip(){

        return this.equals(VIP);
    }
}
