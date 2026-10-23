package com.bikeshare;

import java.util.ArrayDeque;
import java.util.Deque;

public class Station {
    private final String name;
    private final Deque<Bike> bikes = new ArrayDeque<>();

    public Station(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean hasAvailableBike() {
        return !bikes.isEmpty();
    }

    public void addBike(Bike bike) {
        bikes.push(bike);
    }

    public Bike releaseBike() {
        return bikes.poll();
    }
}