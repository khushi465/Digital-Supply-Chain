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

//    public void checkValidUser(Role... roles) {
//        if (!sessionManager.isLoggedIn()) {
//            throw new RuntimeException("Not logged in ");
//        }
//        Role currentRole = sessionManager.getCurrentUser().getRole();
//        for (Role role1 : roles) {
//            if (currentRole == role1) {
//                return;
////                returns control back to the calling function
//            }
//        }
//        throw new RuntimeException("Access denied to role "+currentRole);

//    }

    @PostMapping
    public ResponseEntity<CheckpointResponseDTO> logCheckpoint(@Valid @RequestBody CheckpointDTO request){
//       if(sessionManager.isLoggedIn()){
//           if(sessionManager.getCurrentUser().getRole()== Role.ADMIN){
//               return ResponseEntity.ok(checkpointService.logCheckpoint(request));
//           }
//           else{
//               throw new RuntimeException("Access Denied")
//           }
//       }
//       else{
//           throw new RuntimeException("Not logged in");
//       }
        sessionManager.checkValidUser(Role.ADMIN,Role.MANAGER);
        return ResponseEntity.ok(checkpointService.logCheckpoint(request));


    }

    @GetMapping("/shipment/{shipmentId}")
    public ResponseEntity<List<CheckpointResponseDTO>> getlogHistory(@PathVariable String shipmentId){
        sessionManager.checkValidUser(Role.ADMIN,Role.MANAGER);
        return ResponseEntity.ok(checkpointService.getlogHistory(shipmentId));
    }


}
