package com.nv.inventoryservice.service;

import com.nv.inventoryservice.model.Inventory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface InventoryService {

    List<Inventory> isInStock(String skuCode);

    List<Inventory> getByQty(Integer quantity);
}
