package com.supplytracker.Controller;

import com.supplytracker.DTO.DeliveryStatusDTO;
import com.supplytracker.DTO.ShipmentResponseDTO;
import com.supplytracker.Enums.Role;
import com.supplytracker.Repository.CheckpointRepository;
import com.supplytracker.Enums.CurrentStatus;
import com.supplytracker.Repository.ShipmentRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reports")
@Tag(name = "Report")
public class ReportController {

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private CheckpointRepository checkpointLogRepository;
    @Autowired
    private SessionManager sessionManager;


    @GetMapping("/delayed-shipments")
    public List<ShipmentResponseDTO> getDelayedShipments() {
        sessionManager.checkValidUser(Role.ADMIN);

        return shipmentRepository.findByCurrentStatus(CurrentStatus.DELAYED)
                .stream()
                .map(ShipmentResponseDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/delivery-performance")
    public List<ShipmentResponseDTO> getDeliveredShipments() {
        sessionManager.checkValidUser(Role.ADMIN);
        return shipmentRepository.findByCurrentStatus(CurrentStatus.DELIVERED)
                .stream()
                .map(ShipmentResponseDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/delivery-status")
    public DeliveryStatusDTO getDeliveryStatus() {
        sessionManager.checkValidUser(Role.ADMIN);

        long totalShipments = shipmentRepository.count();
        long delayedShipments = checkpointLogRepository.countByStatus("DELAYED");
        long delivered = shipmentRepository.countByCurrentStatus(CurrentStatus.DELIVERED);
        long onTimeShipments = totalShipments - delayedShipments;

        return new DeliveryStatusDTO(totalShipments, delayedShipments, onTimeShipments, delivered);
    }

}
