package com.whatchamacallitwharf.service;

import com.whatchamacallitwharf.model.Vessel;
import com.whatchamacallitwharf.dao.VesselDao;
import java.util.List;
import java.util.Optional;
import java.util.Objects;


public class VesselService {

    private final VesselDao vesselDao;

    // Dependency Injection: Pass the DAO via the constructor
    public VesselService(VesselDao vesselDao) {
        this.vesselDao = vesselDao;
    }

    public Optional<Vessel> getVesselProfile(int id) {
        // Business logic could go here (e.g., checking permissions)
        return vesselDao.getVesselById(id);
    }

    public List<Vessel> getAllVessels() {
        return vesselDao.getAllVessels();
    }

    public void addVessel(Vessel vessel){
        // Service layer does business validation before touching DAO.
        Objects.requireNonNull(vessel, "vessel cannot be null"); 
        validateVessel(vessel);
        vesselDao.addVessel(vessel);
    }

    private void validateVessel(Vessel vessel) {
        if (isBlank(vessel.getName())) {
            throw new IllegalArgumentException("vessel name is required");
        }
        if (isBlank(vessel.getType())) {
            throw new IllegalArgumentException("vessel type is required");
        }
        if (isBlank(vessel.getColor())) {
            throw new IllegalArgumentException("vessel color is required");
        }
        if (isBlank(vessel.getMaterial())) {
            throw new IllegalArgumentException("vessel material is required");
        }
        if (vessel.getCapacity() <= 0) {
            throw new IllegalArgumentException("capacity must be greater than 0");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }


}




// The Service Layer is the secret to changing UIs. It holds your business logic (e.g., validation, calculations, transactions). 
// The UI layer will only interact with this service.

// 4. Calling Code / Business Logic
// UserService.java    # Coordinates transactions and calls DAOs