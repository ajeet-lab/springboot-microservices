package com.ajeet.electronic.store.daos;

import com.ajeet.electronic.store.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDao extends JpaRepository<Product, String> {
}
