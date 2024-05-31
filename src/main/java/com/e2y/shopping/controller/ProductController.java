package com.e2y.shopping.controller;

import com.e2y.shopping.dto.ProductDto;
import com.e2y.shopping.model.Product;
import com.e2y.shopping.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<ProductDto> getProduct(@RequestParam(name = "page", defaultValue = "1") int page) {
        return productService.findAllProducts(page);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto addProduct(@RequestBody ProductDto productDto) {
        return productService.createProduct(productDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findProduct(@PathVariable("id") Long id) {
        try {
            ProductDto productDto = productService.findProductById(id);
            return ResponseEntity.ok(productDto);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateProduct(@RequestBody ProductDto productDto) {
        try {
            ProductDto updateProductDto = productService.updateProduct(productDto);
            return ResponseEntity.ok(updateProductDto);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long id){
        try {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();  // Return 204 No Content status
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}
