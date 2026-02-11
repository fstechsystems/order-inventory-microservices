package com.company.supply.inventory.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.company.supply.inventory.entity.InventoryBatch;
import com.company.supply.inventory.repository.InventoryRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class InventoryControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private InventoryRepository repository;

        @Test
        void shouldReturnInventorySortedByExpiry() throws Exception {

                mockMvc.perform(get("/inventory/1002"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$").isArray())
                                .andExpect(jsonPath("$[0].productId").value(1002));
        }

        @Test
        void shouldReserveInventoryAndReturnBatchIds() throws Exception {

                String request = """
                                {
                                "productId": 1002,
                                "quantity": 3
                                }
                                """;

                mockMvc.perform(post("/inventory/update")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.productName").value("Smartphone"))
                                .andExpect(jsonPath("$.reservedBatchIds").isArray())
                                .andExpect(jsonPath("$.message").value("Inventory reserved."));
        }

        @Test
        void shouldReduceQuantityInDatabase() throws Exception {

                int before = repository.findByProductIdOrderByExpiryDateAsc(1002L)
                                .stream()
                                .mapToInt(InventoryBatch::getQuantity)
                                .sum();

                String request = """
                                {
                                "productId": 1002,
                                "quantity": 5
                                }
                                """;

                mockMvc.perform(post("/inventory/update")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request))
                                .andExpect(status().isOk());

                int after = repository.findByProductIdOrderByExpiryDateAsc(1002L)
                                .stream()
                                .mapToInt(InventoryBatch::getQuantity)
                                .sum();

                assertEquals(before - 5, after);
        }
}