package com.ajeet.blog.ajeetblog.services;

import java.util.List;

import com.ajeet.blog.ajeetblog.payloads.CategoryDto;

public interface CategoryService {
    CategoryDto createCat(CategoryDto catDto);

    CategoryDto updateCatDto(CategoryDto catDto, int catId);

    CategoryDto getCatById(int catId);

    List<CategoryDto> getAllCats();

    void deleteCatById(int catId);
}
