package com.whatchamacallitwharf.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.whatchamacallitwharf.model.Vessel;
import com.whatchamacallitwharf.service.VesselService;

// TODO
// implement this, just plugging it in right now for the future
// Spring API path is not fully wireable yet if/when you run it through Spring context.
// VesselApiController.java:10 is a Spring bean, but VesselService.java:9 is not annotated as a Spring bean, 
// and DAO impl is manually created in PlayDAOMain.java:23.

// Impact: REST controller construction can fail at runtime in Spring mode unless you add bean 
// configuration/annotations.

// For current CLI/manual wiring flow, this is fine.

@RestController
@RequestMapping("/api/vessels")
public class VesselApiController {
    private final VesselService vesselService;

    // Spring injects the same service here
    public VesselApiController(VesselService vesselService) {
        this.vesselService = vesselService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vessel> getVessel(@PathVariable int id) {
        return vesselService.getVesselProfile(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}


// he Web API UI (using a framework like Spring Boot)
// If you switch to an API, you use the exact same service, but expose it via HTTP.



// 5. Entry Point (Optional)
// UserController.java # Handles API requests / User interface