package com.supplytracker.Service;
import com.supplytracker.DTO.CheckpointDTO;
import com.supplytracker.DTO.CheckpointResponseDTO;
import com.supplytracker.Entity.Shipment;
import com.supplytracker.Entity.ShipmentCheckpoint;
import com.supplytracker.Exception.ResourceNotFoundException;
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

    public List<CheckpointResponseDTO> getlogHistory(String shipmentId) {
        if (shipmentId == null || shipmentId.trim().isEmpty()) {
            throw new IllegalArgumentException("Shipment ID must not be empty");
        }

        List<ShipmentCheckpoint> logs = checkpointRepository.findByShipmentId(Long.parseLong(shipmentId));
        if (logs.isEmpty()) {
            throw new ResourceNotFoundException("No checkpoint history found for shipment ID: " + shipmentId);
        }
        return logs.stream()
                .map(CheckpointResponseDTO::new)
                .collect(Collectors.toList());
    }
}