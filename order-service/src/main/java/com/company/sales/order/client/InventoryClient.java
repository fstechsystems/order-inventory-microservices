package com.company.sales.order.client;

import com.company.sales.order.dto.InventoryReservationResponse;

public interface InventoryClient {

    InventoryReservationResponse reserve(Long productId, int quantity);
}