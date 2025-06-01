package com.supplytracker.entity.ControllerTests;

import com.supplytracker.Controller.CheckpointController;
import com.supplytracker.Controller.SessionManager;
import com.supplytracker.DTO.CheckpointDTO;
import com.supplytracker.DTO.CheckpointResponseDTO;
import com.supplytracker.Entity.Shipment;
import com.supplytracker.Entity.User;
import com.supplytracker.Enums.CurrentStatus;
import com.supplytracker.Enums.Role;
import com.supplytracker.Exception.AccessDeniedCustomException;
import com.supplytracker.Repository.ShipmentRepository;
import com.supplytracker.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CheckpointControllerTest {

    @Autowired
    private CheckpointController checkpointController;

    @Autowired
    private ShipmentRepository shipmentRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SessionManager sessionManager;

    @Test
    @Transactional
    void testLogCheckpoint() {
        User supplier = new User();
        supplier.setName("Test Supplier");
        supplier.setEmail("supplier_" + System.currentTimeMillis() + "@example.com");
        supplier.setPassword("password");
        supplier.setRole(Role.ADMIN);
        supplier = userRepository.save(supplier);
        sessionManager.login(supplier);
        Shipment shipment = new Shipment();
        shipment.setFromLocation("Source A");
        shipment.setToLocation("Dest B");
        shipment.setCurrentStatus(CurrentStatus.CREATED);
        shipment.setExpectedDelivery(new Date());
        shipment = shipmentRepository.save(shipment);
        CheckpointDTO dto = new CheckpointDTO();
        dto.setShipmentId(shipment.getId());
        dto.setStatus("CREATED");
        ResponseEntity<CheckpointResponseDTO> response = checkpointController.logCheckpoint(dto);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("CREATED", response.getBody().getStatus());
        assertEquals(shipment.getId(), response.getBody().getShipmentId());
    }

    @Test
    @Transactional
    void testLogCheckpoint_AccessDeniedException() {
        User supplier = new User();
        supplier.setName("Test Supplier");
        supplier.setEmail("supplier_" + System.currentTimeMillis() + "@example.com");
        supplier.setPassword("password");
        supplier.setRole(Role.SUPPLIER);
        supplier = userRepository.save(supplier);

        sessionManager.login(supplier);

        Shipment shipment = new Shipment();
        shipment.setFromLocation("Log A");
        shipment.setToLocation("Log B");
        shipment.setExpectedDelivery(new Date());
        shipment.setCurrentStatus(CurrentStatus.CREATED);
        shipment = shipmentRepository.save(shipment);

        CheckpointDTO dto = new CheckpointDTO();
        dto.setShipmentId(shipment.getId());
        dto.setStatus("CREATED");

        AccessDeniedCustomException exception = assertThrows(AccessDeniedCustomException.class, () -> {
            checkpointController.logCheckpoint(dto);
        });

        assertEquals("Access denied for role: SUPPLIER", exception.getMessage());
    }
}
