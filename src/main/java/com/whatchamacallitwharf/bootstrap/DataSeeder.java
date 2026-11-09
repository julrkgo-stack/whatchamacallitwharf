package com.whatchamacallitwharf.bootstrap;

import com.whatchamacallitwharf.model.Vessel;
import com.whatchamacallitwharf.service.VesselService;

public class DataSeeder {
    
    private final VesselService vesselService;

    // Dependency Injection: Pass the Service via the constructor
    public DataSeeder(VesselService vesselService) {
        this.vesselService = vesselService;
    }

    // Seeder mutates one vessel instance for demo-only data setup.
    public void seedDatabase() {

        Vessel seedVesselOne = new Vessel("Flowerplunk", "vase", "red", "smooth", "glass", 14, false);  //id hard coded for now, under construction
        Vessel seedVesselTwo = new Vessel("Rustico", "gourd", "brown", "wood grain", "gourd", 6, false);  //id hard coded for now, under construction
        Vessel seedVesselThree = new Vessel("Sal", "salt shaker", "none", "smooth", "glass", 14, false);  //id hard coded for now, under construction

        vesselService.addVessel(seedVesselOne);
        vesselService.addVessel(seedVesselTwo);
        vesselService.addVessel(seedVesselThree);

    }
}
