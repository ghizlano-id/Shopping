package com.e2y.shopping.controller;

import com.e2y.shopping.dto.ProductDto;
import com.e2y.shopping.model.Product;
import com.e2y.shopping.repository.ProductRepository;
import com.e2y.shopping.service.ProductService;
import com.e2y.shopping.utils.ProductMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc
class ProductControllerTest {

    @MockBean
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void should_find_product() throws Exception {

        var productId = 1L;
        ProductDto productDto = new ProductDto(
                1L,
                "jean",
                "blue jean",
                159.99
        );

        when(productService.findProductById(productId))
                .thenReturn(productDto);

        mockMvc.perform(get("/api/v1/product/{id}",productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.name", Matchers.is("jean")))
                .andExpect(jsonPath("$.description", Matchers.is("blue jean")))
                .andExpect(jsonPath("$.price", Matchers.is(159.99)));
    }

    @Test
    void should_add_product() throws Exception {

        var productDtoRequest = new ProductDto(
                null,
                "jean",
                "blue jean",
                159.99
        );

        var productDtoResponse = new ProductDto(
                1L,
                "jean",
                "blue jean",
                159.99
        );

        when(productService.createProduct(productDtoRequest))
                .thenReturn(productDtoResponse);

        mockMvc.perform(post("/api/v1/product/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDtoRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.name", Matchers.is("jean")))
                .andExpect(jsonPath("$.description", Matchers.is("blue jean")))
                .andExpect(jsonPath("$.price", Matchers.is(159.99)));
    }



    @Test
    void updateProduct() throws Exception {

        var productDtoRequest = new ProductDto(
                1L,
                "jean",
                "red jean",
                159.99
        );

        var productDtoResponse = new ProductDto(
                1L,
                "jean",
                "blue jean",
                59.99
        );


        when(productService.updateProduct(productDtoRequest))
                .thenReturn(productDtoResponse);

        mockMvc.perform(put("/api/v1/product/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDtoRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.name", Matchers.is("jean")))
                .andExpect(jsonPath("$.description", Matchers.is("blue jean")))
                .andExpect(jsonPath("$.price", Matchers.is(59.99)));
    }

    @Test
    void deleteProduct() throws Exception {

        var productId = 1L;

        /*var product = new Product(
                1L,
                "jean",
                "blue jean",
                159.99,
                null
        );

        product = productRepository.save(product);*/


        mockMvc.perform(delete("/api/v1/product/{id}",productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
/*
        mockMvc.perform(get("/api/v1/product/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());*/
    }
}