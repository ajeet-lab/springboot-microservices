package com.ajeet.electronic.store.services;

import com.ajeet.electronic.store.dtos.CategoryDto;
import com.ajeet.electronic.store.helpers.PageableResponse;

import java.util.List;

public interface CategoryService {
    //Create
    CategoryDto create(CategoryDto categoryDto);

    // Update
    CategoryDto update(CategoryDto categoryDto, String categoryId);

    // Delete
    void delete(String categoryId);

    // GetAll Category
    PageableResponse<CategoryDto> getAllCategory(int pageNumber, int pageSize, String sortBy, String sortDir);

    // Get category by id
    CategoryDto getCategoryById(String categoryId);
}
