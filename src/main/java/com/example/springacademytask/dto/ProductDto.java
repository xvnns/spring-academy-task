package com.example.springacademytask.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProductDto {
    @NotBlank(message = "Name must not be blank")
    @NotNull(message = "Please enter name")
    @Size(max = 90, message = "Name must not be more than 90 characters")
    private String name;

    @NotBlank(message = "Vendor code must not be blank")
    @NotNull(message = "Please enter vendor code")
    @Size(max = 255, message = "Vendor code must not be more than 255 characters")
    @JsonProperty("vendor_code")
    private String vendorCode;

    @Size(max = 2000, message = "Description must not be more than 2000 characters")
    @NotBlank(message = "Description must not be blank")
    private String description;

    @NotBlank(message = "Category must not be blank")
    @NotNull(message = "Please enter category")
    @Size(max = 255, message = "Category must not be more than 255 characters")
    private String category;

    @NotNull(message = "Please enter price")
    @DecimalMin(value = "0.0", message = "Price cannot be less than 0.0")
    private Double price;

    @NotNull(message = "Please enter quantity")
    @Min(value = 0, message = "Quantity cannot be less than 0")
    private Integer quantity;
}
