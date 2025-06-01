package com.supplytracker.entity.ControllerTests;

import com.supplytracker.Controller.SessionManager;
import com.supplytracker.Controller.ShipmentController;
import com.supplytracker.DTO.ShipmentDTO;
import com.supplytracker.DTO.ShipmentResponseDTO;
import com.supplytracker.Entity.Item;
import com.supplytracker.Entity.Shipment;
import com.supplytracker.Entity.User;
import com.supplytracker.Enums.CurrentStatus;
import com.supplytracker.Enums.Role;
import com.supplytracker.Repository.ItemRepository;
import com.supplytracker.Repository.ShipmentRepository;
import com.supplytracker.Repository.UserRepository;
import com.supplytracker.Service.ShipmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ShipmentControllerTests {

    @Autowired
    private ShipmentController shipmentController;

    @Autowired
    private ShipmentService shipmentService;

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    SessionManager sessionManager;

    private Item prepareItemWithSupplier() {
        User supplier = new User();
        supplier.setName("Test Supplier");
        supplier.setEmail("supplier_" + System.currentTimeMillis() + "@example.com");
        supplier.setPassword("password");
        supplier.setRole(Role.ADMIN);
        supplier = userRepository.save(supplier);

        sessionManager.login(supplier);
        User user = new User();
        user.setName("Supplier");
        user.setEmail("supplier_" + System.currentTimeMillis() + "@mail.com");
        user.setPassword("pass");
        user.setRole(Role.SUPPLIER);

        Item item = new Item();
        item.setName("Sample Item");
        item.setCategory("Category");
        item.setCreatedDate(new Date());
        item.setSupplier(supplier);
        return itemRepository.save(item);
    }

    @Test
    @Transactional
    void testAddShipment() {
        User supplier = new User();
        supplier.setName("Test Supplier");
        supplier.setEmail("supplier_" + System.currentTimeMillis() + "@example.com");
        supplier.setPassword("password");
        supplier.setRole(Role.ADMIN);
        supplier = userRepository.save(supplier);

        sessionManager.login(supplier);
        Item item = prepareItemWithSupplier();

        ShipmentDTO dto = new ShipmentDTO();
        dto.setItemId(item.getId());
        dto.setFromLocation("A");
        dto.setToLocation("B");
        dto.setDate(new Date());
        dto.setCurrentStatus(CurrentStatus.CREATED);

        ResponseEntity<ShipmentResponseDTO> response = shipmentController.addShipment(dto);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("B", response.getBody().getToLocation());
    }

    @Test
    @Transactional
    void testGetAllShipments() {
        User supplier = new User();
        supplier.setName("Test Supplier");
        supplier.setEmail("supplier_" + System.currentTimeMillis() + "@example.com");
        supplier.setPassword("password");
        supplier.setRole(Role.ADMIN);
        supplier = userRepository.save(supplier);
        sessionManager.login(supplier);
        Item item = prepareItemWithSupplier();

        Shipment shipment = new Shipment();
        shipment.setItem(item);
        shipment.setFromLocation("X");
        shipment.setToLocation("Y");
        shipment.setExpectedDelivery(new Date());
        shipment.setCurrentStatus(CurrentStatus.CREATED);
        shipmentRepository.save(shipment);

        ResponseEntity<List<ShipmentResponseDTO>> response = shipmentController.getAllShipments();

        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    @Transactional
    void testGetShipmentById() {
        User supplier = new User();
        supplier.setName("Test Supplier");
        supplier.setEmail("supplier_" + System.currentTimeMillis() + "@example.com");
        supplier.setPassword("password");
        supplier.setRole(Role.ADMIN);
        supplier = userRepository.save(supplier);

        sessionManager.login(supplier);
        Item item = prepareItemWithSupplier();

        Shipment shipment = new Shipment();
        shipment.setItem(item);
        shipment.setFromLocation("M");
        shipment.setToLocation("N");
        shipment.setExpectedDelivery(new Date());
        shipment.setCurrentStatus(CurrentStatus.CREATED);
        shipment = shipmentRepository.save(shipment);

        ResponseEntity<ShipmentResponseDTO> response = shipmentController.getShipmentById(shipment.getId());

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("M", response.getBody().getFromLocation());
    }

    @Test
    @Transactional
    void testAssignTransporter() {
        User supplier = new User();
        supplier.setName("Test Supplier");
        supplier.setEmail("supplier_" + System.currentTimeMillis() + "@example.com");
        supplier.setPassword("password");
        supplier.setRole(Role.TRANSPORTER);
        supplier = userRepository.save(supplier);

        sessionManager.login(supplier);
        User transporter = new User();
        transporter.setName("Transporter");
        transporter.setEmail("trans_" + System.currentTimeMillis() + "@mail.com");
        transporter.setPassword("trans123");
        transporter.setRole(Role.TRANSPORTER);
        transporter = userRepository.save(transporter);

        Item item = prepareItemWithSupplier();

        Shipment shipment = new Shipment();
        shipment.setItem(item);
        shipment.setFromLocation("Origin");
        shipment.setToLocation("Destination");
        shipment.setExpectedDelivery(new Date());
        shipment.setCurrentStatus(CurrentStatus.CREATED);
        shipment = shipmentRepository.save(shipment);

        ResponseEntity<ShipmentResponseDTO> response = shipmentController.assignTransporter(shipment.getId(), transporter.getId());

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    @Transactional
    void testUpdateStatus() {
        User supplier = new User();
        supplier.setName("Test Supplier");
        supplier.setEmail("supplier_" + System.currentTimeMillis() + "@example.com");
        supplier.setPassword("password");
        supplier.setRole(Role.ADMIN);
        supplier = userRepository.save(supplier);
        sessionManager.login(supplier);
        Item item = prepareItemWithSupplier();

        Shipment shipment = new Shipment();
        shipment.setItem(item);
        shipment.setFromLocation("From");
        shipment.setToLocation("To");
        shipment.setExpectedDelivery(new Date());
        shipment.setCurrentStatus(CurrentStatus.CREATED);
        shipment = shipmentRepository.save(shipment);

        ResponseEntity<ShipmentResponseDTO> response = shipmentController.updateStatus(shipment.getId(), "IN_TRANSIT");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("IN_TRANSIT", response.getBody().getCurrentStatus().name());
    }
}
