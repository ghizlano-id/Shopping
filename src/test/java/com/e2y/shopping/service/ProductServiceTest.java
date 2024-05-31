package com.e2y.shopping.service;

import com.e2y.shopping.dto.ProductDto;
import com.e2y.shopping.model.Product;
import com.e2y.shopping.repository.ProductRepository;
import com.e2y.shopping.utils.ProductMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void should_successfully_create_product() {

        //Given
        ProductDto productDto = new ProductDto(
                null,
                "jean",
                "blue jean",
                159.99
        );
        Product product = new Product(
                null,
                "jean",
                "blue jean",
                159.99,
                null
        );
        Product savedProduct = new Product(
                1L,
                "jean",
                "blue jean",
                159.99,
                null
        );

        ProductDto savedProductDto = new ProductDto(
                1L,
                "jean",
                "blue jean",
                159.99
        );



        when(productMapper.fromDto(productDto))
                .thenReturn(product);
        when(productRepository.save(product))
                .thenReturn(savedProduct);
        when(productMapper.toDto(savedProduct))
                .thenReturn(savedProductDto);

        //when
        ProductDto productDtoResponse = productService.createProduct(productDto);

        //Then
        assertNotNull(productDtoResponse);
        assertEquals(1,productDtoResponse.id());
        assertEquals(productDto.name(), productDtoResponse.name());
        assertEquals(productDto.description(), productDtoResponse.description());
        assertEquals(productDto.price(), productDtoResponse.price());

    }

    @Test
    void should_successfully_find_product_by_id() {
        //Given
        Product product = new Product(
                1L,
                "jean",
                "blue jean",
                159.99,
                null
        );
        ProductDto productDto = new ProductDto(
                1L,
                "jean",
                "blue jean",
                159.99
        );

        when(productRepository.findById(1L))
                .thenReturn(Optional.ofNullable(product));
        when(productMapper.toDto(product)).thenReturn(productDto);

        //when
        ProductDto productDtoResponse = productService.findProductById(1L);

        //then
        assertNotNull(productDtoResponse);
        assertEquals(productDto.id(),productDtoResponse.id());
        assertEquals(productDto.name(), productDtoResponse.name());
        assertEquals(productDto.description(), productDtoResponse.description());
        assertEquals(productDto.price(), productDtoResponse.price());

    }

    @Test
    void should_throw_exception_when_find_product_by_id() {

        when(productRepository.findById(1L))
                .thenReturn(Optional.empty());

        //then
        assertThrows(IllegalArgumentException.class, () -> productService.findProductById(1L));
    }

    @Test
    void should_successfully_find_all() {
        //Given
        int page = 1;

        Product product1 = new Product(
                1L,
                "jean",
                "blue jean",
                159.99,
                null
        );
        Product product2 =new Product(
                2L,
                "jean",
                "red jean",
                199.99,
                null
        );
        var products = Arrays.asList(product1,product2);
        ProductDto productDto1 = new ProductDto(
                1L,
                "jean",
                "blue jean",
                159.99
        );
        ProductDto productDto2 =new ProductDto(
                2L,
                "jean",
                "red jean",
                199.99
        );

        var productsDto = Arrays.asList(productDto1, productDto2);

        var pageable = PageRequest.of(page-1,10);


        when(productRepository.findAll(pageable))
                .thenReturn(new PageImpl<>(products));
        when(productMapper.toDto(product1))
                .thenReturn(productDto1);
        when(productMapper.toDto(product2))
                .thenReturn(productDto2);
        //when
        List<ProductDto> productsDtoResponse = productService.findAllProducts(page);

        //Then
        assertNotNull(productsDtoResponse);
        assertEquals(productsDto.size(),productsDtoResponse.size());
        assertEquals(productsDto, productsDtoResponse);
    }

    @Test
    void should_successfully_update_product() {

        ProductDto productDto = new ProductDto(
                1L,
                "jean",
                "red jean",
                99.99
        );

        Product product = new Product(
                1L,
                "jean",
                "blue jean",
                159.99,
                null
        );

        Product updatedProduct = new Product(
                1L,
                "jean",
                "red jean",
                99.99,
                null
        );

        ProductDto updatedProductDto = new ProductDto(
                1L,
                "jean",
                "red jean",
                99.99
        );

        when(productRepository.findById(productDto.id()))
                .thenReturn(Optional.ofNullable(product));
        when(productRepository.save(product)).
                thenReturn(updatedProduct);
        when(productMapper.toDto(updatedProduct)).thenReturn(updatedProductDto);
        //when
        ProductDto productDtoResponse = productService.updateProduct(productDto);

        //then
        assertNotNull(productDtoResponse);
        assertEquals(productDto.id(),productDtoResponse.id());
        assertEquals(productDto.name(), productDtoResponse.name());
        assertEquals(productDto.description(), productDtoResponse.description());
        assertEquals(productDto.price(), productDtoResponse.price());
    }

    @Test
    void should_throw_exception_when_trying_update_product() {

        ProductDto productDto = new ProductDto(
                1L,
                "jean",
                "red jean",
                99.99
        );

        when(productRepository.findById(1L))
                .thenReturn(Optional.empty());

        //then
        assertThrows(IllegalArgumentException.class, () -> productService.updateProduct(productDto));
    }

    @Test
    void should_successfully_delete_product() {

        Long productId = 1L;
        when(productRepository.existsById(productId)).thenReturn(true);

        // When
        productService.deleteProduct(productId);

        // Then
        verify(productRepository, times(1)).deleteById(productId);

    }

    @Test
    void should_throw_exception_when_trying_delete_product() {

        Long productId = 1L;
        when(productRepository.existsById(productId)).thenReturn(false);


        // Then
        assertThrows(IllegalArgumentException.class, () -> productService.deleteProduct(productId));

    }
}