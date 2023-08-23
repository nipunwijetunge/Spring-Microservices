package com.nv.inventoryservice.service;

import com.nv.inventoryservice.dto.InventoryResponse;
import com.nv.inventoryservice.model.Inventory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface InventoryService {

    List<InventoryResponse> isInStock(List<String> skuCode);

    List<Inventory> getByQty(Integer quantity);

    List<Inventory> getAll();
}
