package com.supplytracker.DTO;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import com.supplytracker.Entity.User;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ItemDTO {

    @NotBlank(message="Name is required")
    public String name;
    @NotBlank(message="Category is required")
    public String category;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", nullable = false)
    public User supplier;
    // @NotBlank(message="Created date is required")
    // public Date createdDate;
}