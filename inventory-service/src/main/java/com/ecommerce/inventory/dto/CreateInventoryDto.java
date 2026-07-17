package com.ecommerce.inventory.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateInventoryDto {

    @NotBlank(message = "SKU Code is required")
    private String skuCode;

    @NotNull(message = "Available quantity is required")
    @Min(value = 0, message = "Available quantity cannot be negative")
    private Integer availableQuantity;

    @NotNull(message = "Reserved quantity is required.")
    @Min(value = 0, message = "Reserved quantity cannot be negative.")
    private Integer reservedQuantity;
}