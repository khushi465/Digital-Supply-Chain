package com.supplytracker.DTO;

import lombok.Getter;

@Getter
public class DeliveryStatusDTO {
    private final long totalShipments;
    private final long delayedShipments;
    private final long onTimeShipments;
    private final long delivered;

    public DeliveryStatusDTO(long totalShipments, long delayedShipments, long onTimeShipments, long delivered) {
        this.totalShipments = totalShipments;
        this.delayedShipments = delayedShipments;
        this.onTimeShipments = onTimeShipments;
        this.delivered = delivered;
    }

}
