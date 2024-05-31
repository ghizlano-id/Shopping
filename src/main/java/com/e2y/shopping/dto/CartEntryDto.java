package com.e2y.shopping.dto;

public record CartEntryDto(
        int quantity,
        long productId
) {
}
