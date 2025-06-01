package com.supplytracker.DTO;

import com.supplytracker.Entity.ShipmentCheckpoint;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CheckpointResponseDTO {
    private long id;
    private long shipmentId;
    private String status;
    private LocalDateTime timestamp;

    public CheckpointResponseDTO(ShipmentCheckpoint checkpoint) {
        this.id = checkpoint.getId();
        this.shipmentId = checkpoint.getShipment().getId();
        this.status = checkpoint.getStatus();
        this.timestamp = checkpoint.getTimestamp();
    }
}