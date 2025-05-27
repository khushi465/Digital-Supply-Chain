package com.supplytracker.Service.Implementation;


import com.supplytracker.DTO.RegisterRequestDTO;
import com.supplytracker.DTO.UserDTO;
import com.supplytracker.Entity.User;
import com.supplytracker.Enums.Role;
import com.supplytracker.Repository.UserRepository;
import com.supplytracker.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(RegisterRequestDTO requestDTO) {
        User user = new User();
        user.setName(requestDTO.getName());
        user.setEmail(requestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        user.setRole(Role.SUPPLIER);
        return userRepository.save(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map((User user) -> new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getRole()))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO updateUserRole(Long userId, String roleStr) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Role role = Role.valueOf(roleStr.toUpperCase());
        user.setRole(role);
        userRepository.save(user);
        return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getRole());
    }

    @Override
    public User login(String email, String rawPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        return user; // Login successful
    }

}
