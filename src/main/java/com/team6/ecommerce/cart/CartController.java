package com.team6.ecommerce.cart;
import com.team6.ecommerce.cartitem.CartItem;
import com.team6.ecommerce.constants.Strings;
import com.team6.ecommerce.invoice.Invoice;
import com.team6.ecommerce.invoice.InvoiceService;
import com.team6.ecommerce.notification.NotificationService;
import com.team6.ecommerce.payment.dto.PaymentRequest;
import com.team6.ecommerce.payment.dto.PaymentRequestDTO;
import com.team6.ecommerce.user.User;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@AllArgsConstructor
@RestController
@Log4j2
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;
    private final InvoiceService invoiceService;
    private final NotificationService notificationService;



    @GetMapping
    public ResponseEntity<?> fetchUserCart() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            log.warn("[CartController][fetchUserCart] Unauthorized access attempt: No authentication found or user not authenticated");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated");
        }

        User user = (User) authentication.getPrincipal();

        String user_id = user.getId();

        Cart cart = cartService.fetchUserCart( user_id );

        if (cart.getCartItems().isEmpty()) {
            log.info("[CartController][fetchUserCart] Cart is empty for user with ID: {}", user_id);

            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Your cart is empty.");
        }

        return ResponseEntity.ok(cart);
    }


//━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//


    @DeleteMapping("/clear")
    public ResponseEntity<?> clearCart() {

        // Retrieve the authenticated user from the security context
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // Check if the authentication object is null or not authenticated
        if (auth == null || !auth.isAuthenticated()) {
            log.warn("[CartController][clearCart]Unauthorized access attempt: No authentication found or user not authenticated");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated");
        }

        // Ensure that the principal is of type User
        if (!(auth.getPrincipal() instanceof User)) {
            log.warn("[CartController][clearCart]Unauthorized access attempt: Invalid principal type for user: {}", auth.getPrincipal());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid user type");
        }

        // Cast the principal to the User class
        User user = (User) auth.getPrincipal();

        // Double-check if the user object is null
        if (user == null) {
            log.error("[CartController][clearCart]Unexpected error: Authenticated principal is null");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authenticated user not found");
        }

        String result = cartService.clearUserCart(user.getId());

        if (Strings.CART_IS_EMPTY.equals(result)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(result);
        }
        return ResponseEntity.ok(result);
    }


//━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//


    @PostMapping("/cart/add")
    public ResponseEntity<String> addProductToCart(@RequestParam String productId, @RequestParam int quantity) {

        // Retrieve the authenticated user from the security context
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // Check if the authentication object is null or not authenticated
        if (auth == null || !auth.isAuthenticated()) {
            log.warn("[CartController][addProductToCart]Unauthorized access attempt: No authentication found or user not authenticated");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated");
        }

        // Ensure that the principal is of type User
        if (!(auth.getPrincipal() instanceof User)) {
            log.warn("[CartController][addProductToCart]Unauthorized access attempt: Invalid principal type for user: {}", auth.getPrincipal());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid user type");
        }

        // Cast the principal to the User class
        User user = (User) auth.getPrincipal();

        // Double-check if the user object is null
        if (user == null) {
            log.error("[CartController][addProductToCart]sUnexpected error: Authenticated principal is null");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authenticated user not found");
        }

        String result = cartService.addItemToUserCart(user.getId(), productId, quantity);


        if (Strings.PRODUCT_NOT_FOUND.equals(result)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);

        } else if (Strings.PRODUCT_OUT_OF_STOCK.equals(result)) {
            return ResponseEntity.badRequest().body(result);

        } else if (result.startsWith(String.format(Strings.PRODUCT_NOT_AVAILABLE_IN_REQUESTED_QUANTITY, 0))) {
            return ResponseEntity.badRequest().body(result);

        } else {
            return ResponseEntity.ok(result);
        }
    }


    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//


    @PutMapping("/update-quantity")
    public ResponseEntity<String> updateCartItemQuantity(@RequestParam String productId, @RequestParam int quantity) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            log.warn("[CartController][updateCartItemQuantity] Unauthorized access attempt.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated");
        }

        if (!(auth.getPrincipal() instanceof User)) {
            log.warn("[CartController][updateCartItemQuantity] Invalid principal type.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid user type");
        }

        User user = (User) auth.getPrincipal();

        String result = cartService.updateCartItemQuantity(user.getId(), productId, quantity);

        if (Strings.CART_IS_EMPTY.equals(result)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(result);

        } else if (Strings.PRODUCT_NOT_IN_CART.equals(result)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);

        } else if (result.startsWith(String.format(Strings.PRODUCT_NOT_AVAILABLE_IN_REQUESTED_QUANTITY, 0))) {
            return ResponseEntity.badRequest().body(result);
        }

        return ResponseEntity.ok(result);
    }

    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//

    @DeleteMapping("/remove")
    public ResponseEntity<String> removeProductFromCart(@RequestParam String productId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // Check if the authentication object is null or not authenticated
        if (auth == null || !auth.isAuthenticated()) {
            log.warn("[CartController][removeProductFromCart] Unauthorized access attempt: No authentication found or user not authenticated");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated");
        }

        // Ensure that the principal is of type User
        if (!(auth.getPrincipal() instanceof User)) {
            log.warn("[CartController][removeProductFromCart] Unauthorized access attempt: Invalid principal type for user: {}", auth.getPrincipal());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid user type");
        }

        // Cast the principal to the User class
        User user = (User) auth.getPrincipal();

        // Double-check if the user object is null
        if (user == null) {
            log.error("[CartController][removeProductFromCart] Unexpected error: Authenticated principal is null");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authenticated user not found");
        }

        // Call the service to remove the product
        String result = cartService.removeItemFromUserCart(user.getId(), productId);

        if (result.equals(Strings.PRODUCT_NOT_IN_CART)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }

        return ResponseEntity.ok(result);
    }


    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//


    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(@RequestBody PaymentRequestDTO dto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            log.warn("[CartController][Checkout] Unauthorized access attempt.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated");
        }

        if (!(auth.getPrincipal() instanceof User)) {
            log.warn("[CartController][Checkout] Invalid principal type.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid user type");
        }

        User user = (User) auth.getPrincipal();

        String result = cartService.checkout(user.getId(),dto);

        if (Strings.CART_IS_EMPTY.equals(result)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("result");
        }


        // Check if result contains the "Invoice" keyword
        if (result.startsWith("Invoice")) {

            String invoiceId = result.split(" ")[1]; // Extract invoice ID

            Optional<Invoice> invoiceOpt = invoiceService.getInvoiceById(invoiceId); // Assume a service method to fetch invoice

            if (invoiceOpt.isPresent()) {
                Invoice invoice = invoiceOpt.get();
                invoice.setId(user.getEmail());
                notificationService.notifyUserWithInvoice(invoice);
                return ResponseEntity.ok(invoiceOpt.get());
            } else {
                log.error("[CartController][Checkout] Invoice ID {} not found", invoiceId);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving invoice");
            }
        }

        //this is the case in which the returned string doesn't match with any if cases which indicates an error.
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Strings.CART_ERROR);
    }





}