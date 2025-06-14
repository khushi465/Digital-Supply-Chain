package com.supplytracker.Entity;


import com.supplytracker.Enums.CurrentStatus;
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;
    @ManyToOne
    @JoinColumn(name = "transporter_id")
    User assignedTransporter;

    @Enumerated(EnumType.STRING)
    private CurrentStatus currentStatus;
    public Shipment(String toLocation, String fromLocation, Date expectedDelivery, CurrentStatus currentStatus, Item item){
        this.toLocation=toLocation;
        this.fromLocation=fromLocation;
        this.expectedDelivery=expectedDelivery;
        this.currentStatus=currentStatus;
        this.item=item;

    }

    public String getFromLocation() {
        return fromLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public CurrentStatus getCurrentStatus() {
        return currentStatus;
    }

    public Date getExpectedDelivery() {
        return expectedDelivery;
    }

    public Item getItem() {
        return item;
    }

    public Long getId() {
        return id;
    }

    public User getAssignedTransporter() {
        return assignedTransporter;
    }
}

