package com.example.DigitalSupplyChainTracker.Service;

import com.example.DigitalSupplyChainTracker.DTO.RegisterRequestDTO;
import com.example.DigitalSupplyChainTracker.DTO.UserDTO;
import com.example.DigitalSupplyChainTracker.Entity.User;

import java.util.List;

public interface UserService {
    User registerUser(RegisterRequestDTO requestDTO);
    List<UserDTO> getAllUsers();
    UserDTO updateUserRole(Long userId, String role);
    User login(String email, String password);
}