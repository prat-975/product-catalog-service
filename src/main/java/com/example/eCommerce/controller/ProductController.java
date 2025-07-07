package com.example.eCommerce.controller;

import com.example.eCommerce.model.Product;
import com.example.eCommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for handling Product CRUD and search/filter operations.
 */
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable String id) {
        return productService.getProductById(id);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable String id, @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/search")
    public List<Product> searchByName(@RequestParam String name) {
        return productService.searchByName(name);
    }

    @GetMapping("/filter/category")
    public List<Product> filterByCategory(@RequestParam String category) {
        return productService.filterByCategory(category);
    }

    @GetMapping("/filter/price")
    public List<Product> filterByPriceRange(@RequestParam double min,
                                            @RequestParam double max) {
        return productService.filterByPriceRange(min, max);
    }
}
