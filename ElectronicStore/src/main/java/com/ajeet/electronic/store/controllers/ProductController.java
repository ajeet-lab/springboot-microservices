package com.ajeet.electronic.store.controllers;


import com.ajeet.electronic.store.dtos.ProductDto;
import com.ajeet.electronic.store.helpers.PageableResponse;
import com.ajeet.electronic.store.services.ProductService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }


    @PostMapping
    public ProductDto create(@RequestBody ProductDto productDto){
        return this.productService.create(productDto);
    }

    @GetMapping
    public PageableResponse<ProductDto> getAll(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ){
        return this.productService.getAllProducts(pageNumber, pageSize, sortBy, sortDir);
    }
}
