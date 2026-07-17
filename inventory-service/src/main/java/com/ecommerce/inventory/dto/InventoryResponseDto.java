package com.ecommerce.inventory.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InventoryResponseDto {

    private String skuCode;
    private Integer availableQuantity;
    private Integer reservedQuantity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}