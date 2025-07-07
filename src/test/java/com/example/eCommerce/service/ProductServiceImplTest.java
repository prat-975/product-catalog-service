package com.example.eCommerce.service;

import com.example.eCommerce.model.Product;
import com.example.eCommerce.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductServiceImpl service;

    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        product = new Product("1", "iPhone", "Latest model", "Electronics", 999.99);
    }

    @Test
    void testCreateProduct() {
        when(repository.save(product)).thenReturn(product);
        Product saved = service.createProduct(product);
        assertEquals("iPhone", saved.getName());
    }

    @Test
    void testGetProductById() {
        when(repository.findById("1")).thenReturn(Optional.of(product));
        Product found = service.getProductById("1");
        assertEquals("Electronics", found.getCategory());
    }

    @Test
    void testUpdateProduct() {
        Product updated = new Product("1", "iPhone 15", "Updated", "Electronics", 1099.99);
        when(repository.findById("1")).thenReturn(Optional.of(product));
        when(repository.save(any(Product.class))).thenReturn(updated);

        Product result = service.updateProduct("1", updated);
        assertEquals("iPhone 15", result.getName());
        assertEquals(1099.99, result.getPrice());
    }

    @Test
    void testDeleteProduct() {
        service.deleteProduct("1");
        verify(repository, times(1)).deleteById("1");
    }

    @Test
    void testGetAllProducts() {
        when(repository.findAll()).thenReturn(List.of(product));
        List<Product> result = service.getAllProducts();
        assertEquals(1, result.size());
    }

    @Test
    void testSearchByName() {
        when(repository.findByNameContainingIgnoreCase("phone")).thenReturn(List.of(product));
        List<Product> result = service.searchByName("phone");
        assertFalse(result.isEmpty());
    }

    @Test
    void testFilterByCategory() {
        when(repository.findByCategoryIgnoreCase("Electronics")).thenReturn(List.of(product));
        List<Product> result = service.filterByCategory("Electronics");
        assertEquals(1, result.size());
    }

    @Test
    void testFilterByPriceRange() {
        when(repository.findByPriceBetween(900.0, 1000.0)).thenReturn(List.of(product));
        List<Product> result = service.filterByPriceRange(900.0, 1000.0);
        assertEquals(1, result.size());
    }
}
