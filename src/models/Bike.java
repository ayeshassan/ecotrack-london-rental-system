package models;

public class Bike extends Vehicle {

    public Bike(String id) {
        super(id);
    }

    @Override
    public double calculateCost(int hours) {
        return hours * 3.99;
    }

    @Override
    public String toString() {
        return "[Bike] " + getId() + " | £3.99/hr | Available: " + isAvailable();
    }

    @Override
    public String getVehicleType() {
        return "Bike";
    }
}