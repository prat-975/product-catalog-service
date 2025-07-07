package com.example.eCommerce.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String id) {
        super("Product not found with id: " + id);
    }

    public ProductNotFoundException(String id, Throwable cause) {
        super("Product not found with id: " + id, cause);
    }
}
