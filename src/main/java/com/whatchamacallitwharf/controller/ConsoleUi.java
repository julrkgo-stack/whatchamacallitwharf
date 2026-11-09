
package com.whatchamacallitwharf.controller;

import java.util.Scanner;
import java.util.List;
import java.util.Optional;
import com.whatchamacallitwharf.exception.DataAccessException;
import com.whatchamacallitwharf.model.Vessel;
import com.whatchamacallitwharf.service.VesselService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConsoleUi {
    private final VesselService vesselService;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleUi(VesselService vesselService) {
        this.vesselService = vesselService;
    }

    public void start() {

        runMenuChoices();
        scanner.close();
    }

    public void runMenuChoices() {
        boolean running = true;

        do {
            printMenu();
            int choice = readMenuChoice(1, 4);
            String currentAction = "menu selection";
            try {
                switch (choice) {
                    case 1 -> 
                    {
                        currentAction = "add vessel";
                        addVessel();
                    }
                    case 2 -> 
                    {
                        currentAction = "display vessel"; 
                        displayVessel();
                    }
                    case 3 -> 
                    {
                        currentAction = "display all vessels"; 
                        displayAllVessels();
                    }
                    case 4 -> {
                        currentAction = "exit";
                        System.out.println("Exiting... Cheers!");
                        running = false;
                    }
                }
            } catch (IllegalArgumentException e) {
                handleUserInputError(e, currentAction);
            } catch (DataAccessException e) {
                handleDataAccessError(e, currentAction);            } 
            System.out.println(); // Blank line for spacing
        } while (running);
    }

    private static void printMenu() {
        System.out.println("=== Command Prompt Menu ===");
        System.out.println("1. Add Vessel");
        System.out.println("2. Display Vessel");
        System.out.println("3. Display All Vessels");
        System.out.println("4. Exit");
        System.out.print("Select an option: ");
    }

    private void addVessel() {
        String name = "";
        String type = "";
        String color = "";
        String pattern = "";
        String material = "";
        int capacity = 0;
        boolean dishwasherSafe = false;

        System.out.print("Enter vessel name: ");
        name = readNameLikeText();
        System.out.print("Enter vessel type: ");
        type = readFreeText();
        System.out.print("Enter vessel color: ");
        color = readFreeText();
        System.out.print("Enter vessel pattern: ");
        pattern = readFreeText();
        System.out.print("Enter vessel material: ");
        material = readFreeText();
        System.out.print("Enter vessel capacity in ounces: ");
        capacity = readPositiveInt("capacity");
        System.out.print("Is vessel dishwasher safe?: ");
        dishwasherSafe = readYesOrNo();

        System.out.println("Your vessel will be -> " + name + " : " + type + " : " + color + " : " + pattern + " : " + material + " : " + capacity + " : " + dishwasherSafe);
        vesselService.addVessel(new Vessel(name, type, color, pattern, material, capacity, dishwasherSafe));
    }

    private void displayVessel() {
        System.out.print("Enter vessel ID: ");
        int id = readPositiveInt("id");

        Optional<Vessel> vessel = vesselService.getVesselProfile(id);
        vessel.ifPresentOrElse(
            v -> System.out.println("You chose: " + v.getName()),
            () -> System.out.println("No vessel was found for ID " + id));
    }

    private void displayAllVessels() {
        List<Vessel> vessels = vesselService.getAllVessels();
        if(!vessels.isEmpty()) {
            vessels.forEach(System.out::println);
            return;
        } 
        System.out.println("No vessels available at this time.");
    }

    private boolean readYesOrNo() {
        String userInput = "";
        boolean isValid = false;
        List<String> yesVariants = List.of("yes", "y", "yeah", "yep", "yup", "sure", "true", "indeed", "for sure", "positive", "1");
        List<String> noVariants = List.of("no", "n", "nah", "nope", "nuh", "probably not", "false", "no way", "not sure", "negative", "0");
        while (!isValid) {
            if (scanner.hasNextLine()) {
                userInput = scanner.nextLine().trim().toLowerCase();
                // Error Check: Check if the string is a recognized yes or no variant
                if (!yesVariants.contains(userInput) && !noVariants.contains(userInput)) {
                    System.out.println("Yes or No not recognized. Please try again:");
                    continue;
                }
                isValid = true;
            }
        }
        return yesVariants.contains(userInput);
    }

private int readMenuChoice(int min, int max) {
    while (true) {
        if (!scanner.hasNextInt()) {
            System.out.println("Invalid input! Please enter a whole number.");
            scanner.nextLine(); // purge the bad token/line
            System.out.print("Enter a valid menu option: ");
            continue;
        }

        int value = scanner.nextInt();
        scanner.nextLine(); // purge newline left by nextInt

        if (value < min || value > max) {
            System.out.println("Please select a number from " + min + " to " + max + ".");
            System.out.print("Enter a valid menu option: ");
            continue;
        }

        return value;
    }
}


    private int readPositiveInt(String fieldName) {
        while (true) {
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input! " + fieldName + " must be a positive whole number.");
                scanner.nextLine();
                System.out.print("Enter " + fieldName + ": ");
                continue;
            }
            int value = scanner.nextInt();
            scanner.nextLine();
            if (value <= 0) {
                System.out.println(fieldName + " must be greater than zero.");
                System.out.print("Enter " + fieldName + ": ");
                continue;
            }
            return value;
        }
    }


    private String readNameLikeText() {

        String userInput = "";
        boolean isValid = false;

        while (!isValid) {
            if (scanner.hasNextLine()) {
                userInput = scanner.nextLine().trim();

                // Error Check 1: Check if the string is empty
                if (userInput.isEmpty()) {
                    System.out.println("Error: Input cannot be empty. Please try again:");
                    continue;
                }

                // Error Check 2: Check if it contains only letters (no digits or symbols)
                if (!userInput.matches("[a-zA-Z\\s]+")) {
                    System.out.println("Error: Name can only contain letters and spaces. Please try again:");
                    continue;
                }

                // All checks passed
                isValid = true;
            }
        }
        return userInput;
    }

    private String readFreeText() {

        String userInput = "";
        boolean isValid = false;

        while (!isValid) {
            if (scanner.hasNextLine()) {
                userInput = scanner.nextLine().trim();

                // Error Check: Check if the string is empty
                if (userInput.isEmpty()) {
                    System.out.println("Error: Input cannot be empty. Please try again:");
                    continue;
                }
                isValid = true;
            }
        }
        return userInput;
    }

    private static final DateTimeFormatter ERROR_ID_FORMAT =
        DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss-SSS");

    private String nextErrorRef(String prefix) {
        String timestamp = LocalDateTime.now().format(ERROR_ID_FORMAT);
        int suffix = java.util.concurrent.ThreadLocalRandom.current().nextInt(1000, 10000);
        return prefix + "-" + timestamp + "-" + suffix;
    }

    private void handleUserInputError(IllegalArgumentException e, String action) {
        String ref = nextErrorRef("VAL");
        System.out.println("Could not complete " + action + ". Reference: " + ref);
        System.err.println("[" + ref + "] Validation error during " + action + ": " + e.getMessage());
    }

    private void handleDataAccessError(DataAccessException e, String action) {
        String ref = nextErrorRef("DB");
        System.out.println("Could not complete " + action + " due to a database issue. Reference: " + ref);
        System.err.println("[" + ref + "] Data access error during " + action + ": " + e.getMessage());
    }
}
