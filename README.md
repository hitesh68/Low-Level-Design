Low-Level-Design : Racetrack Management

# Context
GeekRacers, an adventure sports company, owns race tracks on which they allow customers to drive their ​​bikes, cars and SUVs. There are separate dedicated tracks for each type of vehicle. Each track has a limit on the number of vehicles that can be driven simultaneously. 
 
# Track Types
GeekRacers offer 2 types of tracks: 
1. Regular, it allows all types of vehicles to race on it. 
2. VIP, it can only be used by cars and SUVs. 
 
 # Booking Fee
 The fee differs based on the type of race track and the type of vehicle. Its image is available in the directory naming "Booking_Fee".
 
 # Booking Rules
 1. The race track has to be booked for a minimum of 3 Hrs per vehicle.  
 2. By default, all the vehicles can book only a regular race track. 
 3. If the regular racetrack is not available, cars or SUVs can be upgraded to a VIP race track. 
 4. Beyond the first 3 hrs, an additional 50 is charged per hour (applicable to all vehicles and all tracks). 
 5. An extra time of 15 minutes beyond the allotted 3 Hrs is not chargeable (provided the track is available). 
 6. If the extra time is more than 15 mts, one will be charged for the whole hour.
 7. If extra time is 20 mins, then they are charged for 1 hour and if extra time is 1hr 5min, then they will be charged for 2 hours. 
 8. Racetracks can be booked from 1 PM to 8PM (both are inclusive). 
 9. All experiences have to be completed by 8PM. Hence, the last booking needs to be done by 5PM. 

# Assumptions
1. Vehicles can only use their dedicated tracks. That means a car can only use a track meant for cars and cannot switch to an SUV track even if it is available. 
2. All the input commands have to be handled in first come first serve mode. 

# Goal
1. Your goal is to calculate the total revenue generated under each racetrack type. 

    Total revenue = Revenue earned for the default booking period + Revenue earned from additional booking period. 
