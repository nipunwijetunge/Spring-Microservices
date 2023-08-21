package com.nv.inventoryservice.service.impl;

import com.nv.inventoryservice.model.Inventory;
import com.nv.inventoryservice.repository.InventoryRepository;
import com.nv.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    @Override
    @Transactional(readOnly = true)
    public List<Inventory> isInStock(String skuCode) {
        return inventoryRepository.findBySkuCode(skuCode);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Inventory> getByQty(Integer quantity) {
        return inventoryRepository.findByQuantity(quantity);
    }
}
