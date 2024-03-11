package com.example.springacademytask.exception;

import com.example.springacademytask.constant.ErrorMessages;

public class ProductWithThisVendorCodeAlreadyExistsException extends Exception {
    public ProductWithThisVendorCodeAlreadyExistsException() {
        super(ErrorMessages.PRODUCT_WITH_THIS_VENDOR_CODE_ALREADY_EXISTS_EXCEPTION_MESSAGE);
    }
}
