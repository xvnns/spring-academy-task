package com.example.springacademytask.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "warehouse", uniqueConstraints = @UniqueConstraint(columnNames = "vendor_code"))
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "vendor_code")
    private String vendorCode;

    private String description;

    @Column(name = "product_category")
    private String productCategory;

    private Double price;

    private Integer quantity;

    @Column(name = "quantity_update_date")
    private Date quantityUpdateDate;

    @Column(name = "create_ts")
    private Date createTs;
}
