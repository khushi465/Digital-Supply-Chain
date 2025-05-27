package com.supplytracker.Service;
import com.supplytracker.Entity.ShipmentCheckpoint;
import com.supplytracker.Repository.CheckpointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckpointService {
    @Autowired
    private final CheckpointRepository checkpointRepository;
    public CheckpointService(CheckpointRepository checkpointRepository) {
        this.checkpointRepository = checkpointRepository;
    }

    public ShipmentCheckpoint logCheckpoint(String shipmentId, String status) {
        ShipmentCheckpoint checkpoint = new ShipmentCheckpoint();
        checkpoint.setShipmentId(shipmentId);
        checkpoint.setStatus(status);
        return checkpointRepository.save(checkpoint);
    }

    public List<ShipmentCheckpoint> getlogHistory(String shipmentId) {
        return checkpointRepository.findByShipmentId(shipmentId);
    }

}
