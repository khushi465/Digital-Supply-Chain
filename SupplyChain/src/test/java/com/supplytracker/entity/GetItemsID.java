package com.supplytracker.entity;

import com.supplytracker.Entity.Item;
import com.supplytracker.Entity.User;
import com.supplytracker.Enums.Role;
import com.supplytracker.Repository.ItemRepository;
import com.supplytracker.Repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class GetItemsID {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    private Long itemId;

    @BeforeEach
    void setup() {
        // Create and save a supplier (user)
        User supplier = new User();
        supplier.setName("Supplier 1");
        supplier.setEmail("supplier@example.com");
        supplier.setPassword("password123");
        supplier.setRole(Role.ADMIN);  // adjust role if needed
        userRepository.save(supplier);

        // Create and save an item
        Item item = new Item();
        item.setName("Test Item");
        item.setCategory("Category A");
        Date date = new Date();
        item.setCreatedDate(date);
        item.setSupplier(supplier);
        itemId = itemRepository.save(item).getId();
    }

    @Test
    void testGetItemById() throws Exception {
        mockMvc.perform(get("/api/items/" + itemId))
                .andExpect(status().isOk());
    }
}
