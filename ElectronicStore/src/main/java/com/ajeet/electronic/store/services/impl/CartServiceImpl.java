package com.ajeet.electronic.store.services.impl;

import com.ajeet.electronic.store.daos.CartDao;
import com.ajeet.electronic.store.daos.CartItemDao;
import com.ajeet.electronic.store.daos.ProductDao;
import com.ajeet.electronic.store.daos.UserDao;
import com.ajeet.electronic.store.dtos.CartDto;
import com.ajeet.electronic.store.entities.*;
import com.ajeet.electronic.store.exceptions.ResourceNotFoundException;
import com.ajeet.electronic.store.helpers.AddToCartRequest;
import com.ajeet.electronic.store.services.CartService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;


@Service
public class CartServiceImpl implements CartService {

    private final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    private static final String USER_NOT_FOUND_BY_ID = "User not found with given id : ";
    private static final String PRODUCT_NOT_FOUND_BY_ID = "Product not found with given id : ";
    private static final String CART_NOT_FOUND_BY_USER = "Cart of given user not found !! ";

    private final UserDao userDao;
    private final ProductDao productDao;
    private final CartDao cartDao;
    private final CartItemDao cartItemDao;
    private final ModelMapper modelMapper;

    @Autowired
    public CartServiceImpl(UserDao userDao, ProductDao productDao, CartDao cartDao, ModelMapper modelMapper, CartItemDao cartItemDao) {
        this.userDao = userDao;
        this.productDao = productDao;
        this.cartDao = cartDao;
        this.modelMapper = modelMapper;
        this.cartItemDao = cartItemDao;
    }

    @Override
    public CartDto addItemToCart(String userId, AddToCartRequest request) {
        String productId = request.getProductId();
        int quantity = request.getQuantity();

        User user = this.userDao.findById(userId).orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_BY_ID + userId));

        Product product = this.productDao.findById(productId).orElseThrow(() -> new ResourceNotFoundException(PRODUCT_NOT_FOUND_BY_ID + productId));

        Cart cart = null;

        try {
            cart = this.cartDao.findByUser(user).get();
        } catch (NoSuchElementException ex) {
            cart = new Cart();
            cart.setCartId(UUID.randomUUID().toString());
            cart.setCreatedAt(new Date());
        }

        // Perform cart operations
        AtomicReference<Boolean> updated = new AtomicReference<>(false);
        List<CartItem> items = cart.getItems();
        List<CartItem> updatedItems = items.stream().map(item -> {
            if (item.getProduct().getProductId().equals(productId)) {
                item.setQuantity(quantity);
                item.setTotalPrice(quantity * product.getDiscountedPrice());
                updated.set(true);
            }
            return item;
        }).collect(Collectors.toList());

        cart.setItems(updatedItems);
        if (!updated.get()) {
            CartItem cartItem = CartItem.builder()
                    .quantity(quantity)
                    .totalPrice(quantity * product.getDiscountedPrice())
                    .cart(cart)
                    .product(product)
                    .build();
            cart.getItems().add(cartItem);
        }
        cart.setUser(user);
        Cart updatedCart = cartDao.save(cart);
        return this.modelMapper.map(updatedCart, CartDto.class);
    }

    @Override
    public void removeItemFromCart(String userId, int  cartItemId) {
        CartItem cartItem = this.cartItemDao.findById(cartItemId).orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_BY_ID + cartItemId));
        this.cartItemDao.delete(cartItem);
    }


    @Override
    public void clearItemFromCart(String userId) {
        User user = this.userDao.findById(userId).orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_BY_ID + userId));

        Cart cart = this.cartDao.findByUser(user).orElseThrow(() -> new ResourceNotFoundException(CART_NOT_FOUND_BY_USER));

        cart.getItems().clear();
        this.cartDao.save(cart);
    }

    @Override
    public CartDto getCartByUser(String userId) {
        User user = this.userDao.findById(userId).orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_BY_ID + userId));
        Cart cart = this.cartDao.findByUser(user).orElseThrow(() -> new ResourceNotFoundException(CART_NOT_FOUND_BY_USER));
        return this.modelMapper.map(cart, CartDto.class);
    }
}
