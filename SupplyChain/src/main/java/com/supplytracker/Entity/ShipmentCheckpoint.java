package com.supplytracker.Entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class ShipmentCheckpoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipment_id", nullable = false)
    private String shipmentId;
    private String status;
    private LocalDateTime timestamp;


    public ShipmentCheckpoint() {
        this.timestamp = LocalDateTime.now();
    }

}
