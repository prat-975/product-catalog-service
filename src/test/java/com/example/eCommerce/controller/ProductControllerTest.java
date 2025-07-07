package com.example.eCommerce.controller;

import com.example.eCommerce.model.Product;
import com.example.eCommerce.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void test_create_product() throws Exception {
        Product product = Product.builder()
                .id("1")
                .name("iPhone")
                .description("Smartphone")
                .category("Electronics")
                .price(new BigDecimal("999.99"))
                .build();

        when(productService.createProduct(any())).thenReturn(product);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("iPhone"));
    }

    @Test
    void test_get_product_by_id() throws Exception {
        Product product = Product.builder()
                .id("1")
                .name("iPhone")
                .description("Smartphone")
                .category("Electronics")
                .price(new BigDecimal("999.99"))
                .build();

        when(productService.getProductById("1")).thenReturn(product);

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.category").value("Electronics"));
    }

    @Test
    void test_get_all_products() throws Exception {
        when(productService.getAllProducts()).thenReturn(List.of(
                Product.builder()
                        .id("1")
                        .name("iPhone")
                        .description("Smartphone")
                        .category("Electronics")
                        .price(new BigDecimal("999.99"))
                        .build()
        ));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("iPhone"));
    }

    @Test
    void test_search_by_name() throws Exception {
        when(productService.searchByName("iphone")).thenReturn(List.of(
                Product.builder()
                        .id("1")
                        .name("iPhone")
                        .description("Smartphone")
                        .category("Electronics")
                        .price(new BigDecimal("999.99"))
                        .build()
        ));

        mockMvc.perform(get("/api/products/search?name=iphone"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("iPhone"));
    }

    @Test
    void test_filter_by_category() throws Exception {
        when(productService.filterByCategory("Electronics")).thenReturn(List.of(
                Product.builder()
                        .id("1")
                        .name("iPhone")
                        .description("Smartphone")
                        .category("Electronics")
                        .price(new BigDecimal("999.99"))
                        .build()
        ));

        mockMvc.perform(get("/api/products/filter/category?category=Electronics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].category").value("Electronics"));
    }

    @Test
    void test_filter_by_price_range() throws Exception {
        when(productService.filterByPriceRange(new BigDecimal("500.00"), new BigDecimal("1000.00")))
                .thenReturn(List.of(
                        Product.builder()
                                .id("1")
                                .name("iPhone")
                                .description("Smartphone")
                                .category("Electronics")
                                .price(new BigDecimal("999.99"))
                                .build()
                ));

        mockMvc.perform(get("/api/products/filter/price?min=500.00&max=1000.00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].price").value(999.99));
    }
}
