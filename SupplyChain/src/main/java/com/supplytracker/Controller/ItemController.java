package com.supplytracker.Controller;
import java.util.List;

import com.supplytracker.DTO.ItemDTO;
import com.supplytracker.DTO.ItemResponseDTO;
import com.supplytracker.Enums.Role;
import com.supplytracker.Exception.ResourceNotFoundException;
import com.supplytracker.Service.ItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import jakarta.validation.Valid;



@RestController
@RequestMapping("/api/items")
@Tag(name = "Item")
public class ItemController{
    @Autowired
    private ItemService itemService;
    @Autowired
    private SessionManager sessionManager;
    @GetMapping
    public ResponseEntity<List<ItemResponseDTO>> getAll() {
        sessionManager.checkValidUser(Role.ADMIN,Role.SUPPLIER);
        List<ItemResponseDTO> items=itemService.getAllItems();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<ItemResponseDTO> addItem(@Valid @RequestBody ItemDTO dto) {
        sessionManager.checkValidUser(Role.ADMIN,Role.SUPPLIER);
        ItemResponseDTO item=itemService.createItem(dto);
        return new ResponseEntity<>(item,HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ItemResponseDTO> getItem(@PathVariable long id) {
        sessionManager.checkValidUser(Role.ADMIN, Role.SUPPLIER);
        try {
            ItemResponseDTO item = itemService.getItemById(id);
            return new ResponseEntity<>(item, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


}
