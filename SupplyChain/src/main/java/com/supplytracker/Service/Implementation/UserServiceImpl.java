package com.supplytracker.Service.Implementation;


import com.supplytracker.DTO.ItemDTO;
import com.supplytracker.DTO.RegisterRequestDTO;
import com.supplytracker.DTO.ShipmentResponseDTO;
import com.supplytracker.DTO.UserDTO;
import com.supplytracker.Entity.Item;
import com.supplytracker.Entity.Shipment;
import com.supplytracker.Entity.User;
import com.supplytracker.Enums.Role;
import com.supplytracker.Exception.InvalidCredentialsException;
import com.supplytracker.Exception.ResourceNotFoundException;
import com.supplytracker.Repository.ItemRepository;
import com.supplytracker.Repository.ShipmentRepository;
import com.supplytracker.Repository.UserRepository;
import com.supplytracker.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemRepository itemRepository ;
    @Autowired
    private ShipmentRepository shipmentRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /*@Override
    public User registerUser(RegisterRequestDTO requestDTO) {
        User user = new User();
        user.setName(requestDTO.getName());
        user.setEmail(requestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        user.setRole(Role.SUPPLIER);
        return userRepository.save(user);
    }*/

    @Override
    public User registerUser(RegisterRequestDTO requestDTO) {
        User user = new User();
        user.setName(requestDTO.getName());
        user.setEmail(requestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));

        // Check if any admin exists
        long adminCount = userRepository.countByRole(Role.ADMIN);

        // Assign ADMIN only to the first user, else set null
        if (adminCount == 0) {
            user.setRole(Role.ADMIN);
        } else {
            user.setRole(null);
        }

        return userRepository.save(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map((User user) -> new UserDTO(user.getId(),user.getName(), user.getEmail(), user.getRole()))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO updateUserRole(Long userId, String roleStr, long replacementId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
        User replacementUser=userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
        try {
            Role role = Role.valueOf(roleStr.toUpperCase());
            user.setRole(role);
            List<Item> items = itemRepository.findBySupplierId(userId);
            for (Item item : items) {
                item.setSupplier(replacementUser); // Set actual entity
            }
            itemRepository.saveAll(items);
//            List<Shipment> shipments = shipmentRepository.findByTransporterId(userId);
//            for (Shipment shipment : shipments) {
//                shipment.setAssignedTransporter(replacementUser); // Set actual entity
//            }
//            shipmentRepository.saveAll(shipments);
//saveall
//            check for role
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role: " + roleStr);
        }

        userRepository.save(user);
        return new UserDTO(user.getId(),user.getName(), user.getEmail(), user.getRole());
    }

    @Override
    public User login(String email, String rawPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        return user; // Login successful
    }

}
