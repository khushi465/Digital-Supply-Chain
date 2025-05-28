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
    @JoinColumn(name = "shipment_id")
    private Shipment shipment;
    private String status;
    private LocalDateTime timestamp;

    public ShipmentCheckpoint(Shipment shipment, String status) {
        this.shipment=shipment;
        this.status=status;
        this.timestamp = LocalDateTime.now();
    }
    public ShipmentCheckpoint(){}

    public String getStatus() {
        return status;
    }

    public long getId() {
        return id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Shipment getShipment() {
        return shipment;
    }

}
