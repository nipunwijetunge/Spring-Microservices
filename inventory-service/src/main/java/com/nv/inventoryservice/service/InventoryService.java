package com.nv.inventoryservice.service;

import com.nv.inventoryservice.dto.InventoryResponse;
import com.nv.inventoryservice.model.Inventory;

import java.util.List;

public interface InventoryService {

    List<InventoryResponse> isInStock(List<String> skuCode);

    List<Inventory> getByQty(Integer quantity);

    List<Inventory> getAll();
}
