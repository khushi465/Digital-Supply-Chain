package com.supplytracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supplytracker.dto.ItemDTO;
import com.supplytracker.entity.Item;
import com.supplytracker.repository.ItemRepository;

@Service
public class ItemService{
    @Autowired
    public ItemRepository itemRepo;
    public List<Item> getAllItems(){
        return itemRepo.findAll();
    }
    public Item getItemById(long id){
        return itemRepo.findById(id).orElse(null);
    }
    public Item createItem(ItemDTO dto){
        Item item=new Item(dto.getName(), dto.getCategory(), dto.getSupplierId());
        return itemRepo.save(item);
    }
}