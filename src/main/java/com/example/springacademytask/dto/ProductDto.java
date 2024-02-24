package com.example.springacademytask.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProductDto {
    private String name;

    private String vendorCode;

    private String description;

    private String category;

    private Double price;

    private Integer quantity;
}
