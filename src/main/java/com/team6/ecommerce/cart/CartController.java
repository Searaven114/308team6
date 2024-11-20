package com.team6.ecommerce.cart;
import com.team6.ecommerce.cartitem.CartItem;
import com.team6.ecommerce.constants.Strings;
import com.team6.ecommerce.user.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@Log4j2
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<?> getAllCartItems() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Kullanıcı kimliği doğrulanmamış.");
        }

        String userId = authentication.getName();
        List<CartItem> cartItems = cartService.getCartItemsByUserId(userId);

        if (cartItems.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Sepetiniz boş.");
        }

        return ResponseEntity.ok(cartItems);
    }

    /* HENÜZ LOGINSIZ CARTA EKLEME FONKSIYONALİTESİ İMPLEMENTE EDİLMEDİ */
    @PostMapping("/cart/add")
    public ResponseEntity<String> addProductToCart(@RequestParam String productId, @RequestParam int quantity) {

        // Retrieve the authenticated user from the security context
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // Check if the authentication object is null or not authenticated
        if (auth == null || !auth.isAuthenticated()) {
            log.warn("Unauthorized access attempt: No authentication found or user not authenticated");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated");
        }

        // Ensure that the principal is of type User
        if (!(auth.getPrincipal() instanceof User)) {
            log.warn("Unauthorized access attempt: Invalid principal type for user: {}", auth.getPrincipal());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid user type");
        }

        // Cast the principal to the User class
        User user = (User) auth.getPrincipal();

        // Double-check if the user object is null
        if (user == null) {
            log.error("Unexpected error: Authenticated principal is null");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authenticated user not found");
        }

        String result = cartService.addItemToUserCart(user.getId(), productId, quantity);


//        if (Strings.PRODUCT_NOT_FOUND.equals(result)) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
//
//        } else if (result.startsWith(String.format(Strings.PRODUCT_NOT_AVAILABLE_IN_REQUESTED_QUANTITY, 0))) {
//            return ResponseEntity.badRequest().body(result);
//
//        } else if (Strings.PRODUCT_OUT_OF_STOCK.equals(result)) {
//            return ResponseEntity.badRequest().body(result);
//
//        } else {
//            return ResponseEntity.ok(result);
//        }

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






}