package com.ajeet.electronic.store.services.impl;

import com.ajeet.electronic.store.daos.CategoryDao;
import com.ajeet.electronic.store.daos.ProductDao;
import com.ajeet.electronic.store.dtos.ProductDto;
import com.ajeet.electronic.store.entities.Category;
import com.ajeet.electronic.store.entities.Product;
import com.ajeet.electronic.store.exceptions.ResourceNotFoundException;
import com.ajeet.electronic.store.helpers.Helper;
import com.ajeet.electronic.store.helpers.PageableResponse;
import com.ajeet.electronic.store.services.ProductService;
import org.modelmapper.ModelMapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;


@Service
public class ProductServiceImpl implements ProductService {

    private static final String NOT_FOUND_BYID="Product not found with given id : ";

    private final ProductDao productDao;
    private final ModelMapper modelMapper;
    private final CategoryDao categoryDao;

    public ProductServiceImpl(ProductDao productDao, ModelMapper modelMapper, CategoryDao categoryDao){
        this.productDao = productDao;
        this.modelMapper = modelMapper;
        this.categoryDao=categoryDao;
    }

    @Override
    public ProductDto create(ProductDto productDto) {
        String productId = UUID.randomUUID().toString();
        productDto.setProductId(productId);
        productDto.setAddedDate(new Date());
        Product product = this.modelMapper.map(productDto, Product.class);
        Product savedProduct = productDao.save(product);
        return this.modelMapper.map(savedProduct, ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllProducts(int pageNumber, int pageSize, String sortBy, String sortDir) {
         Sort sort =   sortDir.equalsIgnoreCase("desc") ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = this.productDao.findAll(pageable);
        return Helper.pageableResponse(page, ProductDto.class);
    }

    @Override
    public ProductDto getById(String productId) {
        Product product = this.productDao.findById(productId).orElseThrow(()-> new ResourceNotFoundException(NOT_FOUND_BYID+productId));
        return this.modelMapper.map(product, ProductDto.class);
    }

    @Override
    public ProductDto updateById(String productId, ProductDto productDto) {
        Product product = this.productDao.findById(productId).orElseThrow(()-> new ResourceNotFoundException(NOT_FOUND_BYID+productId));
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setLive(productDto.isLive());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        product.setStock(productDto.isStock());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setImageName(productDto.getImageName());
        Product updatedProduct = this.productDao.save(product);
        return this.modelMapper.map(updatedProduct, ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllLiveProducts(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort =   sortDir.equalsIgnoreCase("desc") ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = this.productDao.findByLiveTrue(pageable);
        return Helper.pageableResponse(page, ProductDto.class);
    }


    @Override
    public PageableResponse<ProductDto> searchProduct(String keyword, int pageNumber, int pageSize, String sortBy, String sortDir) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Product> page = this.productDao.findByTitleContaining(keyword, pageable);
        return Helper.pageableResponse(page, ProductDto.class);
    }

    @Override
    public void deleteProduct(String productId) throws IOException {
        Product product = this.productDao.findById(productId).orElseThrow(()-> new ResourceNotFoundException(NOT_FOUND_BYID+productId));
        this.productDao.delete(product);
    }

    @Override
    public ProductDto createProductWithCategory(String categoryId, ProductDto productDto) {
        Category category = this.categoryDao.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category not found with given id : "+categoryId));
        Product product = this.modelMapper.map(productDto, Product.class);
        String productId = UUID.randomUUID().toString();
        product.setProductId(productId);
        product.setCategory(category);
        product.setAddedDate(new Date());
        Product savedProduct = productDao.save(product);
        return this.modelMapper.map(savedProduct, ProductDto.class);
    }

    @Override
    public ProductDto updateCategoryInExistingProduct(String categoryId, String productId) {
        Category category = this.categoryDao.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category not found with given id : "+categoryId));
        Product product = this.productDao.findById(productId).orElseThrow(()-> new ResourceNotFoundException(NOT_FOUND_BYID+productId));
        product.setCategory(category);
        product = this.productDao.save(product);
        return this.modelMapper.map(product, ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllProductOfCategory(String categoryId, int pageNumber, int pageSize, String sortBy, String sortDir) {
        Category category = this.categoryDao.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category not found with given id : "+categoryId));
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Product> page = this.productDao.findByCategory(category, pageable);;
        return Helper.pageableResponse(page, ProductDto.class);
    }
}
