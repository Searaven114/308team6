package com.team6.ecommerce.cart;

import com.team6.ecommerce.cartitem.CartItem;
import com.team6.ecommerce.constants.Strings;
import com.team6.ecommerce.product.Product;
import com.team6.ecommerce.product.ProductRepository;
import com.team6.ecommerce.user.UserRepository;
import com.team6.ecommerce.user.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.*;

@Log4j2
@AllArgsConstructor
@Service
public class CartService {

    private final UserService userService;
    private final UserRepository userRepo;
    private final ProductRepository productRepo;
    private final HttpSession session;
    private final CartRepository cartRepo;

    private void recalculateTotalPrice(Cart cart) {
        double totalPrice = cart.getCartItems().stream()
                .mapToDouble(item -> item.getProduct().getBasePrice() * item.getQuantity())
                .sum();
        cart.setTotalPrice(totalPrice);
    }


    /*
     *  - Retreive Cart
     *  - Add product to cart
     *  - Remove product from cart
     *  - Clear the cart
     *  - Checkout? yada order -> checkoutta mesela addressi olması lazım tamamlanması icin, address yoksa kullanıcıya bildirim gitmeli adresin yok diye.
     *  - (ileri seviye) Check Stock Availability -> cart'a ekledigi urunlere 4 ay sonra gelip baktıgında illa stok durumu farklı olacaktır, belirli bir işlem/sürede tetiklenen bu fonksiyon,
     *       cartta stogu bitmis urun olunca carrtan otomatik olarak çıkartmalı o ürünü ve (We have removed x, y and z due to no stock available) gibi bir mesaj döndürmeli.
     *
     */


    public List<CartItem> getCartItemsByUserId(String userId) {
        Cart cart = cartRepo.findByUserId(userId);
        if (cart == null || cart.getCartItems().isEmpty()) {
            log.info("[CartService] RETURNING EMPTY CART");
            return Collections.emptyList();
        }

        return cart.getCartItems();
    }


    public String addItemToUserCart(String userId, String productId, int quantity) {

        Optional<Product> productOpt = productRepo.findById(productId);

        // Product presence checking
        if (productOpt.isEmpty()) {
            return Strings.PRODUCT_NOT_FOUND;
        }

        Product product = productOpt.get();

        // Stock checking
        if (product.getQuantityInStock() == 0) {
            return Strings.PRODUCT_OUT_OF_STOCK;
        }


        // Retrieve the user's corresponding cart, if there is none, create the user's cart
        Cart cart = cartRepo.findByUserId(userId);

        if (cart == null) {
            cart = new Cart();
            cart.setUserId(userId);
        }


        boolean isCurrentTotalAmountInCartMoreThanAvailable = false;

        Optional<CartItem> existingCartItemOpt = cart.getCartItems().stream().filter( item -> item.getProduct().getId().equals(productId)).findFirst();
        if (existingCartItemOpt.isPresent()) {
            int existingAmount = existingCartItemOpt.get().getQuantity();

            if (existingAmount + quantity > product.getQuantityInStock()){
                isCurrentTotalAmountInCartMoreThanAvailable = true;
            }
        }


        // Requested amount checking (if a single request exceeds the whole stock i.e 40 left but request asked for 42)
        if (product.getQuantityInStock() < quantity) {
            return String.format(Strings.PRODUCT_NOT_AVAILABLE_IN_REQUESTED_QUANTITY, product.getQuantityInStock());
        }

        //Requested amount checking (if, after this request, the total amounf of a certain product in users cart exceeds that products left stock i.e product stock: 40, cart: 39, user wants to add 3 more)
        if (isCurrentTotalAmountInCartMoreThanAvailable) {
            return String.format(Strings.PRODUCT_NOT_AVAILABLE_IN_REQUESTED_QUANTITY, product.getQuantityInStock());
        }

        // Add the item to the cart
        CartItem cartItem = new CartItem(product, quantity);
        cart.getCartItems().add(cartItem);

        // Recalculate total price of the cart
        recalculateTotalPrice(cart);
        cartRepo.save(cart);

        log.info("[CartService] User with ID {} added {} units of '{}' to their cart.", userId, quantity, product.getTitle());

        return Strings.PRODUCT_ADDED_TO_CART;
    }


}




// Merge the session cart with the user's cart
//    private void mergeCarts(Cart sessionCart, Cart userCart) {
//
//        for (CartItem sessionCartItem : sessionCart.getCartItems()) {
//            boolean productExists = false;
//
//            // Check if the session cart item already exists in the user's cart
//            for (CartItem userCartItem : userCart.getCartItems()) {
//                if (Objects.equals(userCartItem.getProduct().getId(), sessionCartItem.getProduct().getId())) {
//                    // Increase the quantity of the existing product in the user's cart
//                    userCartItem.setQuantity(userCartItem.getQuantity() + sessionCartItem.getQuantity());
//                    cartItemRepository.save(userCartItem);
//                    productExists = true;
//                    break;
//                }
//            }
//
//            // If the product doesn't exist in the user's cart, add it
//            if (!productExists) {
//                CartItem newItem = CartItem.builder()
//                        .product(sessionCartItem.getProduct())
//                        .quantity(sessionCartItem.getQuantity())
//                        .build();
//                userCart.getCartItems().add(newItem);
//                cartItemRepository.save(newItem);
//            }
//        }
//
//        // Save the updated user's cart
//        cartRepository.save(userCart);
//
//        // Clear the session cart
//        sessionCart.getCartItems().clear();
//        cartRepository.save(sessionCart);
//    }




