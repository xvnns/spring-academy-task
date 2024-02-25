package com.example.springacademytask.exception;

import java.util.UUID;

public class ProductNotFoundException extends Exception {
    public ProductNotFoundException(UUID uuid) {
        super(String.format("Product with id %s not found", uuid));
    }
}
