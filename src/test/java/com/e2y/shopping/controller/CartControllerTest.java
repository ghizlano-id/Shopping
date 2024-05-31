package com.e2y.shopping.controller;

import com.e2y.shopping.dto.CartDto;
import com.e2y.shopping.dto.CartEntryDto;
import com.e2y.shopping.model.Cart;
import com.e2y.shopping.repository.CartRepository;
import com.e2y.shopping.repository.ProductRepository;
import com.e2y.shopping.service.CartService;
import com.e2y.shopping.utils.CartMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(CartController.class)
@AutoConfigureMockMvc
class CartControllerTest {

    @MockBean
    private CartService cartService;
    @Mock
    private CartRepository cartRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private CartMapper cartMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void initCart() throws Exception {

        var cartDto = new CartDto(
                1L,
                null
        );

        when(cartService.initCart()).
                thenReturn(cartDto);

        mockMvc.perform(post("/api/v1/cart")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", Matchers.is(1)));
    }

    @Test
    void should_get_cart() throws Exception {

        var cartId = 1L;

        var cartEntryDto = new CartEntryDto(
                5,
                1
        );

        var cartDto = new CartDto(
                1L,
                Arrays.asList(cartEntryDto)
        );

        when(cartService.getCartById(cartId))
                .thenReturn(cartDto);

        mockMvc.perform(get("/api/v1/cart/{id}",cartId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.entries.length()").value(1));
    }

    @Test
    void should_add_to_cart() throws Exception {

        var cartId = 1L;
        var cartEntryDto = new CartEntryDto(
                5,
                1
        );

        var cartDto = new CartDto(
                1L,
                Arrays.asList(cartEntryDto)
        );

        when(cartService.addToCart(cartId,cartEntryDto))
                .thenReturn(cartDto);

        mockMvc.perform(put("/api/v1/cart/add/{cardId}",cartId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartEntryDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.entries.length()").value(1));
    }

    @Test
    void removeFromCart() {
    }

    @Test
    void emptyCart() {
    }
}