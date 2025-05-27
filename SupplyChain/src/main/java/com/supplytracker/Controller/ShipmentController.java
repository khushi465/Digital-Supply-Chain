package com.example.DigitalSupplyChainTracker.Controller;

import org.springframework.web.bind.annotation.*;

@RestController

public class ShipmentController {

    @PostMapping("/api/shipments")

    @PutMapping("/api/shipments/{id}/assign")

    @GetMapping("/api/shpiments")

    @GetMapping("/api/shpiments/{id}")

    @PutMapping("/api/shipments/{id}/status")

}
