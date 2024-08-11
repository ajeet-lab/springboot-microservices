package com.ajeet.electronic.store.services.impl;

import com.ajeet.electronic.store.daos.*;
import com.ajeet.electronic.store.dtos.CartDto;
import com.ajeet.electronic.store.dtos.OrderDto;
import com.ajeet.electronic.store.dtos.ProductDto;
import com.ajeet.electronic.store.entities.*;
import com.ajeet.electronic.store.exceptions.BadApiRequest;
import com.ajeet.electronic.store.exceptions.ResourceNotFoundException;
import com.ajeet.electronic.store.helpers.CreateOrderRequest;
import com.ajeet.electronic.store.helpers.Helper;
import com.ajeet.electronic.store.helpers.PageableResponse;
import com.ajeet.electronic.store.services.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;


@Service
public class OrderServiceImpl implements OrderService {

    private static final String USER_NOT_FOUND_BY_ID = "User is not found with given id : ";
    private static final String PRODUCT_NOT_FOUND_BY_ID = "Product is not found with given id : ";
    private static final String CART_NOT_FOUND_BY_USER = "Cart of given user not found !! ";
    private static final String CART_NOT_FOUND_BY_ID = "Cart is not found with given id : ";
    private static final String ORDER_NOT_FOUND_BY_ID = "Order is not found with given id : ";

    private final UserDao userDao;

    private final ProductDao productDao;

    private final OrderDao orderDao;

    private final OrderItemDao orderItemDao;

    private final CartDao cartDao;

    private final ModelMapper modelMapper;

    @Autowired
    public OrderServiceImpl(UserDao userDao, ProductDao productDao, OrderDao orderDao, OrderItemDao orderItemDao, ModelMapper modelMapper, CartDao cartDao) {
        this.userDao = userDao;
        this.productDao = productDao;
        this.orderDao = orderDao;
        this.orderItemDao = orderItemDao;
        this.modelMapper = modelMapper;
        this.cartDao = cartDao;
    }

    @Override
    public OrderDto createOrder(CreateOrderRequest createOrderRequest) {
        String userId=createOrderRequest.getUserId();
        String cartId=createOrderRequest.getCartId();
        User user = this.userDao.findById(userId).orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_BY_ID));
        Cart cart = this.cartDao.findById(cartId).orElseThrow(() -> new ResourceNotFoundException(CART_NOT_FOUND_BY_ID));

        List<CartItem> cartItems = cart.getItems();

        if (cartItems.size() <= 0) {
            throw new BadApiRequest("Invalid number of items in cart");
        }


        Order order = Order.builder()
                .billingName(createOrderRequest.getBillingName())
                .billingAddress(createOrderRequest.getBillingAddress())
                .billingPhone(createOrderRequest.getBillingPhone())
                .orderDate(new Date())
                .deliveredDate(null)
                .paymentStatus(createOrderRequest.getPaymentStatus())
                .orderStatus(createOrderRequest.getOrderStatus())
                .orderId(UUID.randomUUID().toString())
                .user(user)
                .build();


        // orderItems, amount
        AtomicReference<Integer> orderAmount = new AtomicReference<>(0);
        List<OrderItem> orderItems = cartItems.stream().map(cartItem -> {
            OrderItem orderItem = OrderItem.builder()
                    .quantity(cartItem.getQuantity())
                    .product(cartItem.getProduct())
                    .totalPrice(cartItem.getQuantity() * cartItem.getProduct().getDiscountedPrice())
                    .orders(order)
                    .build();
            orderAmount.set(orderAmount.get() + orderItem.getTotalPrice());
            return orderItem;
        }).collect(Collectors.toList());


        order.setOrderItems(orderItems);
        order.setOrderAmount(orderAmount.get());

        cart.getItems().clear();
        this.cartDao.save(cart);

        Order savedOrder = this.orderDao.save(order);
        return modelMapper.map(savedOrder, OrderDto.class);
    }

    @Override
    public void removeOrder(String orderId) {
        Order order = this.orderDao.findById(orderId).orElseThrow(() -> new ResourceNotFoundException(ORDER_NOT_FOUND_BY_ID+orderId));
        this.orderDao.delete(order);
    }

    @Override
    public List<OrderDto> getOrderOfUser(String userId) {
        User user = this.userDao.findById(userId).orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_BY_ID));

       List<Order> orders =  this.orderDao.findByUser(user);
        return orders.stream().map(order -> modelMapper.map(order, OrderDto.class)).collect(Collectors.toList());

    }

    @Override
    public PageableResponse<OrderDto> getOrders(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Order> page = this.orderDao.findAll(pageable);
        return Helper.pageableResponse(page, OrderDto.class);
    }
}
