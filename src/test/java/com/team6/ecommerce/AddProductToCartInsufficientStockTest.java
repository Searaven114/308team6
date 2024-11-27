import com.team6.ecommerce.cart.Cart;
import com.team6.ecommerce.cart.CartRepository;
import com.team6.ecommerce.cart.CartService;
import com.team6.ecommerce.constants.Strings;
import com.team6.ecommerce.product.Product;
import com.team6.ecommerce.product.ProductRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AddProductToCartInsufficientStockTest {

    @Test
    public void testAddItemToUserCart_WhenStockIsInsufficient() {
        // Arrange
        String userId = "test-user-id";
        String productId = "test-product-id";
        int quantityToAdd = 1;

        CartRepository cartRepository = mock(CartRepository.class);
        ProductRepository productRepository = mock(ProductRepository.class);

        Product mockProduct = new Product();
        mockProduct.setId(productId);
        mockProduct.setBasePrice(100.0);
        mockProduct.setQuantityInStock(0); // Stokta ürün yok

        Cart mockCart = new Cart();
        mockCart.setUserId(userId);
        mockCart.setCartItems(new ArrayList<>());
        mockCart.setTotalPrice(0.0);

        when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(mockCart));

        CartService cartService = new CartService(
                null, // Mocked UserService
                null, // Mocked UserRepository
                productRepository,
                null, // Mocked HttpSession
                cartRepository,
                null, // Mocked OrderRepository
                null, // Mocked InvoiceRepository
                null  // Mocked PaymentService
        );

        // Act
        String result = cartService.addItemToUserCart(userId, productId, quantityToAdd);

        // Assert
        assertEquals(Strings.PRODUCT_OUT_OF_STOCK, result, "Expected out of stock message");
        assertTrue(mockCart.getCartItems().isEmpty(), "Cart should still be empty");

        // Verify no save was called
        verify(cartRepository, never()).save(mockCart);
    }
}
