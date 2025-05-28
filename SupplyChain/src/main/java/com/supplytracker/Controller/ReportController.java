package com.supplytracker.controller;

import com.supplytracker.model.Shipment;
import com.supplytracker.model.enums.CurrentStatus;
import com.supplytracker.repository.ShipmentRepository;
import com.supplytracker.repository.CheckpointLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private CheckpointRepository checkpointLogRepository;


    @GetMapping("/delayed-shipments")
    public List<Shipment> getDelayedShipments() {
        return shipmentRepository.findByCurrentStatus(ShipmentStatus.DELAYED);
    }
hr
    @GetMapping("/delivery-performance")
    public List<Shipment> getDeliveredShipments() {
        return shipmentRepository.findByCurrentStatus(ShipmentStatus.DELIVERED);
    }
}
