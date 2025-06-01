package com.supplytracker.entity.ServiceTests;


import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import com.supplytracker.DTO.*;
import com.supplytracker.Entity.Shipment;
import com.supplytracker.Entity.ShipmentCheckpoint;
import com.supplytracker.Enums.CurrentStatus;
import com.supplytracker.Repository.CheckpointRepository;
import com.supplytracker.Repository.ShipmentRepository;
import com.supplytracker.Service.CheckpointService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest

public class CheckpointServiceTest {

    @Autowired
    ShipmentRepository shipmentRepository;
    @Autowired
    CheckpointRepository checkpointRepository;
    @Autowired
    CheckpointService checkpointService;

    @Test
    void testLogCheckpoint() {
        Shipment shipment = shipmentRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Shipment with ID 1 not found."));
        CheckpointDTO dto = new CheckpointDTO();
        dto.setShipmentId(shipment.getId());
        dto.setStatus("CREATED");
        CheckpointResponseDTO response = checkpointService.logCheckpoint(dto);
        assertNotNull(response);
    }
    @Test
    void testGetLogHistory() {
        Shipment shipment = new Shipment();
        shipment.setFromLocation("Warehouse A");
        shipment.setToLocation("Warehouse B");
        shipment.setCurrentStatus(CurrentStatus.CREATED);
        shipment.setExpectedDelivery(new Date(System.currentTimeMillis() + 5L * 24 * 60 * 60 * 1000));
        shipment = shipmentRepository.save(shipment);

        ShipmentCheckpoint checkpoint = new ShipmentCheckpoint();
        checkpoint.setShipment(shipment);
        checkpoint.setStatus("CREATED");
        checkpoint.setTimestamp(LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault()));
        checkpointRepository.save(checkpoint);

        List<CheckpointResponseDTO> history = checkpointService.getlogHistory(String.valueOf(shipment.getId()));
        assertEquals(1, history.size());
        assertEquals("CREATED", history.get(0).getStatus());
    }
}
