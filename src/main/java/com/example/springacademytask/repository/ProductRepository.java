package com.example.springacademytask.repository;

import com.example.springacademytask.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findById(UUID id);

    List<Product> findAllByNameContainsIgnoreCase(String productName);

    List<Product> findAllByCategoryContainsIgnoreCase(String productCategory);

    List<Product> findAllByNameContainsIgnoreCaseAndCategoryContainsIgnoreCase(String productName,
                                                                               String productCategory);

    void deleteById(UUID id);
}
