package com.whatchamacallitwharf.service;

import com.whatchamacallitwharf.model.Vessel;
import com.whatchamacallitwharf.dao.VesselDao;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VesselServiceSelfTestRunner {

    public static void main(String[] args){
        testMain();
    }

    public static void testMain() {
        System.out.println("In tests.");
        addVessel_blankName_throwsIllegalArgumentException();
        addVessel_valid_callsDaoOnce();
        addVessel_zeroCapacity_throwsIllegalArgumentException();
        System.out.println("All VesselService tests passed.");
    }


    static void addVessel_blankName_throwsIllegalArgumentException() {
        VesselDao fakeDao = new FakeVesselDao();
        VesselService service = new VesselService(fakeDao);

        Vessel invalid = new Vessel("", "Mug", "Blue", "None", "Ceramic", 12, true);

        try {
            service.addVessel(invalid);
            throw new AssertionError("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            if (!"vessel name is required".equals(ex.getMessage())) {
                throw new AssertionError("Unexpected message: " + ex.getMessage());
            }
        }
    }

    static void addVessel_zeroCapacity_throwsIllegalArgumentException() {
        VesselDao fakeDao = new FakeVesselDao();
        VesselService service = new VesselService(fakeDao);

        Vessel invalid = new Vessel("Not A Vessel", "Mug", "Blue", "None", "Ceramic", 0, true);

        try {
            service.addVessel(invalid);
            throw new AssertionError("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            if (!"capacity must be greater than 0".equals(ex.getMessage())) {
                throw new AssertionError("Unexpected message: " + ex.getMessage());
            }
        }
    }

    static void addVessel_valid_callsDaoOnce() {
        FakeVesselDao fakeDao = new FakeVesselDao();
        VesselService service = new VesselService(fakeDao);

        Vessel valid = new Vessel("Camp Cup", "Mug", "Blue", "Plain", "Steel", 12, true);
        service.addVessel(valid);

        if (fakeDao.addCalls != 1) {
            throw new AssertionError("Expected addCalls=1 but was " + fakeDao.addCalls);
        }
    }

    private static class FakeVesselDao implements VesselDao {
        int addCalls = 0;
        Vessel lastAdded;
        List<Vessel> vessels = new ArrayList<>();

        @Override
        public void addVessel(Vessel vessel) {
            addCalls++;
            lastAdded = vessel;
            vessels.add(vessel);
        }

        @Override
        public List<Vessel> getAllVessels() {
            return vessels;
        }

        @Override
        public Optional<Vessel> getVesselById(int id) {
            return vessels.stream().filter(v -> v.getId() == id).findFirst();
        }
    }
}
