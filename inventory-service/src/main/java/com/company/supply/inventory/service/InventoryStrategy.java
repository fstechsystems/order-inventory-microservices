package com.company.supply.inventory.service;

import java.util.List;

import com.company.supply.inventory.dto.InventoryReservationResponse;
import com.company.supply.inventory.entity.InventoryBatch;

public interface InventoryStrategy {

    List<InventoryBatch> getBatches(Long productId);

    InventoryReservationResponse reduceStock(Long productId, int quantity);
}