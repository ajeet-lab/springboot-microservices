package com.ajeet.electronic.store.daos;


import com.ajeet.electronic.store.entities.Order;
import com.ajeet.electronic.store.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDao  extends JpaRepository<Order, String> {
    List<Order> findByUser(User user);
}
