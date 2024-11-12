package com.team6.ecommerce.cart;
import com.team6.ecommerce.cartitem.CartItem;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Log4j2
@AllArgsConstructor
public class CartService {

    private final CartRepository cartRepository;


    public List<CartItem> getCartItemsByUserId(String userId) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null || cart.getCartItems().isEmpty()) {

            return Collections.emptyList();
        }

        return cart.getCartItems();
    }
}