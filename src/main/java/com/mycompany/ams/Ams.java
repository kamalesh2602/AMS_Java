package com.mycompany.ams;

import java.util.Scanner;
import java.util.*;
import java.io.*;

// Class to represent a Car with its attributes and functionalities
class Cars {

    String make;  // Manufacturer of the car
    String model; // Model of the car
    int seating;  // Seating capacity of the car
    double price; // Price of the car
    String type;  // Type of the car (e.g., fuel, diesel)

    // Constructor to initialize car details
    Cars(String make, String model, int seating, double price, String type) {
        this.make = make;
        this.model = model;
        this.seating = seating;
        this.price = price;
        this.type = type;
    }

    // Getters for the car's properties
    double getPrice() {
        return price;
    }

    String getMake() {
        return make;
    }

    String getModel() {
        return model;
    }

    int getSeating() {
        return seating;
    }

    String getType() {
        return type;
    }

    // Method to display car details
    void display() {
        System.out.print("Make: " + make + "\t");
        System.out.print("Model: " + model + "\t");
        System.out.print("Seating capacity: " + seating + "\t");
        System.out.print("Price: " + price + "\t");
        System.out.println("Type: " + type);
        System.out.println("");
    }

    // Converts car details into a CSV-style string
    @Override
    public String toString() {
        return make + "," + model + "," + seating + "," + price + "," + type;
    }

    // Factory method to create a car object from a CSV-style string
    static Cars fromString(String line) {
        String[] parts = line.split(",");
        String make = parts[0];
        String model = parts[1];
        int seating = Integer.parseInt(parts[2]);
        double price = Double.parseDouble(parts[3]);
        String type = parts[4];
        return new Cars(make, model, seating, price, type);
    }
}

// Subclass of Cars representing a car being sold by a customer
class SellCar extends Cars {

    String customer; // Name of the customer selling the car

    // Constructor to initialize SellCar details
    SellCar(String make, String model, int seating, double price, String type, String customer) {
        super(make, model, seating, price, type);
        this.customer = customer;
    }

    // Overrides to include customer name in CSV-style string
    @Override
    public String toString() {
        return make + "," + model + "," + seating + "," + price + "," + type + "," + customer;
    }

    // Display car and customer details
    @Override
    void display() {
        System.out.println("Customer Name: " + customer);
        System.out.println("Make: " + make);
        System.out.println("Model: " + model);
        System.out.println("Seating capacity: " + seating);
        System.out.println("Price: " + price);
        System.out.println("Type: " + type);
    }
}

// Main class for the Automobile Management System
public class Ams {

    // Constants for file names
    final static String fileName = "cars.txt";
    final static String sellfileName = "sellcars.txt";

    // Lists to hold filtered and sold cars
    static List<Cars> filter = new ArrayList<>();
    static List<SellCar> sell = new ArrayList<>();

    // Handles car booking
    static void booking(String model, int confirm) {
        if (!filter.isEmpty()) {
            for (Cars i : filter) {
                if (i.getModel().equals(model)) {
                    i.display();

                    if (confirm == 1) {
                        System.out.println("Booking Confirmed !!");
                        Calendar c = Calendar.getInstance();
                        System.out.println("You will get your delivery by");
                        System.out.println("Booking Date: " + c.get(Calendar.DATE) + " " + c.get(Calendar.YEAR));
                    } else {
                        System.out.println("Booking Cancelled !!");
                    }
                }
            }
        } else {
            System.out.println("No bookings found!");
        }
    }

    // Reads car data from file
    static List<Cars> loadCarsFromFile() {
        File file = new File(fileName);
        List<Cars> car = new ArrayList<>();
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    car.add(Cars.fromString(line));
                }
            } catch (IOException e) {
                System.out.println("Error reading file: " + e.getMessage());
            }
        }
        return car;
    }

    // Saves car data to file
    static void saveCarsToFile(List<Cars> car) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Cars i : car) {
                writer.write(i.toString());
                writer.newLine();
            }
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    // Saves sold car data to file
    static void saveSellCarsToFile(List<SellCar> sell) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(sellfileName, true))) {
            for (SellCar i : sell) {
                writer.write(i.toString());
                writer.newLine();
            }
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    // Displays all cars
    static void initialdisplay(List<Cars> car) {
        for (Cars i : car) {
            i.display();
        }
    }

    // Filter and display cars based on various criteria
    static void displayCarsAbovePrice(List<Cars> car, double pricefilter) {
        System.out.println("\nCars above Price " + pricefilter + ":");
        for (Cars i : car) {
            if (i.getPrice() > pricefilter) {
                i.display();
                filter.add(i);
            }
        }
    }

    static void displayCarsBelowPrice(List<Cars> car, double pricefilter) {
        System.out.println("\nCars below price " + pricefilter + ":");
        filter.clear();
        for (Cars i : car) {
            if (i.getPrice() < pricefilter) {
                i.display();
                filter.add(i);
            }
        }
    }

    static void displayCarsByMake(List<Cars> car, String makefilter) {
        System.out.println("\nCar of make , " + makefilter + ":");
        filter.clear();
        for (Cars i : car) {
            if (i.getMake().equalsIgnoreCase(makefilter)) {
                i.display();
                filter.add(i);
            }
        }
    }

    static void displayCarsBySeat(List<Cars> car, int seatfilter) {
        System.out.println("\nCars of seating capacity, " + seatfilter + ":");
        filter.clear();
        for (Cars i : car) {
            if (i.getSeating() == seatfilter) {
                i.display();
                filter.add(i);
            }
        }
    }

    static void displayCarsByType(List<Cars> car, String typefilter) {
        System.out.println("\nCars of type (eg:Fuel,diesel) " + typefilter + ":");
        filter.clear();
        for (Cars i : car) {
            if (i.getType().equalsIgnoreCase(typefilter)) {
                i.display();
                filter.add(i);
            }
        }
    }

    // Entry point for the application
    public static void main(String[] args) {
        List<Cars> car = loadCarsFromFile();
        Scanner input = new Scanner(System.in);

        System.out.println("Automobile Management System");
        System.out.println("CarGo");
        System.out.println("Our Partners: BMW | SUZUKI | TATA | TOYOTA | FORD");

        int running = 1;
        while (running == 1) {
            System.out.println("1. Admin");
            System.out.println("2. Buy Car");
            System.out.println("3. Sell Car");
            System.out.print("Enter choice: ");
            int ch = input.nextInt();
            input.nextLine();
            switch (ch) {
                // Admin section
                case 1:
                    System.out.println("Welcome Admin\n");
                    System.out.print("Enter Password: ");
                    String passwd = input.nextLine();
                    if (passwd.equals("admin")) {
                        System.out.println("Enter new arrivals: ");
                        int addmore = 1;
                        while (addmore == 1) {
                            System.out.print("Enter make: ");
                            String make = input.nextLine();
                            System.out.print("Enter model: ");
                            String model = input.nextLine();
                            System.out.print("Enter seating: ");
                            int seating = input.nextInt();
                            input.nextLine();
                            System.out.print("Enter price: ");
                            double price = input.nextDouble();
                            input.nextLine();
                            System.out.print("Enter type(fuel,diesel): ");
                            String type = input.nextLine();

                            car.add(new Cars(make, model, seating, price, type));
                            saveCarsToFile(car);
                            System.out.print("Want to add more? Enter 1");
                            addmore = input.nextInt();
                            input.nextLine();
                            if (addmore != 1) {
                                System.out.println("Exiting!");
                                break;
                            }
                        }
                        break;
                    } else {
                        System.out.println("Wrong Password");
                        break;
                    }

                // Buy car section
                case 2:
                    System.out.println("Welcome Customer to Car Buying Section\n");
                    initialdisplay(car);
                    System.out.println("Filter cars by:");
                    System.out.println("1. Cars Above Price");
                    System.out.println("2. Cars Below Price");
                    System.out.println("3. Cars of Make");
                    System.out.println("4. Cars with Seating capacity");
                    System.out.println("5. Cars with Fuel type");
                    int ch1 = input.nextInt();
                    input.nextLine();
                    System.out.println("");
                    switch (ch1) {
                        case 1:
                            System.out.print("Enter the price to filter by: ");
                            double pricefilter = input.nextDouble();
                            input.nextLine();
                            displayCarsAbovePrice(car, pricefilter);
                            break;
                        case 2:
                            System.out.print("Enter the price to filter by: ");
                            double pricebelowfilter = input.nextDouble();
                            input.nextLine();
                            displayCarsBelowPrice(car, pricebelowfilter);
                            break;
                        case 3:
                            System.out.print("Enter the make to filter by: ");
                            String makefilter = input.nextLine();
                            displayCarsByMake(car, makefilter);
                            break;
                        case 4:
                            System.out.print("Enter the seating to filter by: ");
                            int seatfilter = input.nextInt();
                            input.nextLine();
                            displayCarsBySeat(car, seatfilter);
                            break;
                        case 5:
                            System.out.print("Enter the type to filter by: ");
                            String typefilter = input.nextLine();
                            displayCarsByType(car, typefilter);
                            break;
                        default:
                            System.out.println("Invalid Choice");
                            break;
                    }
                    System.out.print("Enter the model to confirm booking: ");
                    String confirm_model = input.nextLine();
                    System.out.println("Enter 1 to confirm or 0 to cancel: ");
                    int confirm = input.nextInt();
                    input.nextLine();
                    booking(confirm_model, confirm);
                    break;

                // Sell car section
                case 3:
                    System.out.println("Welcome to Car Selling Section");
                    System.out.println("Customer, Enter your name");
                    String cname = input.nextLine();
                    System.out.println("Customer, Enter your car model");
                    String cmodel = input.nextLine();
                    boolean carfound = false;
                    for (Cars i : car) {
                        if (i.getModel().equalsIgnoreCase(cmodel)) {
                            carfound = true;
                            String cmake = i.make;
                            int cseat = i.seating;
                            double cprice = i.price;
                            String ctype = i.type;
                            SellCar c1 = new SellCar(cmake, cmodel, cseat, cprice, ctype, cname);
                            sell.add(c1);
                            saveSellCarsToFile(sell);
                            c1.display();
                            break;
                        }
                    }
                    if (!carfound) {
                        System.out.println("Enter Make: ");
                        String cmake = input.nextLine();
                        System.out.println("Enter seat: ");
                        int cseat = input.nextInt();
                        input.nextLine();
                        System.out.println("Enter Price: ");
                        double cprice = input.nextDouble();
                        input.nextLine();
                        System.out.println("Enter Fuel Type: ");
                        String ctype = input.nextLine();
                        car.add(new Cars(cmake, cmodel, cseat, cprice, ctype));
                        SellCar c1 = new SellCar(cmake, cmodel, cseat, cprice, ctype, cname);
                        sell.add(c1);
                        saveSellCarsToFile(sell);
                        saveCarsToFile(car);
                        c1.display();
                    }
                    break;

                // Invalid choice
                default:
                    System.out.println("Invalid Choice..");
                    break;
            }
            System.out.println("Do you want to continue? Press 1 to continue...");
            running = input.nextInt();
            if (running != 1) {
                System.out.println("Thank YOU");
                System.out.println("Exiting...");
                break;
            }
        }
    }
}
