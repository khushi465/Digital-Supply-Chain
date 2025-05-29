
package com.supplytracker.DTO;

import com.supplytracker.Entity.Item;
import lombok.Data;

import java.util.Date;

@Data
public class ItemResponseDTO {
    private long id;
    private String name;
    private String category;
    private String supplierName;
    private long supplierId;
    private Date createdDate;

    public ItemResponseDTO(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.category = item.getCategory();
        this.supplierName = item.getSupplier().getName(); // safely access lazy field
        this.supplierId = item.getSupplier().getId(); // safely access lazy field
        this.createdDate=item.getCreatedDate();
    }
}
