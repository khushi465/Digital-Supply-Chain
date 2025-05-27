package com.supplytracker.Service;

import com.supplytracker.Entity.Item;
import com.supplytracker.Repository.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supplytracker.Entity.Shipment;
import com.supplytracker.Entity.User;
import com.supplytracker.Enums.CurrentStatus;
import com.supplytracker.DTO.ShipmentDTO;
import java.util.*;

@Service
public class ShipmentService {
    @Autowired
    private ShipmentRepository shipmentRepo;
    public List<Shipment> getAllShipmentsFiltered(){
//        filtering
        return shipmentRepo.findAll();
    }
    public Shipment getShipmentById(long id){
        return shipmentRepo.findById(id).orElse(null);
    }
    public Shipment createShipment(ShipmentDTO dto){
        Shipment shipment=new Shipment(dto.getToLocation(), dto.getFromLocation(),dto.getDate(), dto.getCurrentStatus(), dto.getItem());
        return shipmentRepo.save(shipment);
    }
    public Shipment assignTransporter(long id, User assignedTransporter){
        Shipment shipment=shipmentRepo.findById(id).orElse(null);
        shipment.setAssignedTransporter(assignedTransporter);
        return shipmentRepo.save(shipment);
    }

    public Shipment updateStatus(long id, CurrentStatus currentStatus){
        Shipment shipment=shipmentRepo.findById(id).orElse(null);
        shipment.setCurrentStatus(currentStatus);
        return shipmentRepo.save(shipment);
    }
}
