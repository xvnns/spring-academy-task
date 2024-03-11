package com.example.springacademytask.util;

import com.example.springacademytask.dto.ProductDto;

import java.util.Random;

public class ProductTestUtil {
    public static ProductDto getProductWithBlankName() {
        ProductDto product = getRandomProduct();
        product.setName("");
        return product;
    }

    public static ProductDto getProductWithNullName() {
        ProductDto product = getRandomProduct();
        product.setName(null);
        return product;
    }

    public static ProductDto getProductWithLongName() {
        ProductDto product = getRandomProduct();
        product.setName(generateString(300));
        return product;
    }

    public static ProductDto getProductWithBlankVendorCode() {
        ProductDto product = getRandomProduct();
        product.setVendorCode("");
        return product;
    }

    public static ProductDto getProductWithNullVendorCode() {
        ProductDto product = getRandomProduct();
        product.setVendorCode(null);
        return product;
    }

    public static ProductDto getProductWithLongVendorCode() {
        ProductDto product = getRandomProduct();
        product.setVendorCode(generateString(300));
        return product;
    }

    public static ProductDto getProductWithBlankCategory() {
        ProductDto product = getRandomProduct();
        product.setCategory("");
        return product;
    }

    public static ProductDto getProductWithNullCategory() {
        ProductDto product = getRandomProduct();
        product.setCategory(null);
        return product;
    }

    public static ProductDto getProductWithLongCategory() {
        ProductDto product = getRandomProduct();
        product.setCategory(generateString(300));
        return product;
    }

    public static ProductDto getProductWithBlankDescription() {
        ProductDto product = getRandomProduct();
        product.setDescription("");
        return product;
    }

    public static ProductDto getProductWithLongDescription() {
        ProductDto product = getRandomProduct();
        product.setDescription(generateString(3000));
        return product;
    }

    public static ProductDto getProductWithNullPrice() {
        ProductDto product = getRandomProduct();
        product.setPrice(null);
        return product;
    }

    public static ProductDto getProductWithNegativePrice() {
        ProductDto product = getRandomProduct();
        product.setPrice(-100.0);
        return product;
    }

    public static ProductDto getProductWithNegativeQuantity() {
        ProductDto product = getRandomProduct();
        product.setQuantity(-200);
        return product;
    }

    public static ProductDto getProductWithNullQuantity() {
        ProductDto product = getRandomProduct();
        product.setQuantity(null);
        return product;
    }

    public static ProductDto getRandomProduct() {
        return ProductDto.builder()
                .name(generateString(20))
                .vendorCode(generateString(30))
                .description(generateString(200))
                .category("Category")
                .price(123.45)
                .quantity(123)
                .build();
    }

    public static String generateString(int targetStringLength) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();
        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
