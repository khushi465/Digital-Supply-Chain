package com.supplytracker.Controller;


import com.supplytracker.DTO.CheckpointDTO;
import com.supplytracker.DTO.CheckpointDTO;
import com.supplytracker.Entity.ShipmentCheckpoint;
import com.supplytracker.Service.CheckpointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/checkpoints")
public class CheckpointController {
    @Autowired
    private final CheckpointService checkpointService;

    public CheckpointController(CheckpointService checkpointService) {
        this.checkpointService = checkpointService;
    }

    @PostMapping
    public ResponseEntity<ShipmentCheckpoint> logCheckpoint(@RequestBody CheckpointDTO request){
        return ResponseEntity.ok(checkpointService.logCheckpoint(request.getShipmentId(), request.getStatus()));

    }

    @GetMapping("/shipment/{shipmentId}")
    public ResponseEntity<List<ShipmentCheckpoint>> getlogHistory(@PathVariable String shipmentId){
        return ResponseEntity.ok(checkpointService.getlogHistory(shipmentId));
    }


}
