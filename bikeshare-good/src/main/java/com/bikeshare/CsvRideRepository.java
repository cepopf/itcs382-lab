package com.bikeshare;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CsvRideRepository implements RideRepository {
    private final String filePath;

    public CsvRideRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void saveRide(Ride ride, String endStation, int minutes, int fee) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
            writer.printf("%s,%s,%s,%s,%d,%d%n",
                    ride.getMemberId(),
                    ride.getMemberType(),
                    ride.getBike().getId(),
                    endStation,
                    minutes,
                    fee);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}