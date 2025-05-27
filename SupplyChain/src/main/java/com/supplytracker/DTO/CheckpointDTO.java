package com.supplytracker.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CheckpointDTO {
    @NotEmpty(message = "shipmentId cannot be null")
    @Column(unique = true, nullable = false)
    private String shipmentId;
    @NotEmpty(message = "status cannot be null")
    @Column(nullable = false)
    private String status;
}
