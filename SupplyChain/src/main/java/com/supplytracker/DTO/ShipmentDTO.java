package com.supplytracker.DTO;

import com.supplytracker.Enums.CurrentStatus;
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

    @NotNull(message = "Item ID is required")
    private Long itemId;

    public String getFromLocation() {
        return fromLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public Long getItemId() {
        return itemId;
    }

    public CurrentStatus getCurrentStatus() {
        return currentStatus;
    }

    public Date getDate() {
        return date;
    }
}
