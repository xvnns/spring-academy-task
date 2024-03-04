package com.example.springacademytask.exception;

public class ProductWithThisVendorCodeAlreadyExistsException extends Exception {
    public ProductWithThisVendorCodeAlreadyExistsException() {
        super("A product with the same vendor code already exists");
    }
}
