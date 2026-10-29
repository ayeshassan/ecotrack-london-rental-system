package models;

public abstract class Vehicle {

    private String id;
    private boolean available;

    public Vehicle(String id) {
        this.id = id;
        this.available = true;
    }

    public String getId() {
        return id;
    }

    public abstract String getVehicleType();

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean status) {
        available = status;
    }

    public abstract double calculateCost(int hours);

    public String toString() {
        return id + " | Available: " + available;
    }
}