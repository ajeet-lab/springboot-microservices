package com.ajeet.electronic.store.daos;

import com.ajeet.electronic.store.entities.Cart;
import com.ajeet.electronic.store.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartDao extends JpaRepository<Cart, String> {

    Optional<Cart> findByUser(User user);

}
