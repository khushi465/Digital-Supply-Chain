package com.supplytracker.Repository;

import com.supplytracker.Entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import com.supplytracker.Entity.Shipment;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {

}
