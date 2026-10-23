package com.bikeshare;

public interface RideRepository {
    void saveRide(Ride ride, String endStation, int minutes, int fee);
}