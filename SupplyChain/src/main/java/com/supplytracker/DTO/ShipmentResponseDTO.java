package com.supplytracker.DTO;

import com.supplytracker.Entity.Item;
import com.supplytracker.Entity.Shipment;
import com.supplytracker.Entity.User;
import com.supplytracker.Enums.CurrentStatus;
import lombok.Data;

import java.util.Date;

@Data
public class ShipmentResponseDTO {

    private Long id;
    private String toLocation;
    private String fromLocation;
    private Date expectedDelivery;
    private CurrentStatus currentStatus;

    private String itemName;
    private String itemCategory;
    private Long itemId;

    private Long transporterId;
    private String transporterName;

    public ShipmentResponseDTO(Shipment shipment) {
        this.id = shipment.getId();
        this.toLocation = shipment.getToLocation();
        this.fromLocation = shipment.getFromLocation();
        this.expectedDelivery = shipment.getExpectedDelivery();
        this.currentStatus = shipment.getCurrentStatus();

        Item item = shipment.getItem();
        if (item != null) {
            this.itemId = item.getId();
            this.itemName = item.getName();
            this.itemCategory = item.getCategory();
        }

        User transporter = shipment.getAssignedTransporter();
        if (transporter != null) {
            this.transporterId = transporter.getId();
            this.transporterName = transporter.getName();
        }
    }
}
