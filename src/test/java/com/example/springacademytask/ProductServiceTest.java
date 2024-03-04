package com.example.springacademytask;

import com.example.springacademytask.dto.ProductDto;
import com.example.springacademytask.exception.ProductNotFoundException;
import com.example.springacademytask.exception.ProductWithThisVendorCodeAlreadyExistsException;
import com.example.springacademytask.repository.ProductRepository;
import com.example.springacademytask.service.ProductService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest()
@Import(ProductService.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductServiceTest {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    private TestData testData;

    @BeforeAll
    @Rollback(false)
    void beforeAllTests() {
        testData = new TestData();
    }

    @Test
    public void testCreateProduct() throws ProductWithThisVendorCodeAlreadyExistsException {
        // create the product and check that it was saved successfully
        ProductDto product1 =
                new ProductDto("Product 1", "1234", "", "Furniture", 99.7, 90);
        assertThat(productService.createProduct(product1)).isEqualTo(product1);

        // check that an exception is thrown when saving a record with the same vendor code
        ProductDto product2 =
                new ProductDto("Product 2", "1234", "", "Furniture", 99.7, 90);
        assertThrows(
                ProductWithThisVendorCodeAlreadyExistsException.class, () -> productService.createProduct(product2));
    }

    @Test
    public void testGetAllProducts() {
        // check that all products have been received
        assertThat(productService.getAllProducts().size()).isEqualTo(productRepository.count());
        assertThat(productService.getAllProducts(null, null).size()).isEqualTo(productRepository.count());

        // check that products are received by product name
        assertThat(productService.getAllProducts("Product", null).size())
                .isEqualTo(productRepository.countByNameContainsIgnoreCase("Product"));

        // check that products are received by product name and category
        assertThat(productService.getAllProducts("Product", "Food").size())
                .isEqualTo(productRepository.countByNameContainsIgnoreCaseAndCategoryContainsIgnoreCase("Product", "Food"));

        // check that products are received by category
        assertThat(productService.getAllProducts(null, "Accessories").size())
                .isEqualTo(productRepository.countByCategoryContainsIgnoreCase("Accessories"));
    }

    @Test
    public void testGetProductById() throws ProductNotFoundException {
        ProductDto testProduct = testData.getProduct1();
        ProductDto serviceProduct = productService.getProductById(
                productRepository.findIdByVendorCode(testProduct.getVendorCode()));
        assertThat(serviceProduct).isEqualTo(testProduct);

        // check for throwing an exception when receiving a product by a non-existent id
        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(generateRandomUUID()));
    }

    @Test
    public void testUpdateProduct() throws ProductNotFoundException {
        UUID id = productRepository.findIdByVendorCode(testData.getProduct2().getVendorCode());
        ProductDto newProduct2 =
                new ProductDto("Product 2", "product2", "", "Food", 35.66, 500);
        ProductDto productDto = productService.updateProduct(id, newProduct2);
        assertThat(productDto.getName()).isEqualTo(newProduct2.getName());
        assertThat(productDto.getVendorCode()).isEqualTo(newProduct2.getVendorCode());
        assertThat(productDto.getDescription()).isEqualTo(newProduct2.getDescription());
        assertThat(productDto.getCategory()).isEqualTo(newProduct2.getCategory());
        assertThat(productDto.getPrice()).isEqualTo(newProduct2.getPrice());
        assertThat(productDto.getQuantity()).isEqualTo(newProduct2.getQuantity());
        assertThat(productRepository.findByVendorCode(newProduct2.getVendorCode()).getQuantityUpdateDate()).isNotNull();

        // check for throwing an exception when updating a non-existent product
        assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(generateRandomUUID(), newProduct2));
    }

    @Test
    public void testDeleteAllProducts() {
        List<ProductDto> productDtoList1 = productService.getAllProducts();
        assertThat(productDtoList1.size()).isGreaterThan(0);
        productService.deleteAllProducts();
        List<ProductDto> productDtoList2 = productService.getAllProducts();
        assertThat(productDtoList2.size()).isEqualTo(0);
    }

    @Test
    public void testDeleteProductById() {
        UUID id = productRepository.findIdByVendorCode(testData.getProduct3().getVendorCode());

        // check that the product with the given id exists
        assertDoesNotThrow(() -> productService.getProductById(id));

        // delete this product
        productService.deleteProductById(id);

        // check that the product with the given id no longer exists
        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(id));

        // check that it is possible to delete a product with a non-existent id
        assertDoesNotThrow(() -> productService.deleteProductById(generateRandomUUID()));
    }

    // generate an id that will not intersect with the ids of all records in the database
    private UUID generateRandomUUID() {
        UUID uuid = UUID.randomUUID();
        while (productRepository.existsById(uuid)) {
            uuid = UUID.randomUUID();
        }
        return uuid;
    }

    // class for initializing test data and filling the database with test data
    public class TestData {
        private ProductDto product1;
        private ProductDto product2;
        private ProductDto product3;
        private ProductDto product4;
        private ProductDto product5;
        private ProductDto product6;
        private ProductDto product7;
        private ProductDto product8;
        private ProductDto product9;
        private ProductDto product10;

        public TestData() {
            createTestData();
        }

        private void createTestData() {
            product1 = new ProductDto(
                    "Product 1", "prod1", "", "Food", 11.7, 200);
            product2 = new ProductDto(
                    "Product 2", "prod2", "", "Food", 21.4, 200);
            product3 = new ProductDto(
                    "Product 3", "prod3", "", "Food", 8.7, 200);
            product4 = new ProductDto(
                    "Product 4", "prod4", "", "Furniture", 221.0, 200);
            product5 = new ProductDto(
                    "Product 5", "prod5", "", "Furniture", 34.9, 200);
            product6 = new ProductDto(
                    "Product 6", "prod6", "", "Clothing", 55.2, 200);
            product7 = new ProductDto(
                    "Product 7", "prod7", "", "Clothing", 19.3, 200);
            product8 = new ProductDto(
                    "Product 8", "prod8", "", "Accessories", 48.7, 200);
            product9 = new ProductDto(
                    "Product 9", "prod9", "", "Accessories", 22.3, 200);
            product10 = new ProductDto(
                    "Product 10", "prod10", "", "Accessories", 111.0, 200);
            try {
                productService.createProduct(product1);
                productService.createProduct(product2);
                productService.createProduct(product3);
                productService.createProduct(product4);
                productService.createProduct(product5);
                productService.createProduct(product6);
                productService.createProduct(product7);
                productService.createProduct(product8);
                productService.createProduct(product9);
                productService.createProduct(product10);
            } catch (ProductWithThisVendorCodeAlreadyExistsException e) {
                throw new RuntimeException(e);
            }
        }

        public ProductDto getProduct1() {
            return product1;
        }

        public ProductDto getProduct2() {
            return product2;
        }

        public ProductDto getProduct3() {
            return product3;
        }

        public ProductDto getProduct4() {
            return product4;
        }

        public ProductDto getProduct5() {
            return product5;
        }

        public ProductDto getProduct6() {
            return product6;
        }

        public ProductDto getProduct7() {
            return product7;
        }

        public ProductDto getProduct8() {
            return product8;
        }

        public ProductDto getProduct9() {
            return product9;
        }

        public ProductDto getProduct10() {
            return product10;
        }
    }
}
