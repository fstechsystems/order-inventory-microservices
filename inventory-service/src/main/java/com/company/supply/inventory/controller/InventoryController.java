package com.company.supply.inventory.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.supply.inventory.dto.InventoryReservationResponse;
import com.company.supply.inventory.dto.InventoryUpdateRequest;
import com.company.supply.inventory.entity.InventoryBatch;
import com.company.supply.inventory.service.InventoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
@Slf4j
public class InventoryController {

    private final InventoryService service;

    @GetMapping("/{productId}")
    public List<InventoryBatch> getInventory(@PathVariable("productId") Long productId) {
        log.info("Get inventory for {}", productId);
        return service.getInventory(productId);
    }

    @PostMapping("/update")
    public InventoryReservationResponse update(@RequestBody InventoryUpdateRequest req) {
        return service.updateInventory(req.getProductId(), req.getQuantity());
    }
}