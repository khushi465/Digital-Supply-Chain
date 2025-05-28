package com.supplytracker.entity;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PostShipments {
    private MockMvc mockMvc;
    @Test
    void testCreateShipment() throws Exception {
        String payload = "{\"itemId\":1,\"fromLocation\":\"Factory A\",\"toLocation\":\"Retailer B\",\"expectedDelivery\":\"2025-06-05\"}";
        mockMvc.perform(post("/api/shipments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isOk());
    }
}
