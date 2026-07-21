package com.ecommerce.order.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CreateOrderDto {
    @NotEmpty(message = "Order must contain at least one item.")
    @Valid
    private List<OrderLineRequestDto> orderLines;
}
