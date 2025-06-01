package com.supplytracker.Service;

import java.util.List;
import java.util.stream.Collectors;

import com.supplytracker.DTO.ItemDTO;
import com.supplytracker.DTO.ItemResponseDTO;
import com.supplytracker.Entity.Item;
import com.supplytracker.Entity.User;
import com.supplytracker.Enums.Role;
import com.supplytracker.Exception.ResourceNotFoundException;
import com.supplytracker.Repository.ItemRepository;
import com.supplytracker.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ItemService{
    @Autowired
    public ItemRepository itemRepo;
    @Autowired
    public UserRepository userRepository;
    public List<ItemResponseDTO> getAllItems(){

        List<Item> items= itemRepo.findAll();
        return items.stream()
                .map(ItemResponseDTO::new)
                .collect(Collectors.toList());
    }

    public ItemResponseDTO getItemById(long id) {
        Item item = itemRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with id: " + id));

        return new ItemResponseDTO(item);
    }
    public ItemResponseDTO createItem(ItemDTO dto) {
        User supplier = userRepository.findById(dto.getSupplierId())
                .orElseThrow(() -> new ResourceNotFoundException("Supplier with ID " + dto.getSupplierId() + " not found"));
        if(supplier.getRole()!= Role.SUPPLIER){
            throw new IllegalArgumentException("Invalid role");
        }
        Item item = new Item(dto.getName(), dto.getCategory(), supplier);
        item = itemRepo.save(item);
        return new ItemResponseDTO(item);
    }
}