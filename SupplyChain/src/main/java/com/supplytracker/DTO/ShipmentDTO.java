package com.supplytracker.DTO;


import com.supplytracker.Entity.Item;
import com.supplytracker.Enums.CurrentStatus;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class ShipmentDTO {

    @NotNull(message = "Expected Delivery date needs to be provided.")
    private Date date;

    @NotNull(message = "Delivery Location cannot be blank")
    private String toLocation;

    @NotNull(message = "Source Location is required")
    private String fromLocation;

    @NotNull(message = "Current status of the delivery cannot be null.")
    private CurrentStatus currentStatus;

    @ManyToOne
    @JoinColumn(name="item_id",nullable = false)
    private Long itemId;

}
