package com.ecommerce.inventory.controller;

import com.ecommerce.inventory.dto.*;
import com.ecommerce.inventory.response.ApiResponse;
import com.ecommerce.inventory.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventories")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<InventoryResponseDto>>> getAllInventories() {
        List<InventoryResponseDto> inventories = inventoryService.getAllInventories();
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Inventories fetched successfully.",
                        inventories
                )
        );
    }

    @GetMapping("/{skuCode}")
    public ResponseEntity<ApiResponse<InventoryResponseDto>> getInventory(@PathVariable String skuCode) {
        InventoryResponseDto inventory = inventoryService.getInventory(skuCode);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Inventory fetched successfully.",
                        inventory
                )
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createInventory(
            @Valid @RequestBody CreateInventoryDto createInventoryDto) {
        inventoryService.createInventory(createInventoryDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Inventory created successfully."));
    }

    @PostMapping("/{skuCode}/reserve")
    public ResponseEntity<ApiResponse<Void>> reserveInventory(
            @PathVariable String skuCode,
            @Valid @RequestBody ReserveInventoryDto reserveInventoryDto) {
        inventoryService.reserveInventory(skuCode, reserveInventoryDto);
        return ResponseEntity.ok(
                ApiResponse.success("Inventory reserved successfully.")
        );
    }

    @PostMapping("/{skuCode}/release")
    public ResponseEntity<ApiResponse<Void>> releaseInventory(
            @PathVariable String skuCode,
            @Valid @RequestBody ReleaseInventoryDto releaseInventoryDto) {
        inventoryService.releaseInventory(skuCode, releaseInventoryDto);
        return ResponseEntity.ok(
                ApiResponse.success("Inventory released successfully.")
        );
    }

    @PostMapping("/{skuCode}/restock")
    public ResponseEntity<ApiResponse<Void>> restockInventory(
            @PathVariable String skuCode,
            @Valid @RequestBody RestockInventoryDto restockInventoryDto) {
        inventoryService.restockInventory(skuCode, restockInventoryDto);
        return ResponseEntity.ok(
                ApiResponse.success("Inventory restocked successfully.")
        );
    }

    @DeleteMapping("/{skuCode}")
    public ResponseEntity<ApiResponse<Void>> deleteInventory(
            @PathVariable String skuCode) {
        inventoryService.deleteInventory(skuCode);
        return ResponseEntity.ok(
                ApiResponse.success("Inventory deleted successfully.")
        );
    }
}
