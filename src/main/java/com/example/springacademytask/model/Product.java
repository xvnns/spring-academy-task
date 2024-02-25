package com.example.springacademytask.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 90)
    private String name;

    @Column(name = "vendor_code", nullable = false, unique = true)
    private String vendorCode;

    @Column(length = 2000)
    private String description;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "quantity_update_date")
    private Date quantityUpdateDate;

    @Column(name = "create_ts", nullable = false)
    private Date createTs;
}
