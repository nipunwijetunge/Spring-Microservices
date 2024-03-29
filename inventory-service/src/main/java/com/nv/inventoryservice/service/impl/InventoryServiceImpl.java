package com.nv.inventoryservice.service.impl;

import com.nv.inventoryservice.dto.InventoryResponse;
import com.nv.inventoryservice.model.Inventory;
import com.nv.inventoryservice.repository.InventoryRepository;
import com.nv.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    @Override
    @Transactional(readOnly = true)
    @SneakyThrows
    public List<InventoryResponse> isInStock(List<String> skuCode) {
        return inventoryRepository.findBySkuCodeIn(skuCode).stream()
                .map(inventory ->
                    InventoryResponse.builder()
                            .skuCode(inventory.getSkuCode())
                            .isInStock(inventory.getQuantity() > 0)
                            .build()
                ).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Inventory> getByQty(Integer quantity) {
        return inventoryRepository.findByQuantity(quantity);
    }

    @Override
    public List<Inventory> getAll() {
        return inventoryRepository.findAll();
    }
}
