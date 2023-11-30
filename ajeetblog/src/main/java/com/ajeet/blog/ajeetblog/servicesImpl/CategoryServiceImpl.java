package com.ajeet.blog.ajeetblog.servicesImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ajeet.blog.ajeetblog.entites.Category;
import com.ajeet.blog.ajeetblog.exceptions.ResourceNotFoundException;
import com.ajeet.blog.ajeetblog.payloads.CategoryDto;
import com.ajeet.blog.ajeetblog.repos.CateRepo;
import com.ajeet.blog.ajeetblog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CateRepo cateRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCat(CategoryDto catDto) {
        Category category = this.modelMapper.map(catDto, Category.class);
        Category saveCat = this.cateRepo.save(category);
        return this.modelMapper.map(saveCat, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCatDto(CategoryDto catDto, int catId) {
        Category category = this.cateRepo.findById(catId).orElseThrow(() -> new ResourceNotFoundException("Category ", "CategoryId ", catId));
        category.setCategoryName(catDto.getCategoryName());
        category.setCategoryDescription(catDto.getCategoryDescription());
        Category updatedCat = this.cateRepo.save(category);
        return this.modelMapper.map(updatedCat, CategoryDto.class);
    }

    @Override
    public CategoryDto getCatById(int catId) {
        Category category = this.cateRepo.findById(catId).orElseThrow(() -> new ResourceNotFoundException("Category ", "CategoryId ", catId));
        return this.modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCats() {
        List<Category> category = this.cateRepo.findAll();
        List<CategoryDto> catDtos = category.stream().map(cate -> this.modelMapper.map(cate, CategoryDto.class)).collect(Collectors.toList());
        return catDtos;
    }

    @Override
    public void deleteCatById(int catId) {
        Category category = this.cateRepo.findById(catId).orElseThrow(() -> new ResourceNotFoundException("Category ", "CategoryId ", catId));
        this.cateRepo.delete(category);
    }
    
}
