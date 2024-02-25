package com.example.springacademytask.exception;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ValidationException extends Exception implements CustomException {
    private List<String> errors;

    @Override
    public List<String> getErrors() {
        return errors;
    }
}
