package com.supplytracker.Controller;

import com.supplytracker.DTO.ShipmentDTO;
import com.supplytracker.Entity.User;
import com.supplytracker.Entity.Shipment;
import com.supplytracker.Enums.CurrentStatus;
import com.supplytracker.Service.ShipmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shipments")
public class ShipmentController {
    @Autowired
    private ShipmentService shipmentService;
    @PostMapping
    public ResponseEntity<Shipment> addShipment(@Valid @RequestBody ShipmentDTO dto) {
       Shipment shipment= shipmentService.createShipment(dto);
        return new ResponseEntity<>(shipment, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Shipment>> getAllShipments(){
        List<Shipment> shipments=shipmentService.getAllShipmentsFiltered();
        return new ResponseEntity<>(shipments, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Shipment> getShipmentById(@PathVariable long id){
        Shipment shipment=shipmentService.getShipmentById(id);
        if(shipment==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(shipment, HttpStatus.OK);
        // gives a response entity of type Item with the body of the item with given id and status Ok 200
    }


@PutMapping("/{id}/assign")
public ResponseEntity<Shipment> assignTransporter(@PathVariable long id, @RequestBody User assignedTransporter) {
    Shipment shipment = shipmentService.assignTransporter(id, assignedTransporter);
    return new ResponseEntity<>(shipment, HttpStatus.OK);
}

@PutMapping("/{id}/status")
    public ResponseEntity<Shipment> updateStatus(@PathVariable long id, @RequestBody CurrentStatus currentStatus){
        Shipment shipment=shipmentService.updateStatus(id, currentStatus);
        return new ResponseEntity<>(shipment, HttpStatus.OK);
}
//    @PutMapping("/api/shipments/{id}/assign")
//
//    @PutMapping("/api/shipments/{id}/status")

}
