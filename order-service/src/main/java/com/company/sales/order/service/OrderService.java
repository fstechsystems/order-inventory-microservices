package com.company.sales.order.service;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.company.sales.order.dto.InventoryReservationResponse;
import com.company.sales.order.dto.OrderResponse;
import com.company.sales.order.entity.Order;
import com.company.sales.order.repository.OrderRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

        private final OrderRepository orderRepository;
        private final WebClient webClient;

        @Transactional
        public OrderResponse placeOrder(Order order) {

                log.info("Calling inventory service");

                InventoryReservationResponse inventoryReservationResponse = webClient.post()
                                .uri("/inventory/update")
                                .bodyValue(Map.of(
                                                "productId", order.getProductId(),
                                                "quantity", order.getQuantity()))
                                .retrieve()
                                .bodyToMono(InventoryReservationResponse.class)
                                .block();

                order.setProductName(inventoryReservationResponse.getProductName());
                order.setStatus("PLACED");
                order.setOrderDate(LocalDate.now());

                Order saved = orderRepository.save(order);

                return OrderResponse.builder().orderId(saved.getOrderId()).productId(saved.getProductId())
                                .productName(saved.getProductName())
                                .quantity(saved.getQuantity()).status(saved.getStatus())
                                .reservedFromBatchIds(inventoryReservationResponse.getReservedBatchIds())
                                .message("Order placed. " + inventoryReservationResponse.getMessage()).build();
        }
}