package com.example.eCommerce.service;

import com.example.eCommerce.exception.ProductNotFoundException;
import com.example.eCommerce.model.Product;
import com.example.eCommerce.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        product = Product.builder()
                .id("abc123")
                .name("iPhone")
                .description("Smartphone")
                .category("Electronics")
                .price(799.99)
                .build();
    }

    @Test
    void test_create_product() {
        when(productRepository.save(any(Product.class))).thenReturn(product);
        Product saved = productService.createProduct(product);
        assertEquals("iPhone", saved.getName());
    }

    @Test
    void test_update_product_success() {
        when(productRepository.findById("abc123")).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product updated = productService.updateProduct("abc123", product);
        assertEquals("iPhone", updated.getName());
    }

    @Test
    void test_update_product_not_found() {
        when(productRepository.findById("abc123")).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> {
            productService.updateProduct("abc123", product);
        });
    }

    @Test
    void test_delete_product_success() {
        when(productRepository.findById("abc123")).thenReturn(Optional.of(product));
        doNothing().when(productRepository).deleteById("abc123");

        assertDoesNotThrow(() -> productService.deleteProduct("abc123"));
        verify(productRepository).deleteById("abc123");
    }

    @Test
    void test_delete_product_not_found() {
        when(productRepository.findById("abc123")).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct("abc123"));
    }

    @Test
    void test_get_product_by_id_success() {
        when(productRepository.findById("abc123")).thenReturn(Optional.of(product));
        Product found = productService.getProductById("abc123");
        assertEquals("iPhone", found.getName());
    }

    @Test
    void test_get_product_by_id_not_found() {
        when(productRepository.findById("abc123")).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> productService.getProductById("abc123"));
    }

    @Test
    void test_get_all_products() {
        when(productRepository.findAll()).thenReturn(List.of(product));
        List<Product> all = productService.getAllProducts();
        assertEquals(1, all.size());
    }

    @Test
    void test_search_by_name() {
        when(productRepository.findByNameContainingIgnoreCase("iphone")).thenReturn(List.of(product));
        List<Product> results = productService.searchByName("iphone");
        assertEquals(1, results.size());
    }

    @Test
    void test_filter_by_category() {
        when(productRepository.findByCategoryIgnoreCase("Electronics")).thenReturn(List.of(product));
        List<Product> results = productService.filterByCategory("Electronics");
        assertEquals(1, results.size());
    }

    @Test
    void test_filter_by_price_range() {
        double min = 500.00;
        double max = 1000.00;
        when(productRepository.findByPriceBetween(min, max)).thenReturn(Arrays.asList(product));

        List<Product> filtered = productService.filterByPriceRange(min, max);
        assertEquals(1, filtered.size());
    }
}
