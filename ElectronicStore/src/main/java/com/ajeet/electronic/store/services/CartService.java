package com.ajeet.electronic.store.services;

import com.ajeet.electronic.store.dtos.CartDto;
import com.ajeet.electronic.store.helpers.AddToCartRequest;

public interface CartService {

    CartDto addItemToCart(String userId, AddToCartRequest request);


    void removeItemFromCart(String userId, int  cartItemId);

    void clearItemFromCart(String userId);

    CartDto getCartByUser(String userId);
}
