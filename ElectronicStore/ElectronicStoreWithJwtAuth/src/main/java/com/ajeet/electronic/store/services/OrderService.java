package com.ajeet.electronic.store.services;

import com.ajeet.electronic.store.dtos.OrderDto;
import com.ajeet.electronic.store.helpers.CreateOrderRequest;
import com.ajeet.electronic.store.helpers.PageableResponse;

import java.util.List;

public interface OrderService {

    // Create Order
    OrderDto createOrder(CreateOrderRequest createOrderRequest);
    // Remove Order
    void removeOrder(String orderId);
    // Get orders of user
    List<OrderDto> getOrderOfUser(String userId);

    // GetOrders
    PageableResponse<OrderDto> getOrders(int pageNumber, int pageSize, String sortBy, String sortDir);
}
