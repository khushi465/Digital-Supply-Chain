package com.supplytracker.DTO;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import com.supplytracker.Entity.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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

    // @NotBlank(message="Created date is required")
    // public Date createdDate;
}