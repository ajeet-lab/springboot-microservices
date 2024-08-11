package com.ajeet.electronic.store.daos;

import com.ajeet.electronic.store.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemDao extends JpaRepository<OrderItem, Integer> {
}
