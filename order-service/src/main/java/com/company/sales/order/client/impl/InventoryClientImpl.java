package com.company.sales.order.client.impl;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.company.sales.order.client.InventoryClient;
import com.company.sales.order.dto.InventoryReservationResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InventoryClientImpl implements InventoryClient {

    private final WebClient webClient;

    @Override
    public InventoryReservationResponse reserve(Long productId, int quantity) {
        return webClient.post()
                .uri("/inventory/update")
                .bodyValue(Map.of("productId", productId, "quantity", quantity))
                .retrieve()
                .bodyToMono(InventoryReservationResponse.class)
                .block();
    }
}