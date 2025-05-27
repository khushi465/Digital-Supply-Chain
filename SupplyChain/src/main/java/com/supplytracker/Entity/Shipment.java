package com.example.DigitalSupplyChainTracker.Entity;

import com.example.DigitalSupplyChainTracker.Enums.CurrentStatus;
import com.example.DigitalSupplyChainTracker.Enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shipment")

public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String toLocation,fromLocation;
    Date expectedDelivery;
    Role assignedTransporter=Role.TRANSPORTER;

    @Enumerated(EnumType.STRING)
    private CurrentStatus currentStatus;
}

