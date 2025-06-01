package com.supplytracker.entity.ControllerTests;

import com.supplytracker.Controller.ItemController;
import com.supplytracker.Controller.SessionManager;
import com.supplytracker.DTO.ItemDTO;
import com.supplytracker.DTO.ItemResponseDTO;
import com.supplytracker.Entity.User;
import com.supplytracker.Enums.Role;
import com.supplytracker.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ItemControllerTest {

    @Autowired ItemController itemController;
    @Autowired UserRepository userRepository;
    @Autowired SessionManager sessionManager;
    Long supplierId;

    @BeforeEach
    void setup() {
        if (supplierId == null) {
            User supplier = new User();
            supplier.setName("Supplier");
            supplier.setEmail("supplier_" + System.currentTimeMillis() + "@example.com");
            supplier.setPassword("password");
            supplier.setRole(Role.SUPPLIER);
            supplierId = userRepository.save(supplier).getId();
        }
    }

    @Test
    @Transactional
    void testAddItem() {
        User supplier = new User();
        supplier.setName("Test Supplier");
        supplier.setEmail("supplier_" + System.currentTimeMillis() + "@example.com");
        supplier.setPassword("password");
        supplier.setRole(Role.SUPPLIER);
        supplier = userRepository.save(supplier);

        sessionManager.login(supplier);  // ✅ Simulate login

        ItemDTO dto = new ItemDTO();
        dto.setName("Test Item");
        dto.setCategory("Test Category");
        dto.setSupplierId(supplier.getId());

        ResponseEntity<ItemResponseDTO> response = itemController.addItem(dto);

        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Test Item", response.getBody().getName());
    }


    @Test
    @Transactional
    void testGetAllItems() {
        User supplier = new User();
        supplier.setName("Test Supplier");
        supplier.setEmail("supplier_" + System.currentTimeMillis() + "@example.com");
        supplier.setPassword("password");
        supplier.setRole(Role.SUPPLIER);
        supplier = userRepository.save(supplier);
        sessionManager.login(supplier);
        ItemDTO dto = new ItemDTO();
        dto.setName("All Test Item");
        dto.setCategory("All Category");
        dto.setSupplierId(supplierId);
        itemController.addItem(dto);

        ResponseEntity<List<ItemResponseDTO>> response = itemController.getAll();
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().stream().anyMatch(i -> "All Test Item".equals(i.getName())));
    }

    @Test
    @Transactional
    void testGetItemById_Found() {
        User supplier = new User();
        supplier.setName("Test Supplier");
        supplier.setEmail("supplier_" + System.currentTimeMillis() + "@example.com");
        supplier.setPassword("password");
        supplier.setRole(Role.SUPPLIER);
        supplier = userRepository.save(supplier);

        sessionManager.login(supplier);
        ItemDTO dto = new ItemDTO();
        dto.setName("Find Me");
        dto.setCategory("Find Category");
        dto.setSupplierId(supplierId);

        long id = itemController.addItem(dto).getBody().getId();

        ResponseEntity<ItemResponseDTO> response = itemController.getItem(id);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Find Me", response.getBody().getName());
    }

    @Test
    @Transactional
    void testGetItemById_NotFound() {
        User supplier = new User();
        supplier.setName("Test Supplier");
        supplier.setEmail("supplier_" + System.currentTimeMillis() + "@example.com");
        supplier.setPassword("password");
        supplier.setRole(Role.SUPPLIER);
        supplier = userRepository.save(supplier);

        sessionManager.login(supplier);
        ResponseEntity<ItemResponseDTO> response = itemController.getItem(999999L);
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }
}
