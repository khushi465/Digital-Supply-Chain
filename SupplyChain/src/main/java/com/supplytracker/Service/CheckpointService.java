package com.supplytracker.Service;
import com.supplytracker.DTO.CheckpointDTO;
import com.supplytracker.DTO.CheckpointResponseDTO;
import com.supplytracker.DTO.ShipmentResponseDTO;
import com.supplytracker.Entity.Shipment;
import com.supplytracker.Entity.ShipmentCheckpoint;
import com.supplytracker.Entity.User;
import com.supplytracker.Repository.CheckpointRepository;
import com.supplytracker.Repository.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CheckpointService {
    @Autowired
    private CheckpointRepository checkpointRepository;
    @Autowired
    private ShipmentRepository shipmentRepository;

    public CheckpointResponseDTO logCheckpoint(CheckpointDTO dto) {
        Shipment shipment = shipmentRepository.findById(dto.getShipmentId())
                .orElseThrow(() -> new RuntimeException("Shipment with ID " + dto.getShipmentId() + " not found"));
        ShipmentCheckpoint checkpoint=new ShipmentCheckpoint(shipment, dto.getStatus());
        checkpoint=checkpointRepository.save(checkpoint);
        return new CheckpointResponseDTO(checkpoint);
    }

    public List<CheckpointResponseDTO> getlogHistory(long shipmentId) {
        List<ShipmentCheckpoint> list = checkpointRepository.findByShipmentId(shipmentId);
        return list.stream()
                .map(CheckpointResponseDTO::new)
                .collect(Collectors.toList());
    }
}

//remove json ignore in entity, add response dto and use in service and controller