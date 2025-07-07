package com.example.eCommerce.service;

import com.example.eCommerce.model.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    Product createProduct(Product product);
    Product updateProduct(String id, Product product);
    void deleteProduct(String id);
    Product getProductById(String id);
    List<Product> getAllProducts();
    List<Product> searchByName(String name);
    List<Product> filterByCategory(String category);
    List<Product> filterByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);
}
