package com.ajeet.electronic.store.controllers;


import com.ajeet.electronic.store.dtos.CategoryDto;
import com.ajeet.electronic.store.helpers.ApiResponse;
import com.ajeet.electronic.store.helpers.PageableResponse;
import com.ajeet.electronic.store.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }


    @PostMapping
    public ResponseEntity<CategoryDto> create(@RequestBody CategoryDto categoryDto){
        return new ResponseEntity<>(this.categoryService.create(categoryDto), HttpStatus.OK);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> update(@PathVariable("categoryId") String categoryId, @RequestBody CategoryDto categoryDto){
        return new ResponseEntity<>(this.categoryService.update(categoryDto, categoryId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PageableResponse<CategoryDto>> getAllCategories(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir)

    {
       // List<CategoryDto> categoryDtos;
        PageableResponse<CategoryDto> categoryDtos;
        categoryDtos = this.categoryService.getAllCategory(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(categoryDtos, HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getById(@PathVariable("categoryId") String categoryId){

        return new ResponseEntity<>(this.categoryService.getCategoryById(categoryId), HttpStatus.OK);
    }


    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> delete(@PathVariable("categoryId") String categoryId){
        this.categoryService.delete(categoryId);
        ApiResponse apiResponse = ApiResponse.builder().status(HttpStatus.OK).message("Categegory delete successfully with given categoryId "+ categoryId).isSuccess(true).build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
