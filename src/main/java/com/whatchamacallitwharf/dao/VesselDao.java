package com.whatchamacallitwharf.dao;

import com.whatchamacallitwharf.model.Vessel;
import java.util.List;
import java.util.Optional;

public interface VesselDao {
    void addVessel(Vessel vessel);
    List<Vessel> getAllVessels();
    Optional<Vessel> getVesselById(int id);
}

// 3. Data Access Objects
// UserDao.java        # Interface defining database operations
// UserDaoImpl.java    # SQL implementation details


