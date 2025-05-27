package com.supplytracker.Controller;


import com.supplytracker.DTO.LoginRequestDTO;
import com.supplytracker.DTO.RegisterRequestDTO;
import com.supplytracker.Entity.User;
import com.supplytracker.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequestDTO requestDTO) {
        return ResponseEntity.ok(userService.registerUser(requestDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginDTO) {
        try {
            User user = userService.login(loginDTO.getEmail(), loginDTO.getPassword());
            return ResponseEntity.ok("Login successful for: " + user.getEmail() + " [" + user.getRole() + "]");
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
}