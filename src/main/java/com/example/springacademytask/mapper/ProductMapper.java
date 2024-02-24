package com.example.springacademytask.mapper;

import com.example.springacademytask.dto.ProductDto;
import com.example.springacademytask.model.Product;

public class ProductMapper {

    public static ProductDto map(Product product) {
        return ProductDto.builder()
                .name(product.getName())
                .description(product.getDescription())
                .category(product.getCategory())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .build();
    }

    public static Product map(ProductDto productDto) {
        return Product.builder()
                .name(productDto.getName())
                .description(productDto.getDescription())
                .category(productDto.getCategory())
                .price(productDto.getPrice())
                .quantity(productDto.getQuantity())
                .build();
    }
}
