package com.example.springacademytask.dto;

import com.example.springacademytask.constant.ValidationMessages;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProductDto {
    @NotBlank(message = ValidationMessages.NAME_NOT_BLANK_MESSAGE)
    @NotNull(message = ValidationMessages.NAME_NOT_NULL_MESSAGE)
    @Size(max = 90, message = ValidationMessages.NAME_SIZE_MAX_MESSAGE)
    private String name;

    @NotBlank(message = ValidationMessages.VENDOR_CODE_NOT_BLANK_MESSAGE)
    @NotNull(message = ValidationMessages.VENDOR_CODE_NOT_NULL_MESSAGE)
    @Size(max = 255, message = ValidationMessages.VENDOR_CODE_SIZE_MAX_MESSAGE)
    @JsonProperty("vendor_code")
    private String vendorCode;

    @Size(max = 2000, message = ValidationMessages.DESCRIPTION_SIZE_MAX_MESSAGE)
    @NotBlank(message = ValidationMessages.DESCRIPTION_NOT_BLANK_MESSAGE)
    private String description;

    @NotBlank(message = ValidationMessages.CATEGORY_NOT_BLANK_MESSAGE)
    @NotNull(message = ValidationMessages.CATEGORY_NOT_NULL_MESSAGE)
    @Size(max = 255, message = ValidationMessages.CATEGORY_SIZE_MAX_MESSAGE)
    private String category;

    @NotNull(message = ValidationMessages.PRICE_NOT_NULL_MESSAGE)
    @DecimalMin(value = "0.0", message = ValidationMessages.PRICE_MIN_VALUE_MESSAGE)
    private Double price;

    @NotNull(message = ValidationMessages.QUANTITY_NOT_NULL_MESSAGE)
    @Min(value = 0, message = ValidationMessages.QUANTITY_MIN_VALUE_MESSAGE)
    private Integer quantity;
}
