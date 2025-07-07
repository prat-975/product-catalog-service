package com.example.eCommerce.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getById_beforeImplementation_shouldReturn404() throws Exception {
        // We have not yet implemented GET /product/{id}, so this should 404
        mockMvc.perform(get("/product/1"))
                .andExpect(status().isNotFound());
    }
}
