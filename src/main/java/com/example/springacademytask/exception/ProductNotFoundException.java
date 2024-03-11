package com.example.springacademytask.exception;

import com.example.springacademytask.constant.ErrorMessages;

import java.util.UUID;

public class ProductNotFoundException extends Exception {
    public ProductNotFoundException(UUID uuid) {
        super(String.format(ErrorMessages.PRODUCT_NOT_FOUND_EXCEPTION_MESSAGE, uuid));
    }
}
