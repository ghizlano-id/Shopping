package com.e2y.shopping.utils;

import com.e2y.shopping.dto.CartDto;
import com.e2y.shopping.model.Cart;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.apache.commons.collections4.CollectionUtils;


import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CartMapper {

    private final CartEntryMapper cartEntryMapper;

    public CartDto toDto(Cart cart){
        return new CartDto(cart.getId(),
                CollectionUtils.emptyIfNull(cart.getEntries()).stream()
                        .map(cartEntryMapper::toDto)
                        .collect(Collectors.toList())
        );
    }
}
