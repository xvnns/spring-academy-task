package com.example.springacademytask.dto;

import com.example.springacademytask.constant.ErrorMessages;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProductDto {
    @NotBlank(message = ErrorMessages.NAME_NOT_BLANK_MESSAGE)
    @NotNull(message = ErrorMessages.NAME_NOT_NULL_MESSAGE)
    @Size(max = 90, message = ErrorMessages.NAME_SIZE_MAX_MESSAGE)
    private String name;

    @NotBlank(message = ErrorMessages.VENDOR_CODE_NOT_BLANK_MESSAGE)
    @NotNull(message = ErrorMessages.VENDOR_CODE_NOT_NULL_MESSAGE)
    @Size(max = 255, message = ErrorMessages.VENDOR_CODE_SIZE_MAX_MESSAGE)
    @JsonProperty("vendor_code")
    private String vendorCode;

    @Size(max = 2000, message = ErrorMessages.DESCRIPTION_SIZE_MAX_MESSAGE)
    @NotBlank(message = ErrorMessages.DESCRIPTION_NOT_BLANK_MESSAGE)
    private String description;

    @NotBlank(message = ErrorMessages.CATEGORY_NOT_BLANK_MESSAGE)
    @NotNull(message = ErrorMessages.CATEGORY_NOT_NULL_MESSAGE)
    @Size(max = 255, message = ErrorMessages.CATEGORY_SIZE_MAX_MESSAGE)
    private String category;

    @NotNull(message = ErrorMessages.PRICE_NOT_NULL_MESSAGE)
    @DecimalMin(value = "0.0", message = ErrorMessages.PRICE_MIN_VALUE_MESSAGE)
    private Double price;

    @NotNull(message = ErrorMessages.QUANTITY_NOT_NULL_MESSAGE)
    @Min(value = 0, message = ErrorMessages.QUANTITY_MIN_VALUE_MESSAGE)
    private Integer quantity;
}
