package com.company.supply.inventory.dto;

import lombok.Data;

@Data
public class InventoryUpdateRequest {

    private Long productId;
    private Integer quantity;
}