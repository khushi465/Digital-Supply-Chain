package com.supplytracker.Service;

import com.supplytracker.DTO.ItemResponseDTO;
import com.supplytracker.DTO.ShipmentResponseDTO;
import com.supplytracker.Entity.Item;
import com.supplytracker.Enums.Role;
import com.supplytracker.Repository.ItemRepository;
import com.supplytracker.Repository.ShipmentRepository;
import com.supplytracker.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supplytracker.Entity.Shipment;
import com.supplytracker.Entity.User;
import com.supplytracker.Enums.CurrentStatus;
import com.supplytracker.DTO.ShipmentDTO;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ShipmentService {
    @Autowired
    private ShipmentRepository shipmentRepo;
    @Autowired
    private ItemRepository itemRepo;
    @Autowired
    private UserRepository userRepository;
    public List<ShipmentResponseDTO> getAllShipmentsFiltered(){
//        filtering
        List<Shipment> shipments= shipmentRepo.findAll();
        return shipments.stream()
                .map(ShipmentResponseDTO::new)
                .collect(Collectors.toList());
    }
    public ShipmentResponseDTO getShipmentById(long id){

        Shipment shipment = shipmentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Shipment not found with id: " + id));
        return new ShipmentResponseDTO(shipment);
    }
    public ShipmentResponseDTO createShipment(ShipmentDTO dto){
        Item item = itemRepo.findById(dto.getItemId())
                .orElseThrow(() -> new RuntimeException("Item with ID " + dto.getItemId() + " not found"));
        Shipment shipment=new Shipment(dto.getToLocation(), dto.getFromLocation(),dto.getDate(), dto.getCurrentStatus(), item);
        shipment= shipmentRepo.save(shipment);
        return new ShipmentResponseDTO(shipment);
    }
    public ShipmentResponseDTO assignTransporter(long id, long transporterId){
        Shipment shipment = shipmentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Shipment not found with id: " + id));
        User assignedTransporter = userRepository.findById(transporterId)
                .orElseThrow(() -> new RuntimeException("Transporter not found"));
        shipment.setAssignedTransporter(assignedTransporter);
        shipment= shipmentRepo.save(shipment);
        return new ShipmentResponseDTO(shipment);
    }

    public ShipmentResponseDTO updateStatus(long id, String currentStatus){
        Shipment shipment = shipmentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Shipment not found with id: " + id));
        CurrentStatus currentStatus1=CurrentStatus.valueOf(currentStatus.toUpperCase());
        shipment.setCurrentStatus(currentStatus1);
        shipment= shipmentRepo.save(shipment);
        return new ShipmentResponseDTO(shipment);
    }
}
