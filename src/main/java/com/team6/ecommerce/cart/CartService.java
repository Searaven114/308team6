package com.team6.ecommerce.cart;
import com.team6.ecommerce.cartitem.CartItem;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public List<CartItem> getCartItemsByUserId(String userId) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null || cart.getCartItems().isEmpty()) {
            return Collections.emptyList();
        }

        return cart.getCartItems();
    }
}