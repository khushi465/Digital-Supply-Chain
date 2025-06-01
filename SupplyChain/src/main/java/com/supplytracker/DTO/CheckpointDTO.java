package com.supplytracker.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CheckpointDTO {
    @NotNull(message = "shipmentId cannot be null")
    @Column(unique = true, nullable = false)
    private long shipmentId;
    @NotEmpty(message = "status cannot be null")
    @Column(nullable = false)
    private String status;

    public long getShipmentId() {
        return shipmentId;
    }

    public String getStatus() {
        return status;
    }
}
