package com.supplytracker.Controller;


import com.supplytracker.DTO.CheckpointDTO;
import com.supplytracker.DTO.CheckpointResponseDTO;
import com.supplytracker.Entity.ShipmentCheckpoint;
import com.supplytracker.Enums.Role;
import com.supplytracker.Service.CheckpointService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/checkpoints")
public class CheckpointController {
    @Autowired
    private CheckpointService checkpointService;
    @Autowired
    private SessionManager sessionManager;
    @PostMapping
    public ResponseEntity<CheckpointResponseDTO> logCheckpoint(@Valid @RequestBody CheckpointDTO request){
        sessionManager.checkValidUser(Role.ADMIN,Role.MANAGER);
        return ResponseEntity.ok(checkpointService.logCheckpoint(request));


    }

    @GetMapping("/shipment/{shipmentId}")
    public ResponseEntity<List<CheckpointResponseDTO>> getLogHistory(@PathVariable String shipmentId){
        sessionManager.checkValidUser(Role.ADMIN,Role.MANAGER,Role.TRANSPORTER);
        return ResponseEntity.ok(checkpointService.getlogHistory(shipmentId));
    }


}
