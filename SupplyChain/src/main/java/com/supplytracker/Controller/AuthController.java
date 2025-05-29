package com.supplytracker.Controller;


import com.supplytracker.DTO.LoginRequestDTO;
import com.supplytracker.DTO.RegisterRequestDTO;
import com.supplytracker.Entity.User;
import com.supplytracker.Service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "User Auth")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private SessionManager sessionManager;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequestDTO requestDTO) {
        return ResponseEntity.ok(userService.registerUser(requestDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginDTO) {
        try {
            User user = userService.login(loginDTO.getEmail(), loginDTO.getPassword());
            sessionManager.login(user);
            return ResponseEntity.ok("Login successful for: " + user.getEmail() + " [" + user.getRole() + "]");
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @GetMapping("/current-user")
    public ResponseEntity<?> currentUser() {
        if (sessionManager.isLoggedIn()) {
            return ResponseEntity.ok(sessionManager.getCurrentUser());
        } else {
            return ResponseEntity.status(401).body("No user is currently logged in.");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        if (sessionManager.isLoggedIn()) {
            sessionManager.login(null);
            return ResponseEntity.ok("User has been logged out.");
        } else {
            return ResponseEntity.status(401).body("No user is currently logged in.");
        }
    }
}