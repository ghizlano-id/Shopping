package com.e2y.shopping.controller;

import com.e2y.shopping.dto.CartDto;
import com.e2y.shopping.dto.CartEntryDto;
import com.e2y.shopping.dto.ProductDto;
import com.e2y.shopping.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
@AllArgsConstructor
public class CartController {

    private CartService cartService;

    @PostMapping
    public ResponseEntity<CartDto> initCart(){
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.initCart());
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getCart(@PathVariable Long id){
        try {
            CartDto cartDto = cartService.getCartById(id);
            return ResponseEntity.ok(cartDto);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PutMapping("/add/{cartId}")
    public ResponseEntity<?> addToCart(@PathVariable long cartId, @RequestBody CartEntryDto cartEntryDto){
        try {
            CartDto cartDto = cartService.addToCart(cartId,cartEntryDto);
            return ResponseEntity.ok(cartDto);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<?> removeFromCart(@PathVariable long cartId, @RequestBody CartEntryDto cartEntryDto){
        try {
            CartDto cartDto = cartService.removeFromCart(cartId,cartEntryDto);
            return ResponseEntity.ok(cartDto);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
    @PutMapping("/{cartId}")
    public ResponseEntity<?> emptyCart(@PathVariable long cartId){
        try {
            CartDto cartDto = cartService.emptyCart(cartId);
            return ResponseEntity.ok(cartDto);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}
