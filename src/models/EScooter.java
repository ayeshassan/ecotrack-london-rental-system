package models;

public class EScooter extends Vehicle {

    public EScooter(String id) {
        super(id);
    }

    @Override
    public double calculateCost(int hours) {
        return hours * 5.25;
    }

    @Override
    public String toString() {
        return "[EScooter] " + getId() + " | £5.25/hr | Available: " + isAvailable();
    }

    @Override
    public String getVehicleType() {
        return "EScooter";
    }
}