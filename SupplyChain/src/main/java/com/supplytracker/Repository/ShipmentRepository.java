package com.supplytracker.Repository;
import com.supplytracker.Entity.Shipment;
import com.supplytracker.Enums.CurrentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    List<Shipment> findByCurrentStatus(CurrentStatus status);
}
