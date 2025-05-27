package com.supplytracker.Service;


import com.supplytracker.DTO.RegisterRequestDTO;
import com.supplytracker.DTO.UserDTO;
import com.supplytracker.Entity.User;

import java.util.List;

public interface UserService {
    User registerUser(RegisterRequestDTO requestDTO);
    List<UserDTO> getAllUsers();
    UserDTO updateUserRole(Long userId, String role);
    User login(String email, String password);
}