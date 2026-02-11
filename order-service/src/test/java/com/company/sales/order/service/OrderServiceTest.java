package com.company.sales.order.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.company.sales.order.client.InventoryClient;
import com.company.sales.order.dto.InventoryReservationResponse;
import com.company.sales.order.dto.OrderResponse;
import com.company.sales.order.entity.Order;
import com.company.sales.order.repository.OrderRepository;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

        @Mock
        private OrderRepository orderRepository;

        @Mock
        private InventoryClient inventoryClient;

        @InjectMocks
        private OrderService orderService;

        @Test
        void shouldPlaceOrderSuccessfully() {

                // given
                Order order = new Order();
                order.setProductId(1002L);
                order.setQuantity(3);

                InventoryReservationResponse inventoryResponse = new InventoryReservationResponse(
                                "Smartphone",
                                List.of(3L),
                                "Inventory reserved.");

                when(inventoryClient.reserve(1002L, 3))
                                .thenReturn(inventoryResponse);

                // mock DB save
                Order saved = new Order();
                saved.setOrderId(5012L);
                saved.setProductId(1002L);
                saved.setProductName("Smartphone");
                saved.setQuantity(3);
                saved.setStatus("PLACED");

                when(orderRepository.save(any(Order.class)))
                                .thenReturn(saved);

                // when
                OrderResponse response = orderService.placeOrder(order);

                // then
                assertEquals(5012L, response.getOrderId());
                assertEquals(1002L, response.getProductId());
                assertEquals("Smartphone", response.getProductName());
                assertEquals(3, response.getQuantity());
                assertEquals("PLACED", response.getStatus());
                assertEquals(List.of(3L), response.getReservedFromBatchIds());
                assertTrue(response.getMessage().contains("Inventory reserved"));

                // verify interactions
                verify(inventoryClient).reserve(1002L, 3);
                verify(orderRepository).save(any(Order.class));
        }
}