package com.ajeet.electronic.store.services.impl;

import com.ajeet.electronic.store.daos.ProductDao;
import com.ajeet.electronic.store.dtos.ProductDto;
import com.ajeet.electronic.store.dtos.UserDto;
import com.ajeet.electronic.store.entities.Product;
import com.ajeet.electronic.store.entities.User;
import com.ajeet.electronic.store.helpers.Helper;
import com.ajeet.electronic.store.helpers.PageableResponse;
import com.ajeet.electronic.store.services.ProductService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service
public class ProductServiceImpl implements ProductService {
    private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductDao productDao;
    private final ModelMapper modelMapper;

    public ProductServiceImpl(ProductDao productDao, ModelMapper modelMapper){
        this.productDao = productDao;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductDto create(ProductDto productDto) {
        String productId = UUID.randomUUID().toString();
        productDto.setProductId(productId);
        productDto.setAddedDate(new Date());
        logger.info("Create >>>>>>>> {} ", productDto.getAddedDate()+", "+productDto.getTitle());
        Product product = this.modelMapper.map(productDto, Product.class);
        Product savedProduct = productDao.save(product);
        return this.modelMapper.map(savedProduct, ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllProducts(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort =   sortDir.equalsIgnoreCase("desc") ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
       // Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Product> page = this.productDao.findAll(pageable);
        return Helper.pageableResponse(page, ProductDto.class);
    }

    @Override
    public ProductDto getById(String productId) {
        return null;
    }

    @Override
    public ProductDto updateById(String productId, ProductDto productDto) {
        return null;
    }

    @Override
    public ProductDto getByEmail(String email) {
        return null;
    }

    @Override
    public List<ProductDto> searchProduct(String keyword) {
        return List.of();
    }

    @Override
    public void deleteProduct(String productId) throws IOException {

    }
}
