package com.supplytracker.entity.ControllerTests;

import com.supplytracker.Controller.ReportController;
import com.supplytracker.Controller.SessionManager;
import com.supplytracker.DTO.ShipmentResponseDTO;
import com.supplytracker.Entity.Shipment;
import com.supplytracker.Entity.User;
import com.supplytracker.Enums.CurrentStatus;
import com.supplytracker.Enums.Role;
import com.supplytracker.Repository.ShipmentRepository;
import com.supplytracker.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReportControllerTests {

    @Autowired
    ReportController reportController;

    @Autowired
    ShipmentRepository shipmentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SessionManager sessionManager;

    @Test
    @Transactional
    void testGetDelayedShipments() {
        User supplier = new User();
        supplier.setName("Test Supplier");
        supplier.setEmail("supplier_" + System.currentTimeMillis() + "@example.com");
        supplier.setPassword("password");
        supplier.setRole(Role.ADMIN);
        supplier = userRepository.save(supplier);

        sessionManager.login(supplier);
        Shipment shipment = new Shipment();
        shipment.setFromLocation("X");
        shipment.setToLocation("Y");
        shipment.setExpectedDelivery(new Date());
        shipment.setCurrentStatus(CurrentStatus.DELAYED);
        shipmentRepository.save(shipment);

        List<ShipmentResponseDTO> delayedShipments = reportController.getDelayedShipments();
        assertFalse(delayedShipments.isEmpty());
        assertTrue(delayedShipments.stream().anyMatch(s -> s.getId().equals(shipment.getId())));
    }

    @Test
    @Transactional
    void testGetDeliveredShipments() {
        User supplier = new User();
        supplier.setName("Test Supplier");
        supplier.setEmail("supplier_" + System.currentTimeMillis() + "@example.com");
        supplier.setPassword("password");
        supplier.setRole(Role.ADMIN);
        supplier = userRepository.save(supplier);

        sessionManager.login(supplier);
        Shipment shipment = new Shipment();
        shipment.setFromLocation("A");
        shipment.setToLocation("B");
        shipment.setExpectedDelivery(new Date());
        shipment.setCurrentStatus(CurrentStatus.DELIVERED);
        shipmentRepository.save(shipment);

        List<ShipmentResponseDTO> deliveredShipments = reportController.getDeliveredShipments();
        assertFalse(deliveredShipments.isEmpty());
        assertTrue(deliveredShipments.stream().anyMatch(s -> s.getId().equals(shipment.getId())));
    }
}

