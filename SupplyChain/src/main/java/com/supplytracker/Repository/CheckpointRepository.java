package com.supplytracker.Repository;

import com.supplytracker.Entity.ShipmentCheckpoint;
import com.supplytracker.Enums.CurrentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CheckpointRepository extends JpaRepository<ShipmentCheckpoint, Long> {
    List<ShipmentCheckpoint> findByShipmentId(long shipmentId);
    long count();
    long countByStatus(String status);
}
