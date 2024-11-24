package com.team6.ecommerce.cart;

import com.team6.ecommerce.cartitem.CartItem;
import com.team6.ecommerce.constants.Strings;
import com.team6.ecommerce.exception.UserNotFoundException;
import com.team6.ecommerce.product.Product;
import com.team6.ecommerce.product.ProductRepository;
import com.team6.ecommerce.user.User;
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
                .filter(item -> item.getProduct() != null) // Avoid null products
                .mapToDouble(item -> item.getProduct().getBasePrice() * item.getQuantity())
                .sum();
        cart.setTotalPrice(totalPrice);
    }



    /*
     *  - Retreive Cart TAMAM
     *  - Add product to cart BITMEK UZERE
     *  - Remove product from cart
     *  - Clear the cart TAMAM
     *  - Checkout? yada order -> checkoutta mesela addressi olması lazım tamamlanması icin, address yoksa kullanıcıya bildirim gitmeli adresin yok diye.
     *  - (ileri seviye) Check Stock Availability -> cart'a ekledigi urunlere 4 ay sonra gelip baktıgında illa stok durumu farklı olacaktır, belirli bir işlem/sürede tetiklenen bu fonksiyon,
     *       cartta stogu bitmis urun olunca carrtan otomatik olarak çıkartmalı o ürünü ve (We have removed x, y and z due to no stock available) gibi bir mesaj döndürmeli.
     *
     */


    public Cart fetchUserCart(String userId) {
        log.info("[CartService] Fetching cart for userId: {}", userId);

        // Fetch the cart
        Optional<Cart> cartOpt = cartRepo.findByUserId(userId);

        if (cartOpt.isEmpty()) {
            log.info("[CartService] No cart found for userId: {}", userId);

            // Create a new cart if none exists
            Cart newCart = new Cart();
            newCart.setUserId(userId);
            newCart.setCartItems(new ArrayList<>());
            newCart.setTotalPrice(0.0);
            cartRepo.save(newCart);
            return newCart;
        }

        Cart cart = cartOpt.get();
        log.info("[CartService] Cart retrieved: {}", cart);
        recalculateTotalPrice(cart); // Ensure the total price is accurate
        return cart;
    }


    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//


    public String clearUserCart(String userId) {

        Optional<User> user = userRepo.findById(userId);

        //Validate the presence of user
        if (userId.isEmpty()) {
            log.error("[CartService][clearUserCart] Invalid userId provided: {}", userId);
            throw new UserNotFoundException("User ID cannot be null or empty");
        }

        //Fetch user's cart
        Optional<Cart> cartOpt = cartRepo.findByUserId(userId);

        //Validate the presence of the cart of user
        if (cartOpt.isEmpty()) {
            log.info("[CartService][clearUserCart] Cart for user {} is empty.", userId);
            return Strings.CART_IS_EMPTY;
        } else {
            Cart cart = cartOpt.get();
            cart.getCartItems().clear();
            cart.setTotalPrice(0.0);
            cartRepo.save(cart);

            log.info("[CartService][clearUserCart] Cart cleared for userId: {}", userId);

            return Strings.CART_HAS_BEEN_CLEARED;
        }
    }


//━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//


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

        Optional<Cart> cartOpt = cartRepo.findByUserId(userId);
        Cart cart;

        if (cartOpt.isEmpty()) {
            cart = new Cart();
            cart.setUserId(userId);
        } else {
           cart = cartOpt.get();
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


    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//





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




