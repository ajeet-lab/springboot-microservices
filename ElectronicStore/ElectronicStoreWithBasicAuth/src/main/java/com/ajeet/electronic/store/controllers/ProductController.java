package com.ajeet.electronic.store.controllers;


import com.ajeet.electronic.store.dtos.ProductDto;
import com.ajeet.electronic.store.helpers.ApiResponse;
import com.ajeet.electronic.store.helpers.ImageApiResponse;
import com.ajeet.electronic.store.helpers.PageableResponse;
import com.ajeet.electronic.store.services.FileService;
import com.ajeet.electronic.store.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Value("${product.image.path}")
    private String imagePath;

    private final ProductService productService;
    private final FileService fileService;

    public ProductController(ProductService productService, FileService fileService) {
        this.fileService=fileService;
        this.productService = productService;
    }


    @PostMapping
    public ProductDto create(@RequestBody ProductDto productDto) {
       return this.productService.create(productDto);
    }

    @GetMapping
    public PageableResponse<ProductDto> getAll(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        return this.productService.getAllProducts(pageNumber, pageSize, sortBy, sortDir);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getByProductId(@PathVariable String productId) {
        ProductDto productDto = this.productService.getById(productId);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateByProductId(@PathVariable String productId, @RequestBody ProductDto productDto) {
        ProductDto updateProductDto = this.productService.updateById(productId, productDto);
        return new ResponseEntity<>(updateProductDto, HttpStatus.OK);
    }

    @GetMapping("/live")
    public ResponseEntity<PageableResponse<ProductDto>> getAllLiveProduct(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        PageableResponse<ProductDto> allLiveProduct = this.productService.getAllLiveProducts(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(allLiveProduct, HttpStatus.OK);
    }

    @GetMapping("/search/{query}")
    public ResponseEntity<PageableResponse<ProductDto>> getByTitleProduct(
            @PathVariable String query,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {

        PageableResponse<ProductDto> getByTitleProducts = this.productService.searchProduct(query, pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(getByTitleProducts, HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse> deleteById(@PathVariable String productId) throws IOException {
        this.productService.deleteProduct(productId);
        ApiResponse apiResponse =ApiResponse.builder().isSuccess(true).message("Product deleted with given id :"+productId).status(HttpStatus.OK).build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/image/{productId}")
    public ResponseEntity<ImageApiResponse> uploadProductImage(@PathVariable("productId") String productId, @RequestParam("productImage") MultipartFile file) throws IOException {
        String uploadImaged = this.fileService.uploadFile(file, imagePath);
        ProductDto productDto = this.productService.getById(productId);
        String fullPath = imagePath+productDto.getImageName();
        productDto.setImageName(fullPath);

        Path path = Paths.get(fullPath);

        // Before the update, we check if the images are available or not.
        if(Files.exists(path)){
            Files.delete(path);
        }
        // Update the imageName in database with given user Id
        productDto.setImageName(uploadImaged);
        this.productService.updateById(productId, productDto);
        ImageApiResponse imageApiResponse = ImageApiResponse.builder().isSuccess(true).message("File uploaded successfully with Image name : " + uploadImaged).imageName(uploadImaged).status(HttpStatus.OK).build();
        return new ResponseEntity<>(imageApiResponse, HttpStatus.OK);
    }


    @GetMapping("/image/{productId}")
    public void serverImages(@PathVariable("productId") String userId, HttpServletResponse response) throws IOException {
        ProductDto productDto = this.productService.getById(userId);
        InputStream resource = fileService.getResource(imagePath, productDto.getImageName());
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }
}
