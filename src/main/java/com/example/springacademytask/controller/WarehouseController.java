package com.example.springacademytask.controller;

import com.example.springacademytask.model.Warehouse;
import com.example.springacademytask.repository.WarehouseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/warehouses")
public class WarehouseController {

    private final WarehouseRepository warehouseRepository;

    public WarehouseController(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    @PostMapping
    public ResponseEntity<Warehouse> create(@RequestBody Warehouse warehouse) {
        Warehouse createdWarehouse = Warehouse.builder()
                .productName(warehouse.getProductName())
                .vendorCode(warehouse.getVendorCode())
                .description(warehouse.getDescription())
                .productCategory(warehouse.getProductCategory())
                .price(warehouse.getPrice())
                .quantity(warehouse.getQuantity())
                .createTs(new Date())
                .build();
        return new ResponseEntity<>(warehouseRepository.save(createdWarehouse), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Warehouse>> getAll(@RequestParam(required = false) String productName,
                                                  @RequestParam(required = false) String productCategory) {
        List<Warehouse> warehouseList;
        if (productName != null) {
            if (productCategory != null) {
                warehouseList = warehouseRepository
                        .findAllByProductNameContainsIgnoreCaseAndProductCategoryContainsIgnoreCase(
                                productName, productCategory);
            } else {
                warehouseList = warehouseRepository.findByProductNameContainsIgnoreCase(productName);
            }
        } else if (productCategory != null) {
            warehouseList = warehouseRepository.findByProductCategoryContainsIgnoreCase(productCategory);
        } else {
            warehouseList = warehouseRepository.findAll();
        }
        return new ResponseEntity<>(warehouseList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Warehouse> getById(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(warehouseRepository.findById(id), HttpStatus.OK);
    }

    @GetMapping("/{vendorCode}")
    public ResponseEntity<Warehouse> getByVendorCode(@PathVariable("vendorCode") String vendorCode) {
        return new ResponseEntity<>(warehouseRepository.findByVendorCode(vendorCode), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Warehouse> updateById(@PathVariable("id") UUID id,
                                                @RequestBody Warehouse requestedWarehouse) {
        Warehouse warehouse = warehouseRepository.findById(id);
        warehouse.setProductName(requestedWarehouse.getProductName());
        warehouse.setDescription(requestedWarehouse.getDescription());
        warehouse.setPrice(requestedWarehouse.getPrice());
        warehouse.setQuantity(requestedWarehouse.getQuantity());
        warehouse.setQuantityUpdateDate(new Date());
        return new ResponseEntity<>(warehouseRepository.save(warehouse), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAll() {
        warehouseRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") UUID id) {
        warehouseRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
