package com.example.springacademytask.service;

import com.example.springacademytask.dto.ProductDto;
import com.example.springacademytask.exception.ProductNotFoundException;
import com.example.springacademytask.exception.ProductWithThisVendorCodeAlreadyExistsException;
import com.example.springacademytask.mapper.ProductMapper;
import com.example.springacademytask.model.Product;
import com.example.springacademytask.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductDto createProduct(ProductDto productDto) throws ProductWithThisVendorCodeAlreadyExistsException {
        if (productRepository.existsByVendorCode(productDto.getVendorCode())) {
            throw new ProductWithThisVendorCodeAlreadyExistsException();
        } else {
            Product product = ProductMapper.map(productDto);
            product.setCreateTs(new Date());
            return ProductMapper.map(productRepository.save(product));
        }
    }

    public List<ProductDto> getAllProducts(String name, String category) {
        List<Product> productList;
        if (name != null) {
            if (category != null) {
                productList = productRepository
                        .findAllByNameContainsIgnoreCaseAndCategoryContainsIgnoreCase(name, category);
            } else {
                productList = productRepository.findAllByNameContainsIgnoreCase(name);
            }
        } else if (category != null) {
            productList = productRepository.findAllByCategoryContainsIgnoreCase(category);
        } else {
            productList = productRepository.findAll();
        }
        return productList.stream().map(ProductMapper::map).collect(Collectors.toList());
    }

    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream().map(ProductMapper::map).collect(Collectors.toList());
    }

    public ProductDto getProductById(UUID id) throws ProductNotFoundException {
        Product product = productRepository.findById(id);
        if (product == null) {
            throw new ProductNotFoundException(id);
        }
        return ProductMapper.map(product);
    }

    public ProductDto updateProduct(UUID id, ProductDto productDto) throws ProductNotFoundException {
        Product product = productRepository.findById(id);
        if (product == null) {
            throw new ProductNotFoundException(id);
        }
        product.setName(productDto.getName());
        product.setVendorCode(productDto.getVendorCode());
        product.setDescription(productDto.getDescription());
        product.setCategory(productDto.getCategory());
        product.setPrice(productDto.getPrice());
        if (!Objects.equals(productDto.getQuantity(), product.getQuantity())) {
            product.setQuantityUpdateDate(new Date());
        }
        product.setQuantity(productDto.getQuantity());
        return ProductMapper.map(productRepository.save(product));
    }

    public void deleteAllProducts() {
        productRepository.deleteAll();
    }

    public void deleteProductById(UUID id) {
        productRepository.deleteById(id);
    }
}
