package com.supplytracker.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ItemDTO {

    @NotBlank(message="Name is required")
    public String name;
    @NotBlank(message="Category is required")
    public String category;

    public long supplierId;

    public long getSupplierId() {
        return supplierId;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }
}