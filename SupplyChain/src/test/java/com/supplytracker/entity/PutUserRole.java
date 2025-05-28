package com.supplytracker.entity;

import com.supplytracker.Entity.User;
import com.supplytracker.Enums.Role;
import com.supplytracker.Repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PutUserRole {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    private Long testUserId;

    @BeforeEach
    void setup() {
        // Clear any existing users
        userRepository.deleteAll();

        // Create a new user without manually setting the ID
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPassword("test123");
        user.setRole(Role.ADMIN);  // Initial role

        // Save and capture the auto-generated ID
        User savedUser = userRepository.save(user);
        testUserId = savedUser.getId();
    }

    @Test
    void testUpdateUserRole() throws Exception {
        mockMvc.perform(put("/api/users/" + testUserId + "/role")
                        .param("role", "TRANSPORTER"))  // Change role to TRANSPORTER
                .andExpect(status().isOk());
    }
}
