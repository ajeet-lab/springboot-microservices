package com.ajeet.electronic.store.daos;

import com.ajeet.electronic.store.entities.Cart;
import com.ajeet.electronic.store.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemDao extends JpaRepository<CartItem, Integer> {

    Optional<CartItem> findByCart(Cart cart);
}
