package com.e2y.shopping.service;

import com.e2y.shopping.dto.ProductDto;
import com.e2y.shopping.model.Product;
import com.e2y.shopping.repository.ProductRepository;
import com.e2y.shopping.utils.ProductMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {

    private final static int PAGE_SIZE = 10;

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductDto createProduct(ProductDto productDto) {
        var product = productMapper.fromDto(productDto);
        var savedProduct = productRepository.save(product);
        return productMapper.toDto(savedProduct);
    }

    public ProductDto findProductById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
    }

    public List<ProductDto> findAllProducts(int page) {
        page = page < 0 ? 0 : page - 1;
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return productRepository.findAll(pageable).stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    public ProductDto updateProduct(ProductDto productDto) {
        var product = productRepository.findById(productDto.id())
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productDto.id()));
        product.setName(productDto.name());
        product.setDescription(productDto.description());
        product.setPrice(productDto.price());
        var updatedProduct = productRepository.save(product);

        return productMapper.toDto(updatedProduct);


    }

    public void deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Product not found with id: " + id);
        }
    }
}
