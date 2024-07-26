package com.ajeet.electronic.store.controllers;


import com.ajeet.electronic.store.dtos.CategoryDto;
import com.ajeet.electronic.store.dtos.ProductDto;
import com.ajeet.electronic.store.helpers.ApiResponse;
import com.ajeet.electronic.store.helpers.PageableResponse;
import com.ajeet.electronic.store.services.CategoryService;
import com.ajeet.electronic.store.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;
    private final ProductService productService;

    @Autowired
    public CategoryController(CategoryService categoryService, ProductService productService){
        this.categoryService = categoryService;
        this.productService = productService;
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

    @PostMapping("/{categoryId}/product")
    public ResponseEntity<ProductDto> createProductWithCategoryId(@PathVariable("categoryId") String categoryId, @RequestBody ProductDto productDto){
        ProductDto productDto1 = this.productService.createProductWithCategory(categoryId, productDto);
        return new ResponseEntity<>(productDto1, HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}/product/{productId}")
    public ResponseEntity<ProductDto> updateCategoryInExistingProduct(@PathVariable("categoryId") String categoryId, @PathVariable("productId") String productId){
        ProductDto productDto1 = this.productService.updateCategoryInExistingProduct(categoryId, productId);
        return new ResponseEntity<>(productDto1, HttpStatus.OK);
    }

    @GetMapping("/{categoryId}/products")
    public ResponseEntity<PageableResponse<ProductDto>> getAllProductOfCategory(
            @PathVariable("categoryId") String categoryId,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir){
        PageableResponse<ProductDto>  productDtos = this.productService.getAllProductOfCategory(categoryId, pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }

}
