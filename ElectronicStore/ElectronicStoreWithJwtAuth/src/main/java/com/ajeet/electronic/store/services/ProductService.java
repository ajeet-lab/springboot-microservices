package com.ajeet.electronic.store.services;

import com.ajeet.electronic.store.dtos.ProductDto;
import com.ajeet.electronic.store.helpers.PageableResponse;

import java.io.IOException;

public interface ProductService {

    ProductDto create(ProductDto productDto);

    PageableResponse<ProductDto> getAllProducts(int pageNumber, int pageSize, String sortBy, String sortDir);
    ProductDto getById(String productId);

    ProductDto updateById(String productId, ProductDto productDto);

    PageableResponse<ProductDto> getAllLiveProducts(int pageNumber, int pageSize, String sortBy, String sortDir);

    PageableResponse<ProductDto> searchProduct(String keyword, int pageNumber, int pageSize, String sortBy, String sortDir);

    void deleteProduct(String productId) throws IOException;

    ProductDto createProductWithCategory(String categoryId, ProductDto productDto);

    ProductDto updateCategoryInExistingProduct(String categoryId, String productId);

    PageableResponse<ProductDto> getAllProductOfCategory(String categoryId, int pageNumber, int pageSize, String sortBy, String sortDir);
}
