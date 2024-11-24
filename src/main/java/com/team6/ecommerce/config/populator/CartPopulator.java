package com.team6.ecommerce.config.populator;
/*
import com.team6.ecommerce.cart.Cart;
import com.team6.ecommerce.cart.CartRepository;
import com.team6.ecommerce.cartitem.CartItem;
import com.team6.ecommerce.product.Product;
import com.team6.ecommerce.product.ProductRepository;
import com.team6.ecommerce.user.User;
import com.team6.ecommerce.user.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@AllArgsConstructor
@Component
public class CartPopulator {

    private final UserRepository userRepo;
    private final ProductRepository productRepo;
    private final CartRepository cartRepo;

    @PostConstruct
    public void init() {

        log.info("[CartPopulator] Clearing Cart Collection.");
        cartRepo.deleteAll();

        User user1 = userRepo.findByEmail("customer1@example.com");
        User user2 = userRepo.findByEmail("customer2@example.com");
        User user3 = userRepo.findByEmail("customer3@example.com");
        User user4 = userRepo.findByEmail("fuat");

        // Populate carts for each user
        if (user1 != null) populateCartForUser(user1, List.of("1", "2"), List.of(2, 1));
        if (user2 != null) populateCartForUser(user2, List.of("3", "4"), List.of(7, 99));
        if (user3 != null) populateCartForUser(user3, List.of("5"), List.of(1));
        if (user4 != null) populateCartForUser(user4, List.of("2", "3", "4"), List.of(3, 2, 5));
    }

    private void populateCartForUser(User user, List<String> productIds, List<Integer> quantities) {
        List<CartItem> cartItems = new ArrayList<>();

        for (int i = 0; i < productIds.size(); i++) {
            String productId = productIds.get(i);
            int quantity = quantities.get(i);

            Optional<Product> productOpt = productRepo.findById(productId);
            if (productOpt.isPresent()) {
                CartItem cartItem = new CartItem(productOpt.get(), quantity);
                cartItems.add(cartItem);
            } else {
                log.warn("[CartPopulator] Product with ID {} not found!", productId);
            }
        }

        // Create cart and associate with user
        Cart cart = new Cart(user.getId(), cartItems);
        cartRepo.save(cart);

        log.info("[CartPopulator] Created cart with ID {} for user {}", cart.getId(), user.getEmail());

        user.setCartId(cart.getId());
        userRepo.save(user);

        log.info("[CartPopulator] Saved user with cartId {}: {}", cart.getId(), user.getEmail());
    }

}*/
