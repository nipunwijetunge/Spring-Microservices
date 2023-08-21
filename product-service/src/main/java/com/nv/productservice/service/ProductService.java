package com.nv.productservice.service;

import com.nv.productservice.dto.ProductRequest;
import com.nv.productservice.dto.ProductResponse;

import java.util.List;

public interface ProductService {
    void createProduct(ProductRequest productRequest);
    List<ProductResponse> getAllProducts();
}
