package com.ecommerce.order.controller;

import com.ecommerce.order.dto.CreateOrderDto;
import com.ecommerce.order.dto.OrderResponseDto;
import com.ecommerce.order.dto.UpdateOrderStatusDto;
import com.ecommerce.order.response.ApiResponse;
import com.ecommerce.order.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public ResponseEntity<ApiResponse<List<OrderResponseDto>>> getAllOrders() {
        List<OrderResponseDto> orders = orderService.getAllOrders();
        return ResponseEntity.ok(ApiResponse.success("Orders fetched successfully.", orders));
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<ApiResponse<OrderResponseDto>> getOrder(@PathVariable Integer orderId) {
        OrderResponseDto order = orderService.getOrder(orderId);
        return ResponseEntity.ok(ApiResponse.success("Order fetched successfully.", order));
    }

    @PostMapping("/orders")
    public ResponseEntity<ApiResponse<OrderResponseDto>> createOrder(@Valid @RequestBody CreateOrderDto dto) {
        OrderResponseDto order = orderService.createOrder(dto);
        return ResponseEntity.ok(ApiResponse.success("Order created successfully.", order));
    }

    @PatchMapping("/orders/{id}/status")
    public ResponseEntity<ApiResponse<OrderResponseDto>> updateOrderStatus(@PathVariable Integer id, @Valid @RequestBody UpdateOrderStatusDto dto) {
        OrderResponseDto order = orderService.updateOrderStatus(id, dto);
        return ResponseEntity.ok(ApiResponse.success("Order status updated successfully.", order));
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteOrder(@PathVariable Integer id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok(ApiResponse.success("Order deleted successfully."));
    }
}
