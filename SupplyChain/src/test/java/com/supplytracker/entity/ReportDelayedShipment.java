package com.supplytracker.entity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ReportDelayedShipment {
    @Autowired
    private MockMvc mockMvc;
    @Test
    void testGetDelayedShipmentsReport() throws Exception {
        mockMvc.perform(get("/api/reports/delayed-shipments"))
                .andExpect(status().isOk());
    }
}
