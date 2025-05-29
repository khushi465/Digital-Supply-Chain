package com.supplytracker.entity.ControllerTests;

import com.supplytracker.Controller.AuthController;
import com.supplytracker.DTO.LoginRequestDTO;
import com.supplytracker.DTO.RegisterRequestDTO;
import com.supplytracker.Entity.User;
import com.supplytracker.Repository.UserRepository;
import com.supplytracker.Service.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthControllerTest {

    @Autowired
    private AuthController authController;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    @Transactional
    void testRegister() {
        RegisterRequestDTO dto = new RegisterRequestDTO();
        dto.setName("Test User");
        dto.setEmail("test_" + System.currentTimeMillis() + "@example.com");
        dto.setPassword("password123");

        ResponseEntity<User> response = authController.register(dto);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(dto.getEmail(), response.getBody().getEmail());
        assertEquals(null, response.getBody().getRole());
    }

    @Test
    @Transactional
    void testLogin_Success() {
        RegisterRequestDTO dto = new RegisterRequestDTO();
        dto.setName("Login User");
        dto.setEmail("login_" + System.currentTimeMillis() + "@example.com");
        dto.setPassword("securepass");
        authController.register(dto);

        LoginRequestDTO login = new LoginRequestDTO();
        login.setEmail(dto.getEmail());
        login.setPassword("securepass");
        ResponseEntity<?> response = authController.login(login);
        assertEquals(200, response.getStatusCode().value());
        String body = (String) response.getBody();
        assertTrue(body!= null && body.contains(dto.getEmail()));
        assertTrue(body.contains("Login successful"));
    }

    @Test
    void testLogin_Failure() {
        LoginRequestDTO login = new LoginRequestDTO();
        login.setEmail("nonexistent@example.com");
        login.setPassword("wrongpass");

        ResponseEntity<?> response = authController.login(login);

        assertEquals(401, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().toLowerCase().contains("invalid"));
    }
}
