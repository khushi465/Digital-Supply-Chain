package com.supplytracker.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.supplytracker.dto.ItemDTO;
import com.supplytracker.entity.Item;
import com.supplytracker.service.ItemService;

import jakarta.validation.Valid;



@RestController
@RequestMapping("/api/items")
public class ItemController{
    @Autowired
    private ItemService itemService;
    @GetMapping
    public ResponseEntity<List<Item>> getAll() {
        List<Item> items=itemService.getAllItems();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Item> addItem(@Valid @RequestBody ItemDTO dto) {
        Item item=itemService.createItem(dto);
        return new ResponseEntity<>(item,HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Item> getItem(@PathVariable long id) {
        Item item=itemService.getItemById(id);
        if(item==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            // returns not found status 404 if item is not found
        }
        return new ResponseEntity<>(item, HttpStatus.OK);
        // gives a response entity of type Item with the body of the item with given id and status Ok 200
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
