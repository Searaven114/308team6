package com.team6.ecommerce.config.populator;

import com.team6.ecommerce.product.Product;
import com.team6.ecommerce.product.ProductRepository;
import com.team6.ecommerce.user.User;
import com.team6.ecommerce.user.UserRepository;
import com.team6.ecommerce.wishlist.Wishlist;
import com.team6.ecommerce.wishlist.WishlistRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;

import java.util.*;

@Log4j2
@AllArgsConstructor
@Component
public class WishlistPopulator {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final WishlistRepository wishlistRepository;

    @PostConstruct
    public void init() {
        log.info("[WishlistPopulator] Starting wishlist population.");

        // Clear existing wishlists
        wishlistRepository.deleteAll();
        log.info("[WishlistPopulator] Cleared Wishlist collection.");

        // Fetch users and products
        List<User> users = userRepository.findAll();
        List<Product> products = productRepository.findAll();

        if (users.isEmpty()) {
            log.warn("[WishlistPopulator] No users found. Population skipped.");
            return;
        }

        // Assign wishlists to all users
        Random random = new Random();

        for (User user : users) {
            if (user.getRoles().contains("ROLE_CUSTOMER")) { // Ensure only customers get wishlists
                Wishlist wishlist = new Wishlist();
                wishlist.setUserId(user.getId());

                // Select random products for the wishlist
                Set<String> productIds = new HashSet<>();
                if (!products.isEmpty()) {
                    int wishlistSize = random.nextInt(5) + 1; // Wishlist size between 1 and 5
                    for (int i = 0; i < wishlistSize; i++) {
                        Product randomProduct = products.get(random.nextInt(products.size()));
                        productIds.add(randomProduct.getId());
                    }
                }

                wishlist.setProductIds(productIds);
                wishlistRepository.save(wishlist);

                log.info("[WishlistPopulator] Wishlist with {} items created for user {}.", productIds.size(), user.getEmail());
            }
        }

        log.info("[WishlistPopulator] Wishlist population completed.");
    }
}
