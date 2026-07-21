package com.ecommerce.order.dto;

import com.ecommerce.order.entity.Order;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderLineResponseDto {
    private Integer id;
    private String skuCode;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal lineTotal;
}
