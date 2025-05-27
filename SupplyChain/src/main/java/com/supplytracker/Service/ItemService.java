package com.supplytracker.Service;

import java.util.List;

import com.supplytracker.DTO.ItemDTO;
import com.supplytracker.Entity.Item;
import com.supplytracker.Repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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