package com.ajeet.electronic.store.controllers;


import com.ajeet.electronic.store.dtos.OrderDto;
import com.ajeet.electronic.store.helpers.ApiResponse;
import com.ajeet.electronic.store.helpers.CreateOrderRequest;
import com.ajeet.electronic.store.helpers.PageableResponse;
import com.ajeet.electronic.store.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {


    private final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }


    @PreAuthorize("hasAnyRole('NORMAL','ADMIN')")
    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody CreateOrderRequest createOrderRequest){
        OrderDto orderDto = this.orderService.createOrder(createOrderRequest);
        return new ResponseEntity<>(orderDto, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('NORMAL','ADMIN')")
    @DeleteMapping("/{orderId}")
    public ResponseEntity<ApiResponse> deleteOrder(@PathVariable("orderId") String orderId){
        this.orderService.removeOrder(orderId);

        ApiResponse apiResponse = ApiResponse
                .builder()
                .isSuccess(true)
                .message("Order is deleted with given id: "+orderId)
                .status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('NORMAL','ADMIN')")
    @GetMapping("/users/{userId}")
    public ResponseEntity<List<OrderDto>> getOrderOfUser(@PathVariable("userId") String userId){
        List<OrderDto> orderDto = this.orderService.getOrderOfUser(userId);
        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<PageableResponse<OrderDto>> getOrders(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "billingName", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir)
    {
        PageableResponse<OrderDto> orderDto = this.orderService.getOrders(pageNumber, pageSize, sortBy,sortDir);
        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }



}
