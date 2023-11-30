package com.ajeet.blog.ajeetblog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ajeet.blog.ajeetblog.payloads.ApiResponse;
import com.ajeet.blog.ajeetblog.payloads.CategoryDto;
import com.ajeet.blog.ajeetblog.services.CategoryService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService cateService;

    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto createdCatDto = this.cateService.createCat(categoryDto);
        return new ResponseEntity<CategoryDto>(createdCatDto, HttpStatus.CREATED);
    }

    @PutMapping("/{cateId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable int cateId){  
        CategoryDto cateDto = this.cateService.updateCatDto(categoryDto, cateId);   
        return new ResponseEntity<CategoryDto>(cateDto, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllCategories(){
        List<CategoryDto> cateDtos = this.cateService.getAllCats();
        return new ResponseEntity<List<CategoryDto>>(cateDtos, HttpStatus.OK);
    }

    @GetMapping("/{cateId}")
    public ResponseEntity<CategoryDto> getCateById(@PathVariable int cateId){
        CategoryDto cateDto = this.cateService.getCatById(cateId);
        return new ResponseEntity<CategoryDto>(cateDto, HttpStatus.OK);
    }

    @DeleteMapping("/{cateId}")
    public ResponseEntity<ApiResponse> deleteCatById(@PathVariable int cateId){
        this.cateService.deleteCatById(cateId);
        ApiResponse apiResponse = new ApiResponse("Category deleted successfully", true);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }

    
    
}
