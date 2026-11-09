package com.whatchamacallitwharf;

import com.whatchamacallitwharf.resources.config.Database;
import com.whatchamacallitwharf.bootstrap.DataSeeder;

import com.whatchamacallitwharf.dao.VesselDao;
import com.whatchamacallitwharf.dao.VesselDaoImpl;
import com.whatchamacallitwharf.service.VesselService;

import com.whatchamacallitwharf.controller.ConsoleUi;
import com.whatchamacallitwharf.exception.DataAccessException;


public class WW {

    public static void main(String[] args) {
        System.out.println("🧜‍♀️ Welcome to Whatchimicalit Wharf! 🧜‍♂️");

         if(initializeApp()) {
             openShop();
         }
    }

    private static void openShop() {
        // 1. Initialize data layer
        VesselDao vesselDao = new VesselDaoImpl();
        
        // 2. Inject data layer into service layer
        VesselService vesselService = new VesselService(vesselDao);

        // Inject data layer into service layer
        DataSeeder dataSeeder = new DataSeeder(vesselService);

        // Temporary step
        // TODO, plug in a persistent database
        try {
            dataSeeder.seedDatabase();
        } catch (IllegalArgumentException e) {
            System.out.println("Seed data is invalid. Closing shop.");
            System.err.println("Seed validation error: " + e.getMessage());
            return;
        } catch (DataAccessException e) {
            System.out.println("Seed failed due to database error. Closing shop.");
            System.err.println("Seed database error: " + e.getMessage());
            return;
        }

        // 3. Pick your UI! 
        
        // To run CLI:
        ConsoleUi cli = new ConsoleUi(vesselService);
        cli.start();
        
        // Or if you were running Spring Boot for the API, you'd do:
        // SpringApplication.run(MainApplication.class, args);
    }

    private static boolean initializeApp() {
        boolean isSuccess = false;
        try {
            Database.initializeDatabase();
            System.out.println("Database Initialized.");
            isSuccess = true;
        } catch (DataAccessException e) {
            System.out.println("Dearest guest, the " + e.getMessage() + ". We will need to close shop. So very sorry.");
        }        
        return isSuccess;
    }

}
