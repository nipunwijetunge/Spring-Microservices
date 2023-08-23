package com.nv.inventoryservice.controller;

import com.nv.inventoryservice.dto.InventoryResponse;
import com.nv.inventoryservice.model.Inventory;
import com.nv.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode) {
        return inventoryService.isInStock(skuCode);
    }

    @GetMapping("/qty/{quantity}")
    @ResponseStatus(HttpStatus.OK)
    public List<Inventory> getByQty(@PathVariable("quantity") Integer quantity) {
        return inventoryService.getByQty(quantity);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Inventory> getAll() {
        return inventoryService.getAll();
    }
}
