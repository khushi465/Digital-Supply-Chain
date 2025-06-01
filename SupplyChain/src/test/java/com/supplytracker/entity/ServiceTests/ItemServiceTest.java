package com.supplytracker.entity.ServiceTests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.List;

import com.supplytracker.DTO.*;
import com.supplytracker.Entity.Item;
import com.supplytracker.Entity.User;
import com.supplytracker.Enums.Role;
import com.supplytracker.Repository.CheckpointRepository;
import com.supplytracker.Repository.ItemRepository;
import com.supplytracker.Repository.ShipmentRepository;
import com.supplytracker.Repository.UserRepository;
import com.supplytracker.Service.CheckpointService;
import com.supplytracker.Service.ItemService;
import com.supplytracker.Service.ShipmentService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ItemServiceTest {
    @Autowired UserRepository userRepository;
    @Autowired ItemRepository itemRepo;
    @Autowired ItemService itemService;

    @Test
    @Transactional
    void testGetAllItems() {
        long timestamp = System.currentTimeMillis();
        User supplier = new User();
        supplier.setName("Test Supplier");
        supplier.setEmail("supplier+" + timestamp + "@example.com");
        supplier.setPassword("password");
        supplier.setRole(Role.ADMIN);
        supplier = userRepository.save(supplier);

        String uniqueItemName = "Test Item " + timestamp;
        Item item = new Item();
        item.setName(uniqueItemName);
        item.setCategory("Test Category");
        item.setCreatedDate(new Date());
        item.setSupplier(supplier);
        itemRepo.save(item);

        List<ItemResponseDTO> items = itemService.getAllItems();
        long matchingItems = items.stream()
                .filter(i -> uniqueItemName.equals(i.getName()) && "Test Category".equals(i.getCategory()))
                .count();
        assertEquals(1, matchingItems);
    }

    @Test
    @Transactional
    void testGetItemById() {
        User supplier = new User();
        supplier.setName("Supplier X");
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

        ItemResponseDTO result = itemService.getItemById(item.getId());
        assertNotNull(result);
        assertEquals("Test Item", result.getName());
        assertEquals("Test Category", result.getCategory());
    }
    @Test
    @Transactional
    void testCreateItem() {
        User supplier = new User();
        supplier.setName("Supplier X");
        supplier.setEmail("supplier_" + System.currentTimeMillis() + "@example.com");
        supplier.setPassword("password");
        supplier.setRole(Role.SUPPLIER);
        supplier = userRepository.save(supplier);

        ItemDTO dto = new ItemDTO();
        dto.setName("Test Item");
        dto.setCategory("Test Category");
        dto.setSupplierId(supplier.getId());

        ItemResponseDTO result = itemService.createItem(dto);
        assertNotNull(result);
        assertEquals("Test Item", result.getName());
        assertEquals("Test Category", result.getCategory());
        assertEquals(supplier.getId(), result.getSupplierId());
    }
}
