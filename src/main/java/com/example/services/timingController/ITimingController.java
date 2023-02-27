package com.example.services.timingController;

import java.time.LocalTime;

import com.example.exceptions.InvalidEntryTimeException;
import com.example.exceptions.InvalidExitTimeException;

public interface ITimingController {
    
    LocalTime computeExitTime(LocalTime entryTimeInHhMm) throws InvalidEntryTimeException;
    void validateExitTime(LocalTime exitTimeInHhMm) throws InvalidExitTimeException;
}
