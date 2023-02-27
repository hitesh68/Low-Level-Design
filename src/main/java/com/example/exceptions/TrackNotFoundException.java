package com.example.exceptions;

public class TrackNotFoundException extends Exception{
    
    public TrackNotFoundException(String message) {
        super(message);
    }
}
