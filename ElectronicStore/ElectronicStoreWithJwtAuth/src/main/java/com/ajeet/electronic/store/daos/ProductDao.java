package com.ajeet.electronic.store.daos;

import com.ajeet.electronic.store.entities.Category;
import com.ajeet.electronic.store.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

public interface ProductDao extends JpaRepository<Product, String> {
    Page<Product> findByLiveTrue(Pageable pageable);
    Page<Product> findByTitleContaining(String keyword, Pageable pageable);

    Page<Product> findByCategory(Category category, Pageable pageable);
}
