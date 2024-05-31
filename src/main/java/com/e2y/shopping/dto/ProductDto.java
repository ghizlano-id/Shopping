package com.e2y.shopping.dto;


public record ProductDto(
        Long id,
        String name,
        String description,
        double price
) {
}
