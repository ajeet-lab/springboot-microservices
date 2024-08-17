package com.ajeet.electronic.store.daos;


import com.ajeet.electronic.store.entities.Order;
import com.ajeet.electronic.store.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderDao  extends JpaRepository<Order, String> {
    //Optional<Order> findByUser(User user);

    List<Order> findByUser(User user);
}