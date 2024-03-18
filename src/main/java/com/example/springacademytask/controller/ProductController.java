package com.example.springacademytask.controller;

import com.example.springacademytask.constant.SpringDocConst;
import com.example.springacademytask.dto.ProductDto;
import com.example.springacademytask.exception.ProductNotFoundException;
import com.example.springacademytask.exception.ProductWithThisVendorCodeAlreadyExistsException;
import com.example.springacademytask.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/products")
@Tag(name = SpringDocConst.TAG_NAME, description = SpringDocConst.TAG_DESCRIPTION)
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @Operation(
            summary = SpringDocConst.CREATE_OPERATION_SUMMARY,
            description = SpringDocConst.CREATE_OPERATION_DESCRIPTION
    )
    public ResponseEntity<ProductDto> create(@Valid @RequestBody ProductDto productDto)
            throws ProductWithThisVendorCodeAlreadyExistsException {
        return new ResponseEntity<>(productService.createProduct(productDto), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(
            summary = SpringDocConst.GET_ALL_OPERATION_SUMMARY,
            description = SpringDocConst.GET_ALL_OPERATION_DESCRIPTION
    )
    public ResponseEntity<List<ProductDto>> getAll(
            @RequestParam(required = false)
            @Parameter(description = SpringDocConst.GET_ALL_NAME_PARAMETER_DESCRIPTION)
            String name,

            @RequestParam(required = false)
            @Parameter(description = SpringDocConst.GET_ALL_CATEGORY_PARAMETER_DESCRIPTION)
            String category
    ) {
        return new ResponseEntity<>(productService.getAllProducts(name, category), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = SpringDocConst.GET_BY_ID_OPERATION_SUMMARY,
            description = SpringDocConst.GET_BY_ID_OPERATION_DESCRIPTION
    )
    public ResponseEntity<ProductDto> getById(
            @PathVariable("id")
            @Parameter(description = SpringDocConst.GET_BY_ID_ID_PARAMETER_DESCRIPTION, required = true)
            UUID id
    ) throws ProductNotFoundException {
        return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = SpringDocConst.UPDATE_OPERATION_SUMMARY,
            description = SpringDocConst.UPDATE_OPERATION_DESCRIPTION
    )
    public ResponseEntity<ProductDto> update(
            @PathVariable("id")
            @Parameter(description = SpringDocConst.UPDATE_ID_PARAMETER_DESCRIPTION, required = true)
            UUID id,

            @Valid @RequestBody ProductDto productDto
    ) throws ProductNotFoundException {
        return new ResponseEntity<>(productService.updateProduct(id, productDto), HttpStatus.OK);
    }

    @DeleteMapping
    @Operation(
            summary = SpringDocConst.DELETE_ALL_OPERATION_SUMMARY,
            description = SpringDocConst.DELETE_ALL_OPERATION_DESCRIPTION
    )
    public ResponseEntity<HttpStatus> deleteAll() {
        productService.deleteAllProducts();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = SpringDocConst.DELETE_BY_ID_OPERATION_SUMMARY,
            description = SpringDocConst.DELETE_BY_ID_OPERATION_DESCRIPTION
    )
    public ResponseEntity<HttpStatus> deleteById(
            @PathVariable("id")
            @Parameter(description = SpringDocConst.DELETE_BY_ID_ID_PARAMETER_DESCRIPTION)
            UUID id
    ) {
        productService.deleteProductById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
