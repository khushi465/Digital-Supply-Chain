package com.supplytracker.Controller;
import java.util.List;

import com.supplytracker.DTO.ItemDTO;
import com.supplytracker.DTO.ItemResponseDTO;
import com.supplytracker.Entity.Item;
import com.supplytracker.Enums.Role;
import com.supplytracker.Service.ItemService;
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
        sessionManager.checkValidUser(Role.ADMIN,Role.SUPPLIER);
        ItemResponseDTO item=itemService.getItemById(id);
        return new ResponseEntity<>(item, HttpStatus.OK);

            // returns not found status 404 if item is not found
        }

}
// for validation
// controlleradvice global exceptions
// @ExceptionHandler(MethodArgumentNotValidException.class)
// public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
//     Map<String, String> errors = new HashMap<>();
//     ex.getBindingResult().getFieldErrors().forEach(error ->
//         errors.put(error.getField(), error.getDefaultMessage())
//     );
//     return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
// }
