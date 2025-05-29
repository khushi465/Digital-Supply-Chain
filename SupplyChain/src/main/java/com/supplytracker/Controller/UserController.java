package com.supplytracker.Controller;


import com.supplytracker.DTO.UserDTO;
import com.supplytracker.Enums.Role;
import com.supplytracker.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private SessionManager sessionManager;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        sessionManager.checkValidUser(Role.ADMIN);
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("/{id}/role")
    public ResponseEntity<UserDTO> updateRole(@PathVariable Long id, @RequestParam String role, @RequestParam long replacementId) {
        sessionManager.checkValidUser(Role.ADMIN);
        return ResponseEntity.ok(userService.updateUserRole(id, role,replacementId));
    }
}