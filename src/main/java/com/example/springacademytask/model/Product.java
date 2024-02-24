package com.example.springacademytask.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product", uniqueConstraints = @UniqueConstraint(columnNames = "vendor_code"))
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "vendor_code")
    @JsonProperty("vendor_code")
    private String vendorCode;

    private String description;

    @Column(name = "category")
    private String category;

    private Double price;

    private Integer quantity;

    @Column(name = "quantity_update_date")
    private Date quantityUpdateDate;

    @Column(name = "create_ts")
    private Date createTs;
}
