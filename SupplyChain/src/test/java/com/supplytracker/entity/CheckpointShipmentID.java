package com.supplytracker.entity;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CheckpointShipmentID {
    private MockMvc mockMvc;
    @Test
    void testGetCheckpointsByShipmentId() throws Exception {
        mockMvc.perform(get("/api/checkpoints/shipment/1"))
                .andExpect(status().isOk());
    }
}
