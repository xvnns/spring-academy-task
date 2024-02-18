package com.example.springacademytask.repository;

import com.example.springacademytask.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    Warehouse findById(UUID id);

    Warehouse findByVendorCode(String vendorCode);

    List<Warehouse> findByProductNameContainsIgnoreCase(String productName);

    List<Warehouse> findByProductCategoryContainsIgnoreCase(String productCategory);

    List<Warehouse> findAllByProductNameContainsIgnoreCaseAndProductCategoryContainsIgnoreCase(String productName,
                                                                                               String productCategory);

    void deleteById(UUID id);
}
