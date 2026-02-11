package com.company.sales.order.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.company.sales.order.client.InventoryClient;
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

        private final InventoryClient inventoryClient;

        @Transactional
        public OrderResponse placeOrder(Order order) {

                log.info("Calling inventory service");

                InventoryReservationResponse inventoryReservationResponse = inventoryClient
                                .reserve(order.getProductId(), order.getQuantity());

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