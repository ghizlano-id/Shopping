package com.e2y.shopping.utils;

import com.e2y.shopping.dto.ProductDto;
import com.e2y.shopping.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product fromDto(ProductDto productDto){
        return Product.builder()
                .name(productDto.name())
                .description(productDto.description())
                .price(productDto.price())
                .build();
    }

    public ProductDto toDto(Product product){
        return new ProductDto(product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice());
    }
}
