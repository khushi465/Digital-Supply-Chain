package com.supplytracker.Controller;

import com.supplytracker.DTO.ShipmentDTO;
import com.supplytracker.DTO.ShipmentResponseDTO;
import com.supplytracker.Enums.Role;
import com.supplytracker.Service.ShipmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shipments")
@Tag(name = "Shipment")
public class ShipmentController {
    @Autowired
    private ShipmentService shipmentService;
    @Autowired
    private SessionManager sessionManager;
    @PostMapping
    public ResponseEntity<ShipmentResponseDTO> addShipment(@Valid @RequestBody ShipmentDTO dto) {
        sessionManager.checkValidUser(Role.ADMIN,Role.TRANSPORTER);
        ShipmentResponseDTO shipment= shipmentService.createShipment(dto);
        return new ResponseEntity<>(shipment, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ShipmentResponseDTO>> getAllShipments(){
        sessionManager.checkValidUser(Role.ADMIN,Role.TRANSPORTER);
        List<ShipmentResponseDTO> shipments=shipmentService.getAllShipmentsFiltered();
        return new ResponseEntity<>(shipments, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShipmentResponseDTO> getShipmentById(@PathVariable long id){
        sessionManager.checkValidUser(Role.ADMIN,Role.TRANSPORTER);
        ShipmentResponseDTO shipment=shipmentService.getShipmentById(id);
        if(shipment==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(shipment, HttpStatus.OK);
    }


@PutMapping("/{id}/assign")
public ResponseEntity<ShipmentResponseDTO> assignTransporter(@PathVariable long id, @RequestParam long transporterId) {
    sessionManager.checkValidUser(Role.ADMIN);
    ShipmentResponseDTO shipment = shipmentService.assignTransporter(id, transporterId);
    return new ResponseEntity<>(shipment, HttpStatus.OK);
}

@PutMapping("/{id}/status")
    public ResponseEntity<ShipmentResponseDTO> updateStatus(@PathVariable long id, @RequestParam String currentStatus){
    sessionManager.checkValidUser(Role.ADMIN,Role.TRANSPORTER);
    ShipmentResponseDTO shipment=shipmentService.updateStatus(id, currentStatus);
        return new ResponseEntity<>(shipment, HttpStatus.OK);
}
}
