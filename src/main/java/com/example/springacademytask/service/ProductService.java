package com.example.springacademytask.service;

import com.example.springacademytask.dto.ProductDto;
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

    public ProductDto createProduct(ProductDto productDto) {
        Product product = ProductMapper.map(productDto);
        product.setCreateTs(new Date());
        return ProductMapper.map(productRepository.save(product));
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

    public ProductDto getProductById(UUID id) {
        Product product = productRepository.findById(id);
        return ProductMapper.map(product);
    }

    public ProductDto updateProduct(UUID id, ProductDto productDto) {
        Product product = productRepository.findById(id);

        product.setName(productDto.getName());
        product.setVendorCode(productDto.getVendorCode());
        product.setDescription(productDto.getDescription());
        product.setCategory(productDto.getCategory());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        if (!Objects.equals(productDto.getQuantity(), product.getQuantity())) {
            product.setQuantityUpdateDate(new Date());
        }

        return ProductMapper.map(productRepository.save(product));
    }

    public void deleteAllProducts() {
        productRepository.deleteAll();
    }

    public void deleteProductById(UUID id) {
        productRepository.deleteById(id);
    }
}
