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
public class AdminRegisterTest {
    @Autowired
    private MockMvc mockMvc;
    @Test
    void testRegisterUser() throws Exception {
        String uniqueEmail = "john" + System.currentTimeMillis() + "@example.com";
        String payload = String.format("""
        {
            "name": "John",
            "email": "%s",
            "password": "pass123"
        }
        """, uniqueEmail);

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isOk());
    }

}
