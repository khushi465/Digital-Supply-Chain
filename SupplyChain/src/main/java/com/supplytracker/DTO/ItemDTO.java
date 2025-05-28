package com.supplytracker.DTO;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import com.supplytracker.Entity.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ItemDTO {

    @NotBlank(message="Name is required")
    public String name;
    @NotBlank(message="Category is required")
    public String category;
    @NotNull(message = "Supplier ID is required")
    public Long supplierId;
    // @NotBlank(message="Created date is required")
    // public Date createdDate;
}