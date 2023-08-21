package com.nv.inventoryservice.controller;

import com.nv.inventoryservice.model.Inventory;
import com.nv.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/{sku-code}")
    @ResponseStatus(HttpStatus.OK)
    public List<Inventory> isInStock(@PathVariable("sku-code") String skuCode) {
        return inventoryService.isInStock(skuCode);
    }

    @GetMapping("/qty/{quantity}")
    @ResponseStatus(HttpStatus.OK)
    public List<Inventory> getByQty(@PathVariable("quantity") Integer quantity) {
        return inventoryService.getByQty(quantity);
    }
}
