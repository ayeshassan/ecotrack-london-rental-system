package models;

public class CargoBike extends Vehicle {

    public CargoBike(String id) {
        super(id);
    }

    @Override
    public double calculateCost(int hours) {
        return hours * 6.99;
    }

    @Override
    public String toString() {
        return "[CargoBike] " + getId() + " | £6.99/hr | Available: " + isAvailable();
    }

    @Override
    public String getVehicleType() {
        return "CargoBike";
    }
}