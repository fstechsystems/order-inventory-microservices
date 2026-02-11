package com.company.supply.inventory.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.supply.inventory.dto.InventoryReservationResponse;
import com.company.supply.inventory.entity.InventoryBatch;
import com.company.supply.inventory.repository.InventoryRepository;
import com.company.supply.inventory.service.InventoryStrategy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExpiryBasedInventoryStrategy implements InventoryStrategy {

    private final InventoryRepository inventoryRepository;

    @Override
    public List<InventoryBatch> getBatches(Long productId) {
        log.info("Fetching batches using expiry strategy");
        return inventoryRepository.findByProductIdOrderByExpiryDateAsc(productId);
    }

    @Override
    @Transactional
    public InventoryReservationResponse reduceStock(Long productId, int qty) {
        List<InventoryBatch> batches = getBatches(productId);
        List<Long> usedBatches = new ArrayList<>();
        String productName = null;
        for (InventoryBatch batch : batches) {
            if (qty <= 0)
                break;
            productName = batch.getProductName();
            int available = batch.getQuantity();
            if (available > 0) {
                usedBatches.add(batch.getBatchId());
            }
            if (available >= qty) {
                batch.setQuantity(available - qty);
                qty = 0;
            } else {
                qty -= available;
                batch.setQuantity(0);
            }
            inventoryRepository.save(batch);
        }
        if (qty > 0) {
            throw new RuntimeException("Insufficient stock");
        }
        return new InventoryReservationResponse(productName, usedBatches, "Inventory reserved.");
    }
}