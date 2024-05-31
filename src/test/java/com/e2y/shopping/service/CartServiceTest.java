package com.e2y.shopping.service;

import com.e2y.shopping.dto.CartDto;
import com.e2y.shopping.dto.CartEntryDto;
import com.e2y.shopping.model.Cart;
import com.e2y.shopping.model.CartEntry;
import com.e2y.shopping.model.Product;
import com.e2y.shopping.repository.CartEntryRepository;
import com.e2y.shopping.repository.CartRepository;
import com.e2y.shopping.repository.ProductRepository;
import com.e2y.shopping.utils.CartMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CartServiceTest {

    @InjectMocks
    private CartService cartService;
    @Mock
    private CartRepository cartRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private  CartMapper cartMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void should_successfully_init_cart() {
        var cart = new Cart();
        var newCart = new Cart(
                1L,
                null
        );
        var cartDto = new CartDto(
                1L,
                null
        );


        when(cartRepository.save(cart))
                .thenReturn(newCart);
        when(cartMapper.toDto(newCart))
                .thenReturn(cartDto);

        CartDto createdCartDto = cartService.initCart();

        assertNotNull(createdCartDto);
        assertEquals(cartDto.id(),createdCartDto.id());
    }

    @Test
    void should_successfully_get_cart_by_id() {
        Long cartId = 1L;

        var cart = new Cart(
                1L,
                null
        );
        var cartDto = new CartDto(
                1L,
                null
        );

        when(cartRepository.findById(cartId))
                .thenReturn(Optional.ofNullable(cart));
        when(cartMapper.toDto(cart))
                .thenReturn(cartDto);
        CartDto cartDtoResponse = cartService.getCartById(cartId);

        assertNotNull(cartDtoResponse);
        assertEquals(cartDto.id(),cartDtoResponse.id());
    }

    @Test
    void should_throw_exception_when_find_product_by_id() {
        Long cartId = 1L;

        when(cartRepository.findById(cartId))
                .thenReturn(Optional.empty());

        //then
        assertThrows(IllegalArgumentException.class, () -> cartService.getCartById(cartId));
    }

    @Test
    void should_successfully_add_to_cart() {
        Long cartId = 1L;

        var cart = new Cart(
                1L,
                new ArrayList<>()
        );

        var product = new Product(
                1L,
                "jean",
                "red jean",
                79.99,
                null
        );

        var cartEntryDto = new CartEntryDto(
                5,
                1
        );

        var cartDto = new CartDto(
                1L,
                Arrays.asList(cartEntryDto)
        );

        when(cartRepository.findById(cartId))
                .thenReturn(Optional.of(cart));
        when(productRepository.findById(cartEntryDto.productId()))
                .thenReturn(Optional.of(product));
        when(cartMapper.toDto(cart))
                .thenReturn(cartDto);

        CartDto cartDtoResponse = cartService.addToCart(cartId, cartEntryDto);

        assertNotNull(cartDtoResponse);
        assertEquals(cartDto.id(),cartDtoResponse.id());
        assertNotNull(cartDtoResponse.entries());
        assertEquals(cartDto.entries().size(),cartDtoResponse.entries().size());
        assertEquals(cartDto.entries(),cartDtoResponse.entries());
    }

    @Test
    void should_successfully_remove_from_cart() {

        Long cartId = 1L;

        var product1 = new Product(
                1L,
                "jean",
                "red jean",
                79.99,
                null
        );
        var product2 = new Product(
                2L,
                "jean",
                "clack jean",
                149.99,
                null
        );
        var cartEntry1 = new CartEntry(
                1L,
                1,
                product1,
                null
        );
        var cartEntry2 = new CartEntry(
                2L,
                2,
                product2,
                null
        );
        var entries = new ArrayList<>(Arrays.asList(cartEntry1,cartEntry2));
        var cart = new Cart(
                1L,
                entries
        );
        var entries2 = Arrays.asList(cartEntry2);
        var updatedCart = new Cart(
                1L,
                entries2
        );

        var cartEntryDto = new CartEntryDto(
                0,
                1L
        );

        var cartEntryDto2 = new CartEntryDto(
                2,
                2L
        );

        var cartDto = new CartDto(
                1L,
                Arrays.asList(cartEntryDto2)
        );

        when(cartRepository.findById(cartId))
                .thenReturn(Optional.of(cart));
        when(cartMapper.toDto(updatedCart))
                .thenReturn(cartDto);

        CartDto updatedCartDto = cartService.removeFromCart(cartId,cartEntryDto);

        assertNotNull(updatedCartDto);
        assertEquals(cartDto.id(),updatedCartDto.id());
        assertEquals(cartDto.entries(),updatedCartDto.entries());
    }

    @Test
    void should_successfully_empty_cart() {

        Long cartId = 1L;

        var product = new Product(
                1L,
                "jean",
                "red jean",
                79.99,
                null
        );
        var cartEntry = new CartEntry(
                1L,
                2,
                product,
                null
        );
        var entries = new ArrayList<CartEntry>(Arrays.asList(cartEntry));
        var cart = new Cart(
                1L,
                entries
        );
        var emptyCart = new Cart(
                1L,
                Collections.emptyList()
        );

        var cartDto = new CartDto(
                1L,
                Collections.emptyList()
        );

        when(cartRepository.findById(cartId))
                .thenReturn(Optional.of(cart));
        when(cartRepository.save(cart))
                .thenReturn(emptyCart);
        when(cartMapper.toDto(emptyCart))
                .thenReturn(cartDto);

        CartDto emptyCartDto = cartService.emptyCart(cartId);

        assertNotNull(emptyCartDto);
        assertEquals(cartDto.id(),emptyCartDto.id());
        assertEquals(0,emptyCartDto.entries().size());

    }
}