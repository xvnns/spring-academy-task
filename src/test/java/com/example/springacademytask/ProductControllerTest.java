package com.example.springacademytask;

import com.example.springacademytask.constant.ErrorMessages;
import com.example.springacademytask.dto.ProductDto;
import com.example.springacademytask.exception.ProductNotFoundException;
import com.example.springacademytask.exception.ProductWithThisVendorCodeAlreadyExistsException;
import com.example.springacademytask.service.ProductService;
import com.example.springacademytask.util.ProductTestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.ArgumentMatchers.any;

@WebMvcTest
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateProduct() throws Exception {
        ProductDto product = ProductTestUtil.getRandomProduct();

        given(productService.createProduct(product)).willReturn(product);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(product.getName())))
                .andExpect(jsonPath("$.vendor_code", is(product.getVendorCode())))
                .andExpect(jsonPath("$.description", is(product.getDescription())))
                .andExpect(jsonPath("$.category", is(product.getCategory())))
                .andExpect(jsonPath("$.price", is(product.getPrice())))
                .andExpect(jsonPath("$.quantity", is(product.getQuantity())));

        given(productService.createProduct(any(ProductDto.class)))
                .willThrow(new ProductWithThisVendorCodeAlreadyExistsException());

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(jsonPath(
                        "$.message",
                        is(ErrorMessages.PRODUCT_WITH_THIS_VENDOR_CODE_ALREADY_EXISTS_EXCEPTION_MESSAGE)
                ));
    }

    @Test
    public void testCreateProductWithIncorrectName() throws Exception {
        mockMvc.perform(
                        post("/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(ProductTestUtil.getProductWithBlankName()))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").isArray())
                .andExpect(jsonPath("$.message", hasItem(ErrorMessages.NAME_NOT_BLANK_MESSAGE)));

        mockMvc.perform(
                        post("/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(ProductTestUtil.getProductWithNullName()))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").isArray())
                .andExpect(jsonPath("$.message", hasItem(ErrorMessages.NAME_NOT_NULL_MESSAGE)));

        mockMvc.perform(
                        post("/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(ProductTestUtil.getProductWithLongName()))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").isArray())
                .andExpect(jsonPath("$.message", hasItem(ErrorMessages.NAME_SIZE_MAX_MESSAGE)));
    }

    @Test
    public void testCreateProductWithIncorrectVendorCode() throws Exception {
        mockMvc.perform(
                        post("/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(ProductTestUtil.getProductWithBlankVendorCode()))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").isArray())
                .andExpect(jsonPath("$.message", hasItem(ErrorMessages.VENDOR_CODE_NOT_BLANK_MESSAGE)));

        mockMvc.perform(
                        post("/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(ProductTestUtil.getProductWithNullVendorCode()))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").isArray())
                .andExpect(jsonPath("$.message", hasItem(ErrorMessages.VENDOR_CODE_NOT_NULL_MESSAGE)));

        mockMvc.perform(
                        post("/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(ProductTestUtil.getProductWithLongVendorCode()))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").isArray())
                .andExpect(jsonPath("$.message", hasItem(ErrorMessages.VENDOR_CODE_SIZE_MAX_MESSAGE)));
    }

    @Test
    public void testCreateProductWithIncorrectCategory() throws Exception {
        mockMvc.perform(
                        post("/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(ProductTestUtil.getProductWithBlankCategory()))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").isArray())
                .andExpect(jsonPath("$.message", hasItem(ErrorMessages.CATEGORY_NOT_BLANK_MESSAGE)));

        mockMvc.perform(
                        post("/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(ProductTestUtil.getProductWithNullCategory()))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").isArray())
                .andExpect(jsonPath("$.message", hasItem(ErrorMessages.CATEGORY_NOT_NULL_MESSAGE)));

        mockMvc.perform(
                        post("/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(ProductTestUtil.getProductWithLongCategory()))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").isArray())
                .andExpect(jsonPath("$.message", hasItem(ErrorMessages.CATEGORY_SIZE_MAX_MESSAGE)));
    }

    @Test
    public void testCreateProductWithIncorrectDescription() throws Exception {
        mockMvc.perform(
                        post("/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(ProductTestUtil.getProductWithBlankDescription()))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").isArray())
                .andExpect(jsonPath("$.message", hasItem(ErrorMessages.DESCRIPTION_NOT_BLANK_MESSAGE)));

        mockMvc.perform(
                        post("/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(ProductTestUtil.getProductWithLongDescription()))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").isArray())
                .andExpect(jsonPath("$.message", hasItem(ErrorMessages.DESCRIPTION_SIZE_MAX_MESSAGE)));
    }

    @Test
    public void testCreateProductWithIncorrectPrice() throws Exception {
        mockMvc.perform(
                        post("/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(ProductTestUtil.getProductWithNullPrice()))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").isArray())
                .andExpect(jsonPath("$.message", hasItem(ErrorMessages.PRICE_NOT_NULL_MESSAGE)));

        mockMvc.perform(
                        post("/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(ProductTestUtil.getProductWithNegativePrice()))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").isArray())
                .andExpect(jsonPath("$.message", hasItem(ErrorMessages.PRICE_MIN_VALUE_MESSAGE)));
    }

    @Test
    public void testCreateProductWithIncorrectQuantity() throws Exception {
        mockMvc.perform(
                        post("/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(ProductTestUtil.getProductWithNullQuantity()))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").isArray())
                .andExpect(jsonPath("$.message", hasItem(ErrorMessages.QUANTITY_NOT_NULL_MESSAGE)));

        mockMvc.perform(
                        post("/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(ProductTestUtil.getProductWithNegativeQuantity()))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").isArray())
                .andExpect(jsonPath("$.message", hasItem(ErrorMessages.QUANTITY_MIN_VALUE_MESSAGE)));
    }

    @Test
    public void testGetAll() throws Exception {
        ProductDto product1 = ProductTestUtil.getRandomProduct();
        ProductDto product2 = ProductTestUtil.getRandomProduct();
        ProductDto product3 = ProductTestUtil.getRandomProduct();
        ProductDto product4 = ProductTestUtil.getRandomProduct();
        ProductDto product5 = ProductTestUtil.getRandomProduct();

        List<ProductDto> productDtoList = List.of(product1, product2, product3, product4, product5);

        given(productService.getAllProducts(null, null)).willReturn(productDtoList);

        mockMvc.perform(get("/products").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(productDtoList.size())));
    }

    @Test
    public void testGetById() throws Exception {
        UUID uuid = UUID.randomUUID();
        ProductDto product = ProductTestUtil.getRandomProduct();

        given(productService.getProductById(uuid)).willReturn(product);

        mockMvc.perform(get(String.format("/products/%s", uuid)).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(product.getName())))
                .andExpect(jsonPath("$.vendor_code", is(product.getVendorCode())))
                .andExpect(jsonPath("$.description", is(product.getDescription())))
                .andExpect(jsonPath("$.category", is(product.getCategory())))
                .andExpect(jsonPath("$.price", is(product.getPrice())))
                .andExpect(jsonPath("$.quantity", is(product.getQuantity())));

        given(productService.getProductById(uuid)).willThrow(new ProductNotFoundException(uuid));

        mockMvc.perform(get(String.format("/products/%s", uuid)).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(jsonPath(
                        "$.message",
                        is(String.format(ErrorMessages.PRODUCT_NOT_FOUND_EXCEPTION_MESSAGE, uuid))
                ));
    }

    @Test
    public void testUpdate() throws Exception {
        UUID uuid = UUID.randomUUID();
        ProductDto product = ProductTestUtil.getRandomProduct();

        given(productService.updateProduct(uuid, product)).willReturn(product);

        ResultActions response = mockMvc.perform(
                put(String.format("/products/%s", uuid))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(product.getName())))
                .andExpect(jsonPath("$.vendor_code", is(product.getVendorCode())))
                .andExpect(jsonPath("$.description", is(product.getDescription())))
                .andExpect(jsonPath("$.category", is(product.getCategory())))
                .andExpect(jsonPath("$.price", is(product.getPrice())))
                .andExpect(jsonPath("$.quantity", is(product.getQuantity())));

        given(productService.updateProduct(uuid, product)).willThrow(new ProductNotFoundException(uuid));

        mockMvc.perform(
                        put(String.format("/products/%s", uuid))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(product)))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(jsonPath(
                        "$.message",
                        is(String.format(ErrorMessages.PRODUCT_NOT_FOUND_EXCEPTION_MESSAGE, uuid))
                ));
    }

    @Test
    public void testDeleteAll() throws Exception {
        mockMvc.perform(delete("/products"))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(productService, times(1)).deleteAllProducts();
    }

    @Test
    public void testDeleteById() throws Exception {
        UUID uuid = UUID.randomUUID();

        mockMvc.perform(delete(String.format("/products/%s", uuid))).andDo(print());

        verify(productService, times(1)).deleteProductById(uuid);
    }
}
