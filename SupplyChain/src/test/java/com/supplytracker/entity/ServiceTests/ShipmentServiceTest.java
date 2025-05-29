package com.supplytracker.entity.ServiceTests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.supplytracker.DTO.*;
import com.supplytracker.Entity.Item;
import com.supplytracker.Entity.Shipment;
import com.supplytracker.Entity.User;
import com.supplytracker.Enums.CurrentStatus;
import com.supplytracker.Enums.Role;
import com.supplytracker.Repository.ItemRepository;
import com.supplytracker.Repository.ShipmentRepository;
import com.supplytracker.Repository.UserRepository;
import com.supplytracker.Service.ShipmentService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ShipmentServiceTest {
    @Autowired ShipmentRepository shipmentRepository;
    @Autowired ShipmentService shipmentService;
    @Autowired UserRepository userRepository;
    @Autowired ItemRepository itemRepo;

    @Test
    @Transactional
    void testGetAllShipmentsFiltered() {
        User supplier = new User();
        supplier.setName("Test Supplier");
        supplier.setEmail("supplier_" + System.currentTimeMillis() + "@example.com");
        supplier.setPassword("password");
        supplier.setRole(Role.ADMIN);
        supplier = userRepository.save(supplier);

        Item item = new Item();
        item.setName("Test Item");
        item.setCategory("Test Category");
        item.setSupplier(supplier);
        item = itemRepo.save(item);

        Shipment shipment = new Shipment();
        shipment.setFromLocation("Warehouse A");
        shipment.setToLocation("Retailer B");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 5);
        shipment.setExpectedDelivery(calendar.getTime());
        shipment.setCurrentStatus(CurrentStatus.CREATED);
        shipment.setItem(item);
        shipment = shipmentRepository.save(shipment);

        List<ShipmentResponseDTO> result = shipmentService.getAllShipmentsFiltered();
        Shipment finalShipment = shipment;
        assertTrue(result.stream().anyMatch(dto -> dto.getId().equals(finalShipment.getId())));
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

        Item item = new Item();
        item.setName("Test Item");
        item.setCategory("Test Category");
        item.setSupplier(supplier);
        item = itemRepo.save(item);

        Shipment shipment = new Shipment();
        shipment.setFromLocation("Warehouse A");
        shipment.setToLocation("Retailer B");
        shipment.setExpectedDelivery(new Date());
        shipment.setCurrentStatus(CurrentStatus.CREATED);
        shipment.setItem(item);
        shipment = shipmentRepository.save(shipment);

        ShipmentResponseDTO result = shipmentService.getShipmentById(shipment.getId());
        assertNotNull(result);
        assertEquals(shipment.getId(), result.getId());
    }

    @Test
    @Transactional
    void testCreateShipment() {
        User supplier = new User();
        supplier.setName("Supplier");
        supplier.setEmail("supplier_" + System.currentTimeMillis() + "@example.com");
        supplier.setPassword("pass");
        supplier.setRole(Role.ADMIN);
        supplier = userRepository.save(supplier);

        Item item = new Item();
        item.setName("Test Item");
        item.setCategory("Test Category");
        item.setCreatedDate(new Date());
        item.setSupplier(supplier);
        item = itemRepo.save(item);

        ShipmentDTO dto = new ShipmentDTO();
        dto.setItemId(item.getId());
        dto.setToLocation("A");
        dto.setFromLocation("B");
        dto.setDate(new Date());
        dto.setCurrentStatus(CurrentStatus.CREATED);

        ShipmentResponseDTO response = shipmentService.createShipment(dto);
        assertNotNull(response);
        assertEquals("A", response.getToLocation());
    }

    @Test
    @Transactional
    void testAssignTransporter() {
        User transporter = new User();
        transporter.setName("Transporter");
        transporter.setEmail("transporter_" + System.currentTimeMillis() + "@example.com");
        transporter.setPassword("secret");
        transporter.setRole(Role.TRANSPORTER);
        transporter = userRepository.save(transporter);

        Shipment shipment = new Shipment();
        shipment.setFromLocation("A");
        shipment.setToLocation("B");
        shipment.setCurrentStatus(CurrentStatus.CREATED);
        shipment.setExpectedDelivery(new Date());
        shipment = shipmentRepository.save(shipment);

        ShipmentResponseDTO result = shipmentService.assignTransporter(shipment.getId(), transporter.getId());
        assertNotNull(result);
    }

    @Test
    @Transactional
    void testUpdateStatus() {
        Shipment shipment = new Shipment();
        shipment.setFromLocation("Warehouse A");
        shipment.setToLocation("Retailer B");
        shipment.setExpectedDelivery(new Date());
        shipment.setCurrentStatus(CurrentStatus.CREATED);
        shipment = shipmentRepository.save(shipment);

        ShipmentResponseDTO result = shipmentService.updateStatus(shipment.getId(), "CREATED");
        assertNotNull(result);
    }
}
