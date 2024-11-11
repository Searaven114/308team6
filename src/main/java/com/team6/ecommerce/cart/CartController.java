package com.team6.ecommerce.cart;
import com.team6.ecommerce.cartitem.CartItem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
@RestController
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
}