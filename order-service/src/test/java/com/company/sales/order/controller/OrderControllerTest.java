package com.company.sales.order.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.company.sales.order.client.InventoryClient;
import com.company.sales.order.dto.InventoryReservationResponse;
import com.company.sales.order.repository.OrderRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class OrderControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    OrderRepository repository;

    @MockitoBean
    InventoryClient inventoryClient;

    @Test
    void shouldPlaceOrder() throws Exception {

        when(inventoryClient.reserve(1002L, 3)).thenReturn(new InventoryReservationResponse("Smartphone",
        List.of(3L),"Inventory reserved."));

        String request = """
        {
        "productId": 1002,
        "quantity": 3
        }
        """;

        mockMvc.perform(post("/order")
        .contentType(MediaType.APPLICATION_JSON)
        .content(request))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.productId").value(1002))
        .andExpect(jsonPath("$.status").value("PLACED"))
        .andExpect(jsonPath("$.productName").value("Smartphone"))
        .andExpect(jsonPath("$.reservedFromBatchIds[0]").value(3));
    }
}