package com.company.supply.inventory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.supply.inventory.entity.InventoryBatch;

public interface InventoryRepository extends JpaRepository<InventoryBatch, Long> {

    List<InventoryBatch> findByProductIdOrderByExpiryDateAsc(Long productId);
}