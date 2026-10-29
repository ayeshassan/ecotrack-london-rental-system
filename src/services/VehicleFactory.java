package services;

import models.Bike;
import models.EBike;
import models.EScooter;
import models.CargoBike;
import models.Moped;
import models.Vehicle;

public class VehicleFactory {

    public static Vehicle createVehicle(String type, String id) {
        switch (type.toLowerCase()) {
            case "bike":
                return new Bike(id);
            case "ebike":
                return new EBike(id);
            case "escooter":
                return new EScooter(id);
            case "cargobike":
                return new CargoBike(id);
            case "moped":
                return new Moped(id);
            default:
                throw new IllegalArgumentException("Unknown vehicle type: " + type);
        }
    }
}