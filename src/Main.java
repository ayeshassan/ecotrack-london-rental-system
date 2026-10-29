import models.Vehicle;
import services.RentalManager;
import services.FileLogger;
import services.VehicleFactory;
import exceptions.RentalException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        RentalManager manager = new RentalManager();
        FileLogger logger = FileLogger.getInstance("rental_log.txt");

        // Set up inventory
        manager.addVehicle(VehicleFactory.createVehicle("bike", "LDN-OXF-01"));
        manager.addVehicle(VehicleFactory.createVehicle("bike", "LDN-OXF-02"));
        manager.addVehicle(VehicleFactory.createVehicle("bike", "LDN-OXF-03"));

        manager.addVehicle(VehicleFactory.createVehicle("ebike", "LDN-CAM-01"));
        manager.addVehicle(VehicleFactory.createVehicle("ebike", "LDN-CAM-02"));
        manager.addVehicle(VehicleFactory.createVehicle("ebike", "LDN-CAM-03"));

        manager.addVehicle(VehicleFactory.createVehicle("escooter", "LDN-SHR-01"));
        manager.addVehicle(VehicleFactory.createVehicle("escooter", "LDN-SHR-02"));
        manager.addVehicle(VehicleFactory.createVehicle("escooter", "LDN-SHR-03"));

        manager.addVehicle(VehicleFactory.createVehicle("cargobike", "LDN-BRI-01"));
        manager.addVehicle(VehicleFactory.createVehicle("cargobike", "LDN-BRI-02"));
        manager.addVehicle(VehicleFactory.createVehicle("cargobike", "LDN-BRI-03"));

        manager.addVehicle(VehicleFactory.createVehicle("moped", "LDN-HAC-01"));
        manager.addVehicle(VehicleFactory.createVehicle("moped", "LDN-HAC-02"));
        manager.addVehicle(VehicleFactory.createVehicle("moped", "LDN-HAC-03"));

        Scanner scanner = new Scanner(System.in);

        System.out.println("=========================================");
        System.out.println("  Welcome to EcoTrack London");
        System.out.println("  Micro-Mobility Rentals");
        System.out.println("=========================================");
        System.out.print("Log in with your name: ");
        String customerName = scanner.nextLine();

        // Track all rented vehicle IDs and names
        ArrayList<String> rentedIds = new ArrayList<>();
        ArrayList<String> rentedNames = new ArrayList<>();

        // Show inventory
        manager.printInventory();

        System.out.print("\nEnter the ID of the vehicle you want to rent: ");
        String chosenId = scanner.nextLine().trim();

        try {  //this returns whatever vehicle the user picked and stores it in rented
            Vehicle rented = manager.rentVehicle(chosenId);
            int hours = readHours(scanner);
            String rentedVehicleName = rented.getVehicleType();
            double cost = rented.calculateCost(hours);

            rentedIds.add(chosenId);
            rentedNames.add(rentedVehicleName);

            System.out.println("\n--- Rental Receipt ---");
            System.out.println("Customer:  " + customerName);
            System.out.println("Vehicle:   " + rentedVehicleName);
            System.out.println("ID:        " + chosenId);
            System.out.println("Duration:  " + hours + " hours");
            System.out.printf("Total:     £%.2f%n", cost);
            System.out.println("----------------------");

            logger.log(FileLogger.LogLevel.INFO, customerName + " rented "
                + rentedVehicleName + " (" + chosenId + ") for " + hours + " hours. Cost: £" + cost);

            // Ask user if they want to continue
            System.out.print("\nDo you want to rent another vehicle? (yes/no): ");
            String continueChoice = scanner.nextLine().trim().toLowerCase();

            if (continueChoice.equals("yes") || continueChoice.equals("y")) {
                manager.printInventory();

                System.out.print("\nEnter the ID of the vehicle you want to rent: ");
                String secondChosenId = scanner.nextLine().trim();

                try {
                    Vehicle secondRented = manager.rentVehicle(secondChosenId);
                    int secondHours = readHours(scanner);
                    String secondRentedName = secondRented.getVehicleType();
                    double secondCost = secondRented.calculateCost(secondHours);
                    System.out.println(secondRentedName + " (" + secondChosenId + ") rented successfully.");

                    rentedIds.add(secondChosenId);
                    rentedNames.add(secondRentedName);

                    System.out.println("\n--- Rental Receipt ---");
                    System.out.println("Customer:  " + customerName);
                    System.out.println("Vehicle:   " + secondRentedName);
                    System.out.println("ID:        " + secondChosenId);
                    System.out.println("Duration:  " + secondHours + " hours");
                    System.out.printf("Total:     £%.2f%n", secondCost);
                    System.out.println("----------------------");

                    logger.log(FileLogger.LogLevel.INFO, customerName + " rented "
                        + secondRentedName + " (" + secondChosenId + ") for " + secondHours + " hours. Cost: £" + secondCost);

                } catch (RentalException e) {
                    System.out.println("Rental Error: " + e.getMessage());
                    logger.log(FileLogger.LogLevel.ERROR, "Failed rental: " + e.getMessage());
                }
            }

        } catch (RentalException e) {
            System.out.println("Rental Error: " + e.getMessage());
            logger.log(FileLogger.LogLevel.ERROR, "Failed rental: " + e.getMessage());
        }

        // Return vehicle
        if (!rentedIds.isEmpty()) {
            System.out.print("\nWould you like to return a vehicle? (yes/no): ");
            String returnChoice = scanner.nextLine().trim().toLowerCase();

            while (returnChoice.equals("yes") || returnChoice.equals("y")) {
                System.out.println("\nYour rented vehicles:");
                for (int i = 0; i < rentedIds.size(); i++) {
                    System.out.println("- " + rentedNames.get(i) + " (" + rentedIds.get(i) + ")");
                }

                System.out.print("Enter the ID of the vehicle you want to return: ");
                String returnId = scanner.nextLine().trim();

                try {
                    manager.returnVehicle(returnId);
                    logger.log(FileLogger.LogLevel.INFO, customerName + " returned " + returnId);
                    rentedIds.remove(returnId);
                } catch (RentalException e) {
                    System.out.println("Return Error: " + e.getMessage());
                    logger.log(FileLogger.LogLevel.ERROR, "Failed return: " + e.getMessage());
                }

                if (!rentedIds.isEmpty()) {
                    System.out.print("\nWould you like to return another vehicle? (yes/no): ");
                    returnChoice = scanner.nextLine().trim().toLowerCase();
                } else {
                    break;
                }
            }
        }

        // Final inventory
        System.out.println("\n-- Updated Inventory --");
        manager.printInventory();

        scanner.close();
    }

    // Keeps asking until the user types a whole number greater than 0
    private static int readHours(Scanner scanner) {
        while (true) {
            System.out.print("Enter number of hours: ");
            String input = scanner.nextLine().trim();
            try {
                int hours = Integer.parseInt(input);
                if (hours > 0) {
                    return hours;
                }
                System.out.println("Please enter a number greater than 0.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a whole number, e.g. 3.");
            }
        }
    }
}
//java -jar "lib/junit-platform-console-standalone-1.10.0.jar" -cp bin --scan-classpath
//java -cp bin Main