package com.e2y.shopping.utils;

import com.e2y.shopping.dto.CartEntryDto;
import com.e2y.shopping.model.CartEntry;
import com.e2y.shopping.model.Product;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CartEntryMapper {

    public CartEntryDto toDto(CartEntry cartEntry){
        return new CartEntryDto(cartEntry.getQuantity(),
                Optional.ofNullable(cartEntry).map(CartEntry::getProduct).map(Product::getId).orElse(null)
        );
    }


}
