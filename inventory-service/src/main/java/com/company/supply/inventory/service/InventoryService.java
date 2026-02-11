package com.company.supply.inventory.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.company.supply.inventory.dto.InventoryReservationResponse;
import com.company.supply.inventory.entity.InventoryBatch;
import com.company.supply.inventory.factory.InventoryStrategyFactory;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryStrategyFactory factory;

    public List<InventoryBatch> getInventory(Long productId) {
        return factory.getStrategy("EXPIRY").getBatches(productId);
    }

    public InventoryReservationResponse updateInventory(Long productId, int qty) {
        return factory.getStrategy("EXPIRY").reduceStock(productId, qty);
    }
}