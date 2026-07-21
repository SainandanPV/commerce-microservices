package com.ecommerce.order.mapper;

import com.ecommerce.order.dto.OrderLineRequestDto;
import com.ecommerce.order.dto.OrderLineResponseDto;
import com.ecommerce.order.dto.OrderResponseDto;
import com.ecommerce.order.entity.Order;
import com.ecommerce.order.entity.OrderLine;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderResponseDto toOrderResponseDto(Order order);
    OrderLineResponseDto toResponseDto(OrderLine orderLine);
    OrderLine toOrderLineEntity(OrderLineRequestDto orderLineRequestDto);
}
