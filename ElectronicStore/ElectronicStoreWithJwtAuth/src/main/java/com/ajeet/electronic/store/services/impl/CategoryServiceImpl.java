package com.ajeet.electronic.store.services.impl;

import com.ajeet.electronic.store.daos.CategoryDao;
import com.ajeet.electronic.store.dtos.CategoryDto;
import com.ajeet.electronic.store.entities.Category;
import com.ajeet.electronic.store.exceptions.ResourceNotFoundException;
import com.ajeet.electronic.store.helpers.Helper;
import com.ajeet.electronic.store.helpers.PageableResponse;
import com.ajeet.electronic.store.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final String NOT_FOUND_BY_ID="Category not found with given category id : ";

    private final ModelMapper modelMapper;
    private final CategoryDao categoryDao;

    @Autowired
    public CategoryServiceImpl(ModelMapper modelMapper, CategoryDao categoryDao){
        this.modelMapper = modelMapper;
        this.categoryDao = categoryDao;
    }

    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        String categoryId = UUID.randomUUID().toString();
        categoryDto.setCategoryId(categoryId);
        Category category = categoryDao.save(this.modelMapper.map(categoryDto, Category.class));
        return this.modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto, String categoryId) {
        Category category = categoryDao.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException(NOT_FOUND_BY_ID+ categoryId));
        category.setTitle(categoryDto.getTitle());
        category.setTitle(categoryDto.getDescription());
        category = categoryDao.save(category);
        return this.modelMapper.map(category, CategoryDto.class);
    }

    //PageableResponse<CategoryDto>
    @Override
    public PageableResponse<CategoryDto> getAllCategory(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Category> page = this.categoryDao.findAll(pageable);
        return Helper.pageableResponse(page, CategoryDto.class);
    }

    @Override
    public CategoryDto getCategoryById(String categoryId) {
        Category category = categoryDao.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException(NOT_FOUND_BY_ID+ categoryId));
        return this.modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public void delete(String categoryId) {
        Category category = categoryDao.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException(NOT_FOUND_BY_ID+ categoryId));
        this.categoryDao.delete(category);
    }

}
