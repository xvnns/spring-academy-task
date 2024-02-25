package com.example.springacademytask.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
public class BaseControllerAdvice {
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm z");

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        return response(HttpStatus.FORBIDDEN, new ValidationException(errors));
    }

    @ExceptionHandler({DataIntegrityViolationException.class, ProductNotFoundException.class})
    public Object handleMultipleExceptions(Exception ex) {
        return response(HttpStatus.FORBIDDEN, ex);
    }

    private Object response(HttpStatus status, Exception ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", dateTimeFormatter.format(ZonedDateTime.now()));
        body.put("status", status.toString());
        if (ex instanceof CustomException) {
            body.put("message", ((CustomException) ex).getErrors());
        } else {
            body.put("message", ex.getMessage());
        }
        return new ResponseEntity<>(body, status);
    }
}
