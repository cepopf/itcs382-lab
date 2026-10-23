package com.bikeshare;

public class App {
    public static void main(String[] args) {
        StationManager stationManager = new StationManager();
        RideRepository repository = new CsvRideRepository("rides.csv");
        ConsoleUI ui = new ConsoleUI(stationManager, repository);

        ui.start();
    }
}