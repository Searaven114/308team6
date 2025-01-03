package com.team6.ecommerce.cart;

import com.team6.ecommerce.cart.dto.CheckoutResponseDTO;
import com.team6.ecommerce.cartitem.CartItem;
import com.team6.ecommerce.cartitem.CartItem2;
import com.team6.ecommerce.constants.Strings;
import com.team6.ecommerce.exception.UserNotFoundException;
import com.team6.ecommerce.invoice.Invoice;
import com.team6.ecommerce.invoice.InvoiceRepository;
import com.team6.ecommerce.invoice.InvoiceService;
import com.team6.ecommerce.notification.NotificationService;
import com.team6.ecommerce.order.Order;
import com.team6.ecommerce.order.OrderRepository;
import com.team6.ecommerce.order.OrderStatus;
import com.team6.ecommerce.payment.PaymentService;
import com.team6.ecommerce.payment.dto.PaymentRequest;
import com.team6.ecommerce.payment.dto.PaymentRequestDTO;
import com.team6.ecommerce.payment.dto.PaymentResponse;
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
    private final PaymentService paymentService;
    private final NotificationService notificationService;
    private final InvoiceService invoiceService;

    private void recalculateTotalPrice(Cart cart) {
        double totalPrice = cart.getCartItems().stream()
                .filter(item -> item.getProduct() != null) // Avoid null products
                .mapToDouble(item -> item.getProduct().getBasePrice() * item.getQuantity())
                .sum();
        cart.setTotalPrice(totalPrice);
    }


    public Cart fetchUserCart(String userId) {
        log.info("[CartService][fetchUserCart] Fetching cart for userId: {}", userId);

        // Fetch the cart
        Optional<Cart> cartOpt = cartRepo.findByUserId(userId);

        if (cartOpt.isEmpty()) {
            log.info("[CartService][fetchUserCart] No cart found for userId: {}", userId);

            // Create a new cart if none exists
            Cart newCart = new Cart();
            newCart.setUserId(userId);
            newCart.setCartItems(new ArrayList<>());
            newCart.setTotalPrice(0.0);
            cartRepo.save(newCart);
            return newCart;
        }

        Cart cart = cartOpt.get();
        log.info("[CartService][fetchUserCart] Cart retrieved: {}", cart);
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

        // Validate the presence of user
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

        // Stock checking for non-positive stock
        if (product.getQuantityInStock() <= 0) {
            return Strings.PRODUCT_OUT_OF_STOCK;
        }

        // Additional check for requested quantity being available
        if (quantity > product.getQuantityInStock()) {
            return String.format(Strings.PRODUCT_NOT_AVAILABLE_IN_REQUESTED_QUANTITY, product.getQuantityInStock());
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


    public CheckoutResponseDTO checkout(String userId, PaymentRequestDTO paymentRequest) {
        log.info("[CartService][Checkout] Starting checkout for user ID: {}", userId);

        // Fetch user's cart
        Optional<Cart> cartOpt = cartRepo.findByUserId(userId);
        if (cartOpt.isEmpty() || cartOpt.get().getCartItems().isEmpty()) {
            log.warn("[CartService][Checkout] Cart is empty for user ID: {}", userId);
            return null; // Handle properly in controller
        }

        Cart cart = cartOpt.get();

        // Deduct product stock
        for (CartItem item : cart.getCartItems()) {
            Product product = productRepo.findById(item.getProduct().getId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + item.getProduct().getId()));

            if (product.getQuantityInStock() < item.getQuantity()) {
                log.error("[CartService][Checkout] Not enough stock for product: {}", product.getTitle());
                throw new RuntimeException("Not enough stock for product: " + product.getTitle());
            }

            product.setQuantityInStock(product.getQuantityInStock() - item.getQuantity());

            productRepo.save(product);

            log.info("[CartService][Checkout] Product '{}' stock reduced by {} units. Remaining stock: {}. User ID: {}",
                    product.getTitle(), item.getQuantity(), product.getQuantityInStock(), userId);

        }

        // Convert CartItem to CartItem2
        List<CartItem2> cartItem2List = cart.getCartItems().stream()
                .map(item -> CartItem2.builder()
                        .productName(item.getProduct().getTitle())
                        .quantity(item.getQuantity())
                        .price(item.getProduct().getBasePrice())
                        .build())
                .toList();

        // Create Order
        Order order = Order.builder()
                .userId(userId)
                .cart(cart)
                .orderStatus(OrderStatus.PROCESSING)
                .createdAt(new Date())
                .total(cart.getTotalPrice().longValue())
                .address(userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found")).getAddresses().get(0))
                .build();

        orderRepo.save(order); // Save order to DB

        log.info("[CartService][Checkout] Order created with ID: {}", order.getId());

        // Create and save invoice
        Invoice invoice = Invoice.builder()
                .userId(userId)
                .orderId(cart.getId())
                .totalAmount(cart.getTotalPrice())
                .invoiceDate(new Date())
                .email(userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found")).getEmail())
                .purchasedItems(cartItem2List)
                .build();

        invoiceRepo.save(invoice);

        log.info("[CartService][Checkout] Invoice created for user ID: {} with invoice ID: {}", userId, invoice.getId());

        // Send invoice notification
        //TODO BUNU UNCOMMENTLE DAHA SONRA. HER CHECKOUTTA MAIL GITMESIN / INVALID ADRESSE MAIL GONDERMEYE CALISIP 500 VERMESIN DIYE COMMENTLEDIM
        //notificationService.notifyUserWithInvoice(invoice);

        // Prepare CheckoutResponseDTO
        List<CartItem> purchasedItems = new ArrayList<>(cart.getCartItems()); // Create a copy of cart items before clearing

        // Prepare CheckoutResponseDTO
        CheckoutResponseDTO response = CheckoutResponseDTO.builder()
                .invoice(invoice)
                .totalAmount(invoice.getTotalAmount())
                .purchasedItems(purchasedItems)
                .build();

        // Clear the cart
        cart.getCartItems().clear();
        cart.setTotalPrice(0.0);
        cartRepo.save(cart);

        log.info("[CartService][Checkout] Cart cleared for user ID: {}", userId);
        log.info("[CartService][Checkout] Checkout completed for user ID: {}", userId);
        log.info("[CartService][Checkout] Order ID: {} has been delegated to Delivery Department.", order.getId());
        return response;
    }



    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//


    public String validateCartItems(String userId) {
        // Validate userId
        if (userId == null || userId.isEmpty()) {
            log.error("[CartService][validateCartItems] Invalid userId provided: {}", userId);
            throw new UserNotFoundException("User ID cannot be null or empty");
        }

        // Fetch the cart for the user
        Optional<Cart> cartOpt = cartRepo.findByUserId(userId);
        if (cartOpt.isEmpty()) {
            log.info("[CartService][validateCartItems] Cart not found for userId: {}", userId);
            return null; // No cart to validate
        }

        Cart cart = cartOpt.get();

        // Initialize a message for removed items
        StringBuilder messageBuilder = new StringBuilder();
        List<CartItem> validItems = new ArrayList<>();

        for (CartItem cartItem : cart.getCartItems()) {
            Product product = cartItem.getProduct();

            if (product.getQuantityInStock() <= 0) {
                log.info("[CartService][validateCartItems] Removing product {} from cart for userId {} as it is out of stock.", product.getId(), userId);
                messageBuilder.append(String.format("'%s' is out of stock. ", product.getTitle()));
                continue;
            }

            if (cartItem.getQuantity() > product.getQuantityInStock()) {
                log.info("[CartService][validateCartItems] Removing product {} from cart for userId {} as it exceeds available stock.", product.getId(), userId);
                messageBuilder.append(String.format("'%s' exceeds available stock. ", product.getTitle()));
                continue;
            }

            validItems.add(cartItem);
        }

        // Update the cart with only valid items
        cart.setCartItems(validItems);
        recalculateTotalPrice(cart);
        cartRepo.save(cart);

        // Return the removal message if any items were removed
        String message = messageBuilder.toString();
        if (!message.isEmpty()) {
            log.info("[CartService][validateCartItems] Removed items message: {}", message);
        }
        return message.isEmpty() ? null : message;
    }

}



