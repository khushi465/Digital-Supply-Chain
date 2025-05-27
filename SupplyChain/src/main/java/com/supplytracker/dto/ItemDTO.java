package com.supplytracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ItemDTO {

    @NotBlank(message="Name is required")
    public String name;
    @NotBlank(message="Category is required")
    public String category;
    @Positive(message="Supplier id must be positive")
    public long supplierId;
    // @NotBlank(message="Created date is required")
    // public Date createdDate;
}