package models;

public class EBike extends Vehicle {

    public EBike(String id) {
        super(id);
    }

    @Override
    public double calculateCost(int hours) {
        return hours * 8.75;
    }

    @Override
    public String toString() {
        return "[EBike] " + getId() + " | £8.75/hr | Available: " + isAvailable();
    }

    @Override
    public String getVehicleType() {
        return "EBike";
    }
}