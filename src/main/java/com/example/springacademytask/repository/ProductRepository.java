package com.example.springacademytask.repository;

import com.example.springacademytask.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    boolean existsByVendorCode(String code);

    boolean existsById(UUID uuid);

    @Query("select p.id from Product p where p.vendorCode = :code")
    UUID findIdByVendorCode(@Param("code") String code);

    Product findByVendorCode(String code);

    long countByNameContainsIgnoreCase(String name);

    long countByCategoryContainsIgnoreCase(String category);

    long countByNameContainsIgnoreCaseAndCategoryContainsIgnoreCase(String name, String category);
}
