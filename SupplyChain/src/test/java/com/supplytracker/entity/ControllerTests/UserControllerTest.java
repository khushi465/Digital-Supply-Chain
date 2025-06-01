package com.supplytracker.entity.ControllerTests;

import com.supplytracker.Controller.SessionManager;
import com.supplytracker.Controller.UserController;
import com.supplytracker.DTO.UserDTO;
import com.supplytracker.Entity.User;
import com.supplytracker.Enums.Role;
import com.supplytracker.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserControllerTests {

    @Autowired
    private UserController userController;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    SessionManager sessionManager;

    @Test
    @Transactional
    void testGetAllUsers() {
        User supplier = new User();
        supplier.setName("Test Supplier");
        supplier.setEmail("supplier_" + System.currentTimeMillis() + "@example.com");
        supplier.setPassword("password");
        supplier.setRole(Role.ADMIN);
        supplier = userRepository.save(supplier);

        sessionManager.login(supplier);
        User user = new User();
        user.setName("Test User");
        user.setEmail("user_" + System.currentTimeMillis() + "@example.com");
        user.setPassword("password");
        user.setRole(Role.SUPPLIER);
        userRepository.save(user);

        ResponseEntity<List<UserDTO>> response = userController.getAllUsers();

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().stream().anyMatch(u -> "Test User".equals(u.getName())));
    }

    @Test
    @Transactional
    void testUpdateRole() {
        User supplier = new User();
        supplier.setName("Test Supplier");
        supplier.setEmail("supplier_" + System.currentTimeMillis() + "@example.com");
        supplier.setPassword("password");
        supplier.setRole(Role.ADMIN);
        supplier = userRepository.save(supplier);

        sessionManager.login(supplier);
        User user = new User();
        user.setName("Role Change User");
        user.setEmail("roleuser_" + System.currentTimeMillis() + "@example.com");
        user.setPassword("pass");
        user.setRole(Role.SUPPLIER);
        user = userRepository.save(user);

        ResponseEntity<UserDTO> response = userController.updateRole(user.getId(), "ADMIN",1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("ADMIN", response.getBody().getRole().name());
    }
}
