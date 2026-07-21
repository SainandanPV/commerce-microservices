package com.ecommerce.order.service;

import com.ecommerce.order.dto.CreateOrderDto;
import com.ecommerce.order.dto.OrderLineRequestDto;
import com.ecommerce.order.dto.OrderResponseDto;
import com.ecommerce.order.dto.UpdateOrderStatusDto;
import com.ecommerce.order.entity.Order;
import com.ecommerce.order.entity.OrderLine;
import com.ecommerce.order.entity.OrderStatus;
import com.ecommerce.order.exception.OrderNotFoundException;
import com.ecommerce.order.mapper.OrderMapper;
import com.ecommerce.order.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private static final Logger logger =
            LoggerFactory.getLogger(OrderService.class);

    public OrderService(
            OrderRepository orderRepository,
            OrderMapper orderMapper) {

        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    public List<OrderResponseDto> getAllOrders(){
        List<Order> orders = orderRepository.findAll();
        List<OrderResponseDto> response=new ArrayList<>();

        for(Order order:orders){
            response.add(orderMapper.toOrderResponseDto(order));
        }
        return response;
    }

    public OrderResponseDto getOrder(Integer orderId){
        Order order=orderRepository.findById(orderId).orElseThrow(()->new OrderNotFoundException("Order not found."));
        return orderMapper.toOrderResponseDto(order);
    }

@Transactional
    public OrderResponseDto createOrder(CreateOrderDto createOrderDto) {

        List<OrderLineRequestDto> orderLineRequestDto=createOrderDto.getOrderLines();
        Order order=new Order();
        BigDecimal totalAmount=BigDecimal.ZERO;
        for(OrderLineRequestDto orderLineRequest:orderLineRequestDto){
            OrderLine orderLine=orderMapper.toOrderLineEntity(orderLineRequest);
            orderLine.setOrder(order);
            BigDecimal lineTotal = orderLine.getUnitPrice()
                    .multiply(BigDecimal.valueOf(orderLine.getQuantity()));
            totalAmount = totalAmount.add(lineTotal);
            orderLine.setLineTotal(lineTotal);
            order.getOrderLines().add(orderLine);
        }

        order.setStatus(OrderStatus.PENDING);
        order.setTotalAmount(totalAmount);
        orderRepository.save(order);
        logger.info("Order created successfully with ID: {}", order.getId());
        return orderMapper.toOrderResponseDto(order);
    }

    @Transactional
    public OrderResponseDto updateOrderStatus(
            Integer id,
            UpdateOrderStatusDto dto) {

        Order order = orderRepository
                .findById(id)
                .orElseThrow(() ->
                        new OrderNotFoundException("Order not found."));

        order.setStatus(dto.getStatus());

        Order updatedOrder = orderRepository.save(order);
        logger.info(
                "Order {} status updated to {}",
                order.getId(),
                order.getStatus()
        );
        return orderMapper.toOrderResponseDto(updatedOrder);
    }

    @Transactional
    public void deleteOrder(Integer id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                        new OrderNotFoundException("Order not found."));

        orderRepository.delete(order);
    }
}
