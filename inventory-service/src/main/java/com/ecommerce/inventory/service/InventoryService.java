package com.ecommerce.inventory.service;

import com.ecommerce.inventory.dto.*;
import com.ecommerce.inventory.entity.Inventory;
import com.ecommerce.inventory.exception.InsufficientInventoryException;
import com.ecommerce.inventory.exception.InvalidQuantityException;
import com.ecommerce.inventory.exception.InventoryNotFoundException;
import com.ecommerce.inventory.repository.InventoryRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InventoryService {
    private static final Logger logger = LoggerFactory.getLogger((InventoryService.class));
    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public List<InventoryResponseDto> getAllInventories() {
        List<Inventory> inventories = inventoryRepository.findAll();
        List<InventoryResponseDto> response = new ArrayList<>();
        for (Inventory inventory : inventories) {
            InventoryResponseDto dto = new InventoryResponseDto();
            dto.setSkuCode(inventory.getSkuCode());
            dto.setAvailableQuantity(inventory.getAvailableQuantity());
            dto.setReservedQuantity(inventory.getReservedQuantity());
            response.add(dto);
        }
        return response;
    }

    @Transactional
    public void reserveInventory(String skuCode, ReserveInventoryDto reserveInventoryDto) {
        Integer quantity = reserveInventoryDto.getQuantity();
        if (quantity <= 0) {
            throw new InvalidQuantityException("Quantity must be greater than zero");
        }
        Inventory inventory = inventoryRepository.findBySkuCode(skuCode).orElseThrow(() -> new InventoryNotFoundException("Inventory not found for SKU: " + skuCode));
        if (inventory.getAvailableQuantity() < quantity) {
            throw new InsufficientInventoryException("Insufficient inventory");
        }
        inventory.setReservedQuantity(inventory.getReservedQuantity() + quantity);
        inventory.setAvailableQuantity(inventory.getAvailableQuantity() - quantity);
        inventoryRepository.save(inventory);
        logger.info("Reserved {} units for SKU: {}", quantity, inventory.getSkuCode());
        return;
    }

    @Transactional
    public void releaseInventory(String skuCode, ReleaseInventoryDto releaseInventoryDto) {
        Integer quantity = releaseInventoryDto.getQuantity();
        if (quantity <= 0) {
            throw new InvalidQuantityException("Quantity must be greater than zero");
        }
        Inventory inventory = inventoryRepository.findBySkuCode(skuCode).orElseThrow(() -> new InventoryNotFoundException("Inventory not found for SKU: " + skuCode));
        if (inventory.getReservedQuantity() < quantity) {
            throw new InsufficientInventoryException("Insufficient inventory");
        }
        inventory.setAvailableQuantity(inventory.getAvailableQuantity() + quantity);
        inventory.setReservedQuantity(inventory.getReservedQuantity() - quantity);
        inventoryRepository.save(inventory);
        logger.info("Released {} reserved units for SKU: {}", quantity, inventory.getSkuCode());
        return;
    }

    @Transactional
    public void createInventory(CreateInventoryDto createInventoryDto) {
        Inventory inventory = new Inventory();
        inventory.setSkuCode(createInventoryDto.getSkuCode());
        inventory.setAvailableQuantity(createInventoryDto.getAvailableQuantity());
        inventory.setReservedQuantity(createInventoryDto.getReservedQuantity());
        inventoryRepository.save(inventory);
        logger.info("Inventory created successfully for SKU: {}", inventory.getSkuCode());
        return;
    }

    @Transactional
    public void deleteInventory(String skuCode) {
        Inventory inventory = inventoryRepository.findBySkuCode(skuCode).orElseThrow(() -> new InventoryNotFoundException("Inventory not found."));
        logger.info("Inventory deleted for SKU: {}", inventory.getSkuCode());
        inventoryRepository.delete(inventory);
        return;
    }

    public InventoryResponseDto getInventory(String skuCode) {
        Inventory inventory = inventoryRepository.findBySkuCode(skuCode).orElseThrow(() -> new InventoryNotFoundException("Inventory not found."));
        InventoryResponseDto dto = new InventoryResponseDto();
        dto.setSkuCode(inventory.getSkuCode());
        dto.setAvailableQuantity(inventory.getAvailableQuantity());
        dto.setReservedQuantity(inventory.getReservedQuantity());
        dto.setCreatedAt(inventory.getCreatedAt());
        dto.setUpdatedAt(inventory.getUpdatedAt());
        return dto;
    }

    @Transactional
    public void restockInventory(String skuCode, RestockInventoryDto dto) {
        Inventory inventory = inventoryRepository.findBySkuCode(skuCode).orElseThrow(() -> new InventoryNotFoundException("Inventory not found."));
        inventory.setAvailableQuantity(inventory.getAvailableQuantity() + dto.getQuantity());
        inventoryRepository.save(inventory);
        logger.info("Restocked {} units for SKU: {}", dto.getQuantity(), inventory.getSkuCode());
        return;
    }
}