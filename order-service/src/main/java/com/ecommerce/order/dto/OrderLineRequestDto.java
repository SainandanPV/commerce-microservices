package com.ecommerce.order.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderLineRequestDto {
    @NotBlank(message = "SKU Code is required.")
    private String skuCode;

    @NotNull(message = "Quantity is required.")
    @Min(value = 1, message = "Quantity must be greater than zero.")
    private Integer quantity;

    @NotNull(message = "Unit price is required.")
    @DecimalMin(value = "0.0", inclusive = false, message = "Unit price must be greater than zero.")
    private BigDecimal unitPrice;
}