package com.ajeet.electronic.store.services.impl;

import com.ajeet.electronic.store.daos.CategoryDao;
import com.ajeet.electronic.store.dtos.CategoryDto;
import com.ajeet.electronic.store.entities.Category;
import com.ajeet.electronic.store.entities.User;
import com.ajeet.electronic.store.exceptions.ResourceNotFoundException;
import com.ajeet.electronic.store.helpers.Helper;
import com.ajeet.electronic.store.helpers.PageableResponse;
import com.ajeet.electronic.store.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

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
        Category category = categoryDao.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category not found with given category id : "+ categoryId));
        category.setTitle(categoryDto.getTitle());
        category.setTitle(categoryDto.getDescription());
        category = categoryDao.save(category);
        return this.modelMapper.map(category, CategoryDto.class);
    }

    //PageableResponse<CategoryDto>
    @Override
    public PageableResponse<CategoryDto> getAllCategory(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        //Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Category> page = this.categoryDao.findAll(pageable);
        return Helper.pageableResponse(page, CategoryDto.class);
    }

    @Override
    public CategoryDto getCategoryById(String categoryId) {
        Category category = categoryDao.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category not found with given category id : "+ categoryId));
        return this.modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public void delete(String categoryId) {
        Category category = categoryDao.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category not found with given category id : "+ categoryId));
        this.categoryDao.delete(category);
    }

}
