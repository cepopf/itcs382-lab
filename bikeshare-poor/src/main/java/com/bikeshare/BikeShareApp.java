package com.bikeshare;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BikeShareApp {

    // Member type integer codes
    public static final int TYPE_STUDENT = 1;
    public static final int TYPE_STAFF = 2;

    // Station bike inventory records
    public static Map<String, Integer> stationBikes = new HashMap<>();

    // Active rentals tracking: Member ID -> Station rented from
    public static Map<String, String> activeRentals = new HashMap<>();

    // Active rental member types tracking: Member ID -> Integer code
    public static Map<String, Integer> activeMemberTypes = new HashMap<>();

    public static void main(String[] args) {
        // Initialize stations with 2 bikes each
        stationBikes.put("Library", 2);
        stationBikes.put("Canteen", 2);
        stationBikes.put("Dorm", 2);

        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Campus BikeShare System ===");

        boolean running = true;
        while (running) {
            System.out.println("\nOptions: [1] Rent [2] Return [3] Quit");
            System.out.print("Enter choice: ");
            String input = scanner.nextLine().trim();

            if ("1".equals(input)) {
                processTransaction(scanner, true);
            } else if ("2".equals(input)) {
                processTransaction(scanner, false);
            } else if ("3".equalsIgnoreCase(input) || "quit".equalsIgnoreCase(input)) {
                running = false;
                System.out.println("Exiting system. Goodbye!");
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }

    // Flag-driven method: isRent = true processes rent; isRent = false processes return
    public static void processTransaction(Scanner scanner, boolean isRent) {
        if (isRent) {
            System.out.print("Enter Member ID: ");
            String memberId = scanner.nextLine().trim();

            if (activeRentals.containsKey(memberId)) {
                System.out.println("Error: Member already has an active rental.");
                return;
            }

            System.out.print("Enter Member Type (1 for Student, 2 for Staff): ");
            int type;
            try {
                type = Integer.parseInt(scanner.nextLine().trim());
                if (type != TYPE_STUDENT && type != TYPE_STAFF) {
                    System.out.println("Error: Invalid member type code.");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Type must be a number.");
                return;
            }

            System.out.print("Enter Station (Library, Canteen, Dorm): ");
            String station = scanner.nextLine().trim();

            if (!stationBikes.containsKey(station)) {
                System.out.println("Error: Unknown station.");
                return;
            }

            int count = stationBikes.get(station);
            if (count <= 0) {
                System.out.println("Error: No bikes available at " + station + ".");
                return;
            }

            stationBikes.put(station, count - 1);
            activeRentals.put(memberId, station);
            activeMemberTypes.put(memberId, type);
            System.out.println("Bike rented successfully from " + station + "!");

        } else {
            System.out.print("Enter Member ID: ");
            String memberId = scanner.nextLine().trim();

            if (!activeRentals.containsKey(memberId)) {
                System.out.println("Error: No active rental found for member ID.");
                return;
            }

            System.out.print("Enter Return Station (Library, Canteen, Dorm): ");
            String returnStation = scanner.nextLine().trim();

            if (!stationBikes.containsKey(returnStation)) {
                System.out.println("Error: Unknown station.");
                return;
            }

            System.out.print("Enter Ride Time (in minutes): ");
            int minutes;
            try {
                minutes = Integer.parseInt(scanner.nextLine().trim());
                if (minutes < 0) {
                    System.out.println("Error: Minutes cannot be negative.");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid minutes input.");
                return;
            }

            int memberType = activeMemberTypes.get(memberId);
            String startStation = activeRentals.get(memberId);

            int rate = (memberType == TYPE_STUDENT) ? 2 : 3;
            int fee = minutes * rate;

            stationBikes.put(returnStation, stationBikes.get(returnStation) + 1);
            activeRentals.remove(memberId);
            activeMemberTypes.remove(memberId);

            System.out.println("\n--- RECEIPT ---");
            System.out.println("Member ID: " + memberId);
            System.out.println("Route: " + startStation + " -> " + returnStation);
            System.out.println("Duration: " + minutes + " minutes");
            System.out.println("Total Fee: " + fee + " baht");
            System.out.println("---------------");

            saveRideRecord(memberId, startStation, returnStation, minutes, fee);
        }
    }

    public static void saveRideRecord(String memberId, String startStation, String endStation, int minutes, int fee) {
        try (FileWriter fw = new FileWriter("rides.csv", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(memberId + "," + startStation + "," + endStation + "," + minutes + "," + fee);
            System.out.println("Ride recorded to rides.csv");
        } catch (IOException e) {
            System.out.println("Error saving ride to file: " + e.getMessage());
        }
    }
}