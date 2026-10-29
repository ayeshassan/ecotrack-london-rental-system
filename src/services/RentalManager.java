package services;

import models.Vehicle;
import exceptions.RentalException;
import java.util.ArrayList;

public class RentalManager {

    private ArrayList<Vehicle> vehicles;

    public RentalManager() {
        vehicles = new ArrayList<>();
    }

    public void addVehicle(Vehicle v) {
        vehicles.add(v);
    }

    public Vehicle rentVehicle(String id) throws RentalException {
        for (Vehicle v : vehicles) {
            if (v.getId().equals(id)) {
                if (!v.isAvailable()) {   //** 
                    throw new RentalException("Vehicle already rented!");
                }
                v.setAvailable(false);
                return v;
            }
            //The exception is thrown in RentalManager when a vehicle is already rented. 
            //It gets caught in Main where we print the error and log it.
            //We made a custom exception instead of using Java's built in ones so the error message is specific to our rental system.
        }
        throw new RentalException("Vehicle not found.");
    }

    public void returnVehicle(String id) throws RentalException {
        for (Vehicle v : vehicles) {
            if (v.getId().equals(id)) {
                v.setAvailable(true);
                System.out.println(v.getVehicleType() + " (" + id + ") returned successfully.");
                return;
            }
        }
        throw new RentalException("Vehicle not found.");
    }

    public void printInventory() {
        System.out.println("\n+---------------+-------------+-----------+--------------+");
        System.out.println("| Vehicle       | ID          | Rate/hr   | Availability |");
        System.out.println("+---------------+-------------+-----------+--------------+");
        for (Vehicle v : vehicles) {
            String availability = v.isAvailable() ? "Available" : "Unavailable";
            System.out.printf("| %-13s | %-11s | £%-8s | %-12s |%n",
                v.getVehicleType(),
                v.getId(),
                String.format("%.2f", v.calculateCost(1)),
                availability);
        }
        System.out.println("+---------------+-------------+-----------+--------------+");
    }
    public boolean isVehicleAvailable(String id) {
    for (Vehicle v : vehicles) {
        if (v.getId().equals(id)) {
            return v.isAvailable();
        }
    }
    return false;
}
}