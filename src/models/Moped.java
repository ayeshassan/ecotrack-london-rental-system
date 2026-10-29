package models;

public class Moped extends Vehicle {

    public Moped(String id) {
        super(id);
    }

    @Override
    public double calculateCost(int hours) {
        return hours * 9.50;
    }

    @Override
    public String toString() {
        return "[Moped] " + getId() + " | £9.50/hr | Available: " + isAvailable();
    }

    @Override
    public String getVehicleType() {
        return "Moped";
    }
}