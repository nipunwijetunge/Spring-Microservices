package com.nv.orderservice.service;

import com.nv.orderservice.dto.OrderRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


public interface OrderService {

    String placeOrder(OrderRequest orderRequest);
}
