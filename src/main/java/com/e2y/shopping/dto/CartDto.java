package com.e2y.shopping.dto;

import java.util.List;

public record CartDto(
        long id,
        List<CartEntryDto> entries
) {
}
