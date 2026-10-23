package com.bikeshare;

import java.util.HashMap;
import java.util.Map;

public class StationManager {
    private final Map<String, Station> stations = new HashMap<>();
    private final Map<String, Ride> activeRides = new HashMap<>();

    public StationManager() {
        initStation("Library", "B1", "B2");
        initStation("Canteen", "B3", "B4");
        initStation("Dorm", "B5", "B6");
    }

    private void initStation(String name, String bike1Id, String bike2Id) {
        Station station = new Station(name);
        station.addBike(new Bike(bike1Id));
        station.addBike(new Bike(bike2Id));
        stations.put(name.toLowerCase(), station);
    }

    public Station getStation(String name) {
        return stations.get(name.toLowerCase());
    }

    public Ride rentBike(String memberId, MemberType type, String stationName) {
        Station station = getStation(stationName);
        if (station == null || !station.hasAvailableBike()) {
            return null;
        }
        if (activeRides.containsKey(memberId)) {
            return null; // Member already has an active ride
        }
        
        Bike bike = station.releaseBike();
        Ride ride = new Ride(memberId, type, bike, station.getName());
        activeRides.put(memberId, ride);
        return ride;
    }

    public Ride returnBike(String memberId, String stationName) {
        Station station = getStation(stationName);
        Ride ride = activeRides.remove(memberId);
        
        if (station == null || ride == null) {
            return null;
        }

        station.addBike(ride.getBike());
        return ride;
    }
}