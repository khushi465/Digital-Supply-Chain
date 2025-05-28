package com.supplytracker.entity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TransporterUpdateStatus {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testUpdateShipmentStatus() throws Exception {
        mockMvc.perform(put("/api/shipments/1/status")
                        .param("currentStatus", "IN_TRANSIT"))  // <-- send as query param
                .andExpect(status().isOk());
    }
}
