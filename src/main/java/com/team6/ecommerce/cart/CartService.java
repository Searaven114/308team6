package com.team6.ecommerce.cart;

import com.team6.ecommerce.cartitem.CartItem;
import com.team6.ecommerce.constants.Strings;
import com.team6.ecommerce.exception.UserNotFoundException;
import com.team6.ecommerce.invoice.Invoice;
import com.team6.ecommerce.invoice.InvoiceRepository;
import com.team6.ecommerce.order.Order;
import com.team6.ecommerce.order.OrderRepository;
import com.team6.ecommerce.order.OrderStatus;
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
    private final OrderRepository orderRepo;
    private final InvoiceRepository invoiceRepo;

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

        log.info("Returned Cart is {}", cart.toString());

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

        //Validate the presence of user
        if (userId.isEmpty()) {
            log.error("[CartService][addItemToUserCart] Invalid userId provided: {}", userId);
            throw new UserNotFoundException("User ID cannot be null or empty");
        }

        Optional<Product> productOpt = productRepo.findById(productId);

        // Product presence checking
        if (productOpt.isEmpty()) {
            return Strings.PRODUCT_NOT_FOUND;
        }

        Product product = productOpt.get();

        // Stock checking
        if (product.getQuantityInStock() <= 0) {
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

        // Check if the product already exists in the cart
        Optional<CartItem> existingCartItemOpt = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingCartItemOpt.isPresent()) {
            CartItem existingCartItem = existingCartItemOpt.get();

            // Check if the new quantity exceeds available stock
            if (existingCartItem.getQuantity() + quantity > product.getQuantityInStock()) {
                return String.format(Strings.PRODUCT_NOT_AVAILABLE_IN_REQUESTED_QUANTITY, product.getQuantityInStock());
            }

            // Update the quantity of the existing cart item
            existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
        } else {
            // Create a new cart item if it does not already exist
            CartItem cartItem = new CartItem(product, quantity);
            cart.getCartItems().add(cartItem);
        }

        // Recalculate total price of the cart
        recalculateTotalPrice(cart);
        cartRepo.save(cart);

        log.info("[CartService][addItemToUserCart] User with ID {} added {} units of '{}' to their cart.", userId, quantity, product.getTitle());

        return Strings.PRODUCT_ADDED_TO_CART;
    }


    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//


    public String updateCartItemQuantity(String userId, String productId, int quantity) {

        //Validate the presence of user
        if (userId.isEmpty()) {
            log.error("[CartService][updateCartItemQuantity] Invalid userId provided: {}", userId);
            throw new UserNotFoundException("User ID cannot be null or empty");
        }

        Optional<Cart> cartOpt = cartRepo.findByUserId(userId);

        if (cartOpt.isEmpty()) {
            log.warn("[CartService][updateCartItemQuantity] No cart found for userId: {}", userId);
            return Strings.CART_IS_EMPTY;
        }

        Cart cart = cartOpt.get();

        // Use Predicate to filter cart items by productId, retaining only matching product in the stream.
        Optional<CartItem> cartItemOpt = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (cartItemOpt.isEmpty()) {
            log.warn("[CartService][updateCartItemQuantity] Product {} not found in cart for userId: {}", productId, userId);
            return Strings.PRODUCT_NOT_IN_CART;
        }

        CartItem cartItem = cartItemOpt.get();
        Product product = cartItem.getProduct();

        if (quantity <= 0) {
            cart.getCartItems().remove(cartItem);
            log.info("[CartService][updateCartItemQuantity] Removed product {} from cart for userId: {}", productId, userId);
        } else if (quantity > product.getQuantityInStock()) {
            return String.format(Strings.PRODUCT_NOT_AVAILABLE_IN_REQUESTED_QUANTITY, product.getQuantityInStock());
        } else {
            cartItem.setQuantity(quantity);
            log.info("[CartService][updateCartItemQuantity] Updated product {} quantity to {} for userId: {}", productId, quantity, userId);
        }

        recalculateTotalPrice(cart);
        cartRepo.save(cart);

        return Strings.CART_UPDATED_SUCCESSFULLY;
    }


    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//


    public String removeItemFromUserCart(String userId, String productId) {

        //Validate the presence of user
        if (userId.isEmpty()) {
            log.error("[CartService][removeItemFromUserCart] Invalid userId provided: {}", userId);
            throw new UserNotFoundException("User ID cannot be null or empty");
        }

        // Fetch the cart for the user
        Optional<Cart> cartOpt = cartRepo.findByUserId(userId);

        if (cartOpt.isEmpty()) {
            log.info("[CartService][removeItemFromUserCart] Cart not found for userId: {}", userId);
            return Strings.CART_IS_EMPTY;
        }

        Cart cart = cartOpt.get();

        // Find the cart item to be removed
        Optional<CartItem> cartItemOpt = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (cartItemOpt.isEmpty()) {
            log.info("[CartService][removeItemFromUserCart] Product with ID {} not found in cart for userId: {}", productId, userId);
            return Strings.PRODUCT_NOT_IN_CART;
        }

        // Remove the cart item
        CartItem cartItem = cartItemOpt.get();
        cart.getCartItems().remove(cartItem);

        // Recalculate the total price
        recalculateTotalPrice(cart);

        // Save the updated cart
        cartRepo.save(cart);

        log.info("[CartService][removeItemFromUserCart] Product with ID {} removed from cart for userId: {}", productId, userId);
        return Strings.PRODUCT_REMOVED_FROM_CART;
    }


    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//


    public String checkout(String userId) {

        // Validate the presence of user
        if (userId == null || userId.isEmpty()) {
            log.error("[CartService][Checkout] Invalid userId provided: {}", userId);
            throw new UserNotFoundException("User ID cannot be null or empty");
        }

        // Fetch the cart for the user
        Optional<Cart> cartOpt = cartRepo.findByUserId(userId);

        if (cartOpt.isEmpty() || cartOpt.get().getCartItems().isEmpty()) {
            log.info("[CartService][Checkout] Cart not found or empty for userId: {}", userId);
            return Strings.CART_IS_EMPTY;
        }

        Cart cart = cartOpt.get();

        // Create Order
        Order order = Order.builder()
                .userId(userId)
                .cart(cart)
                .orderStatus(OrderStatus.PROCESSING)
                .createdAt(new Date())
                .total(cart.getTotalPrice().longValue())
                .build();

        orderRepo.save(order); // Save order to DB

        log.info("[CartService][Checkout] Order created with ID: {}", order.getId());

        // Generate Invoice
        User user = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));

        Invoice invoice = Invoice.builder()
                .orderId(order.getId())
                .userId(userId)
                .totalAmount(cart.getTotalPrice())
                .invoiceDate(new Date())
                .email(user.getEmail())
                .build();

        invoiceRepo.save(invoice); // Save invoice to DB

        log.info("[CartService][Checkout] Invoice generated with ID: {}", invoice.getId());

        // Clear Cart
        cart.getCartItems().clear();
        cart.setTotalPrice(0.0);
        cartRepo.save(cart);

        log.info("[CartService][Checkout] Cart cleared for userId: {}", userId);

        return String.format("Order placed successfully. Invoice ID: %s", invoice.getId());
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




