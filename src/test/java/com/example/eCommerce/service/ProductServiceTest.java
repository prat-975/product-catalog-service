package com.example.eCommerce.service;

import com.example.eCommerce.model.Product;
import com.example.eCommerce.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    private ProductRepository repository;
    private ProductServiceImpl service;

    @BeforeEach
    void setup() {
        repository = Mockito.mock(ProductRepository.class);
        service = new ProductServiceImpl(repository);
    }

    @Test
    void testCreateProduct() {
        Product product = new Product(null, "TV", "Smart TV", "Electronics", 45000);
        Product saved = new Product("1", "TV", "Smart TV", "Electronics", 45000);
        when(repository.save(product)).thenReturn(saved);
        Product result = service.createProduct(product);
        assertEquals("TV", result.getName());
        assertNotNull(result.getId());
    }

    @Test
    void testGetById() {
        Product product = new Product("1", "TV", "Smart TV", "Electronics", 45000);
        when(repository.findById("1")).thenReturn(Optional.of(product));
        Product result = service.getProductById("1");
        assertEquals("TV", result.getName());
    }

    @Test
    void testUpdateProduct() {
        Product existing = new Product("1", "TV", "Smart TV", "Electronics", 45000);
        Product updated = new Product("1", "TV Pro", "4K Smart TV", "Electronics", 55000);
        when(repository.findById("1")).thenReturn(Optional.of(existing));
        when(repository.save(any(Product.class))).thenReturn(updated);
        Product result = service.updateProduct("1", updated);
        assertEquals("TV Pro", result.getName());
    }

    @Test
    void testDeleteProduct() {
        doNothing().when(repository).deleteById("1");
        service.deleteProduct("1");
        verify(repository, times(1)).deleteById("1");
    }
}
