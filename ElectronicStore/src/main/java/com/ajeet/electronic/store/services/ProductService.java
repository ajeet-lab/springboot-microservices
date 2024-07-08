package com.ajeet.electronic.store.services;

import com.ajeet.electronic.store.dtos.ProductDto;
import com.ajeet.electronic.store.helpers.PageableResponse;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    ProductDto create(ProductDto productDto);

    PageableResponse<ProductDto> getAllProducts(int pageNumber, int pageSize, String sortBy, String sortDir);
    ProductDto getById(String productId);

    ProductDto updateById(String productId, ProductDto productDto);

    ProductDto getByEmail(String email);

    List<ProductDto> searchProduct(String keyword);

    void deleteProduct(String productId) throws IOException;

}
