package com.supplytracker.entity.ServiceTests;

import com.supplytracker.DTO.RegisterRequestDTO;
import com.supplytracker.DTO.UserDTO;
import com.supplytracker.Entity.User;
import com.supplytracker.Enums.Role;
import com.supplytracker.Repository.UserRepository;
import com.supplytracker.Service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class UserServiceTests {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void testRegisterUser() {
        RegisterRequestDTO requestDTO = new RegisterRequestDTO();
        requestDTO.setName("John Doe");
        requestDTO.setEmail("john_" + System.currentTimeMillis() + "@example.com");
        requestDTO.setPassword("secret123");

        User savedUser = userService.registerUser(requestDTO);

        assertNotNull(savedUser.getId());
        assertEquals(requestDTO.getName(), savedUser.getName());
        assertEquals(requestDTO.getEmail(), savedUser.getEmail());
        assertTrue(passwordEncoder.matches("secret123", savedUser.getPassword()));
        assertNull(savedUser.getRole());
    }

    @Test
    void testGetAllUsers() {
        User user = new User();
        user.setName("Alice");
        user.setEmail("alice_" + System.currentTimeMillis() + "@example.com");
        user.setPassword(passwordEncoder.encode("pass"));
        user.setRole(Role.ADMIN);
        userRepository.save(user);

        List<UserDTO> users = userService.getAllUsers();

        assertFalse(users.isEmpty());
        assertTrue(users.stream().anyMatch(u -> u.getEmail().equals(user.getEmail())));
    }

    @Test
    void testUpdateUserRole() {
        User user = new User();
        user.setName("Bob");
        user.setEmail("bob_" + System.currentTimeMillis() + "@example.com");
        user.setPassword(passwordEncoder.encode("pass"));
        user.setRole(Role.SUPPLIER);
        user = userRepository.save(user);

        UserDTO updated = userService.updateUserRole(user.getId(), "ADMIN",1);

        assertEquals(Role.ADMIN, updated.getRole());
        assertEquals(user.getEmail(), updated.getEmail());
    }

    @Test
    void testLoginSuccess() {
        String rawPassword = "securepass";
        User user = new User();
        user.setName("Charlie");
        user.setEmail("charlie_" + System.currentTimeMillis() + "@example.com");
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRole(Role.ADMIN);
        userRepository.save(user);

        User result = userService.login(user.getEmail(), rawPassword);

        assertNotNull(result);
        assertEquals(user.getEmail(), result.getEmail());
    }

    @Test
    void testLoginFailure_InvalidPassword() {
        String rawPassword = "rightpassword";
        User user = new User();
        user.setName("David");
        user.setEmail("david_" + System.currentTimeMillis() + "@example.com");
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRole(Role.SUPPLIER);
        userRepository.save(user);

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                userService.login(user.getEmail(), "wrongpassword"));

        assertEquals("Invalid email or password", ex.getMessage());
    }

    @Test
    void testLoginFailure_InvalidEmail() {
        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                userService.login("nonexistent@example.com", "any"));

        assertEquals("Invalid email or password", ex.getMessage());
    }
}
