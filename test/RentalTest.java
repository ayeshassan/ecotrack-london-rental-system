import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import models.Bike;
import models.EBike;
import models.EScooter;
import models.CargoBike;
import models.Moped;
import services.RentalManager;
import exceptions.RentalException;

public class RentalTest {

    private RentalManager manager;

    @BeforeEach
    public void setUp() {
        manager = new RentalManager();
        manager.addVehicle(new Bike("LDN-OXF-01"));
        manager.addVehicle(new EBike("LDN-CAM-01"));
        manager.addVehicle(new EScooter("LDN-SHR-01"));
        manager.addVehicle(new CargoBike("LDN-BRI-01"));
        manager.addVehicle(new Moped("LDN-HAC-01"));
    }

    @Test
    public void testBikeCost() {
        Bike bike = new Bike("TEST-01");
        assertEquals(7.98, bike.calculateCost(2), 0.01);
    }

    @Test
    public void testEBikeCost() {
        EBike ebike = new EBike("TEST-02");
        assertEquals(17.50, ebike.calculateCost(2), 0.01);
    }

    @Test
    public void testEScooterCost() {
        EScooter escooter = new EScooter("TEST-03");
        assertEquals(10.50, escooter.calculateCost(2), 0.01);
    }

    @Test
    public void testCargoBikeCost() {
        CargoBike cargobike = new CargoBike("TEST-04");
        assertEquals(13.98, cargobike.calculateCost(2), 0.01);
    }

    @Test
    public void testMopedCost() {
        Moped moped = new Moped("TEST-05");
        assertEquals(19.00, moped.calculateCost(2), 0.01);
    }

    @Test
    public void testRentVehicleSuccess() throws RentalException {
        manager.rentVehicle("LDN-OXF-01");
        assertFalse(manager.isVehicleAvailable("LDN-OXF-01"));
    }

    @Test
    public void testRentAlreadyRentedVehicle() throws RentalException {
        manager.rentVehicle("LDN-CAM-01");
        assertThrows(RentalException.class, () -> {
            manager.rentVehicle("LDN-CAM-01");
        });
    }

    @Test
    public void testReturnVehicle() throws RentalException {
        manager.rentVehicle("LDN-SHR-01");
        manager.returnVehicle("LDN-SHR-01");
        assertTrue(manager.isVehicleAvailable("LDN-SHR-01"));
    }
}