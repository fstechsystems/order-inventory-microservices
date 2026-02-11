package com.company.sales.order.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderResponse {

    private Long orderId;
    private Long productId;
    private String productName;
    private Integer quantity;
    private String status;
    private List<Long> reservedFromBatchIds;
    private String message;
}