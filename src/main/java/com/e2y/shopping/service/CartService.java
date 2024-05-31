package com.e2y.shopping.service;

import com.e2y.shopping.dto.CartDto;
import com.e2y.shopping.dto.CartEntryDto;
import com.e2y.shopping.model.Cart;
import com.e2y.shopping.model.CartEntry;
import com.e2y.shopping.model.Product;
import com.e2y.shopping.repository.CartEntryRepository;
import com.e2y.shopping.repository.CartRepository;
import com.e2y.shopping.repository.ProductRepository;
import com.e2y.shopping.utils.CartMapper;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Predicate;

@Service
@AllArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartEntryRepository cartEntryRepository;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;

    public CartDto initCart() {
        var cart = new Cart();
        return cartMapper.toDto(cartRepository.save(cart));
    }

    public CartDto getCartById(Long id) {
        var cart = cartRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found with id" + id));
        return cartMapper.toDto(cart);
    }

    public CartDto addToCart(long cartId, CartEntryDto cartEntryDto) {
        var cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found with id" + cartId));
        getCartEntryForProduct(cartEntryDto.productId(), cart).ifPresentOrElse(cartEntry -> updateQuantity(cart,cartEntry, cartEntryDto.quantity()),
                        () -> addNewProduct(cart, cartEntryDto));

        return cartMapper.toDto(cart);
    }

    private Optional<CartEntry> getCartEntryForProduct(long productId, Cart cart) {
        return CollectionUtils.emptyIfNull(cart.getEntries()).stream()
                .filter(cartEntry -> hasProduct(cartEntry, productId))
                .findFirst();
    }

    private boolean hasProduct(CartEntry cartEntry, long productId) {
        return Optional.ofNullable(cartEntry.getProduct())
                .map(Product::getId)
                .filter(Predicate.isEqual(productId))
                .isPresent();
    }

    private void updateQuantity(Cart cart, CartEntry cartEntry, int quantity) {
        if (quantity == 0) {
            cartEntryRepository.delete(cartEntry);
            cart.getEntries().remove(cartEntry);
            cartRepository.save(cart);
        }
        else {
            cartEntry.setQuantity(cartEntry.getQuantity() + quantity);
        }
    }

    private void addNewProduct(Cart cart, CartEntryDto cartEntryDto) {
        long productId = cartEntryDto.productId();
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productId));
        CartEntry cartEntry = new CartEntry();
        cartEntry.setQuantity(cartEntryDto.quantity());
        cartEntry.setCart(cart);
        cartEntry.setProduct(product);

        cart.getEntries().add(cartEntry);

        cartRepository.save(cart);

    }

    public CartDto removeFromCart(long cartId, CartEntryDto cartEntryDto) {
        var cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found with id" + cartId));
        getCartEntryForProduct(cartEntryDto.productId(), cart).ifPresent(cartEntry -> updateQuantity(cart, cartEntry, 0));

        return cartMapper.toDto(cart);
    }

    public CartDto emptyCart(long cartId) {
        var cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found with id" + cartId));
        cart.getEntries().clear();
        var emptyCart = cartRepository.save(cart);

        return cartMapper.toDto(emptyCart);
    }
}
