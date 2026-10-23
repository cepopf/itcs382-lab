package com.bikeshare;

import java.util.Scanner;

public class ConsoleUI {
    private final StationManager stationManager;
    private final RideRepository repository;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleUI(StationManager stationManager, RideRepository repository) {
        this.stationManager = stationManager;
        this.repository = repository;
    }

    public void start() {
        while (true) {
            System.out.print("\nCommand [rent / return / quit]: ");
            String command = scanner.nextLine().trim().toLowerCase();

            if ("quit".equals(command)) {
                System.out.println("Goodbye!");
                break;
            }

            switch (command) {
                case "rent" -> handleRent();
                case "return" -> handleReturn();
                default -> System.out.println("Unknown command. Try again.");
            }
        }
    }

    private void handleRent() {
        System.out.print("Enter Member ID: ");
        String memberId = scanner.nextLine().trim();

        System.out.print("Enter Member Type (STUDENT/STAFF): ");
        String typeInput = scanner.nextLine().trim().toUpperCase();
        MemberType type;
        try {
            type = MemberType.valueOf(typeInput);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid member type.");
            return;
        }

        System.out.print("Enter Station (Library/Canteen/Dorm): ");
        String stationName = scanner.nextLine().trim();

        Ride ride = stationManager.rentBike(memberId, type, stationName);
        if (ride != null) {
            System.out.println("Bike " + ride.getBike().getId() + " rented successfully!");
        } else {
            System.out.println("Rental failed. Check station availability or active rentals.");
        }
    }

    private void handleReturn() {
        System.out.print("Enter Member ID: ");
        String memberId = scanner.nextLine().trim();

        System.out.print("Enter Return Station (Library/Canteen/Dorm): ");
        String stationName = scanner.nextLine().trim();

        System.out.print("Enter Ride Time (minutes): ");
        int minutes;
        try {
            minutes = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid minutes entered.");
            return;
        }

        Ride ride = stationManager.returnBike(memberId, stationName);
        if (ride == null) {
            System.out.println("Return failed. Check member ID or station name.");
            return;
        }

        int fee = ride.calculateFee(minutes);
        repository.saveRide(ride, stationName, minutes, fee);
        printReceipt(ride.getBike().getId(), minutes, fee);
    }

    private void printReceipt(String bikeId, int minutes, int fee) {
        System.out.println("\n--- RECEIPT ---");
        System.out.println("Bike ID: " + bikeId);
        System.out.println("Minutes: " + minutes);
        System.out.println("Fee: " + fee + " baht");
        System.out.println("----------------");
    }
}