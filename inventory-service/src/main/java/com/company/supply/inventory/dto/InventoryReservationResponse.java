package com.company.supply.inventory.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryReservationResponse {

    private String productName;
    private List<Long> reservedBatchIds;
    private String message;
}