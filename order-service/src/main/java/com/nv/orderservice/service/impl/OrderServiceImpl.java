package com.nv.orderservice.service.impl;

import brave.Span;
import brave.Tracer;
import com.nv.orderservice.dto.InventoryResponse;
import com.nv.orderservice.dto.OrderLineItemsDTO;
import com.nv.orderservice.dto.OrderRequest;
import com.nv.orderservice.model.Order;
import com.nv.orderservice.model.OrderLineItems;
import com.nv.orderservice.repository.OrderRepository;
import com.nv.orderservice.service.OrderService;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    private final ObservationRegistry observationRegistry;

//   private final Tracer tracer;

    @Override
    public String placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItems().stream().map(this::mapToDTO).toList();

        order.setOrderLineItems(orderLineItems);

        List<String> skuCodes = order.getOrderLineItems().stream().map(OrderLineItems::getSkuCode).toList();

        Observation inventoryServiceObservation = Observation.createNotStarted("inventory-service-lookup",
                this.observationRegistry);
        inventoryServiceObservation.lowCardinalityKeyValue("call", "inventory-service");

//        Span span = this.tracer.nextSpan().name("inventoryServiceLookup");

        return inventoryServiceObservation.observe(() -> {
            InventoryResponse[] result = webClientBuilder.build().get()
                    .uri("http://inventory-service/api/inventory", uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                    .retrieve()
                    .bodyToMono(InventoryResponse[].class)
                    .block();

            assert result != null;
            boolean allProductsInStock = Arrays.stream(result).allMatch(InventoryResponse::isInStock);

            if (allProductsInStock) {
                orderRepository.save(order);

                return "Order placed successfully!";
            } else {
                throw new IllegalArgumentException("The product is not available, Please try again later.");
            }
        });

//        try (Tracer.SpanInScope ws = this.tracer.withSpanInScope(span.start())) {
//            InventoryResponse[] result = webClientBuilder.build().get()
//                    .uri("http://inventory-service/api/inventory", uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
//                    .retrieve()
//                    .bodyToMono(InventoryResponse[].class)
//                    .block();
//
//            assert result != null;
//            boolean allProductsInStock = Arrays.stream(result).allMatch(InventoryResponse::isInStock);
//
//            if (allProductsInStock) {
//                orderRepository.save(order);
//
//                return "Order placed successfully!";
//            } else {
//                throw new IllegalArgumentException("The product is not available, Please try again later.");
//            }
//        } finally {
//            span.finish();
//        }
    }

    private OrderLineItems mapToDTO(OrderLineItemsDTO orderLineItemsDTO) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setId(orderLineItemsDTO.getId());
        orderLineItems.setPrice(orderLineItemsDTO.getPrice());
        orderLineItems.setQuantity(orderLineItemsDTO.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDTO.getSkuCode());

        return orderLineItems;
    }
}
