package com.ecommerce.order.dto;

import com.ecommerce.order.entity.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponseDto {

    private Integer id;

    private OrderStatus status;

    private BigDecimal totalAmount;

    private List<OrderLineResponseDto> orderLines;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}