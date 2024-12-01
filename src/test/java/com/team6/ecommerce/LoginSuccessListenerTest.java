import com.team6.ecommerce.cart.CartService;
import com.team6.ecommerce.listener.LoginSuccessListener;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoginSuccessListenerTest {

    @Test
    public void testOnAuthenticationSuccess_CartValidationTriggered() {
        // Arrange
        CartService cartService = mock(CartService.class);
        LoginSuccessListener loginSuccessListener = new LoginSuccessListener(cartService);

        Authentication authentication = mock(Authentication.class);
        User mockUser = new User("test@example.com", "password", new ArrayList<>());
        when(authentication.getPrincipal()).thenReturn(mockUser);

        AuthenticationSuccessEvent event = new AuthenticationSuccessEvent(authentication);

        // Act
        loginSuccessListener.onAuthenticationSuccess(event);

        // Assert
        ArgumentCaptor<String> userEmailCaptor = ArgumentCaptor.forClass(String.class);
        verify(cartService, times(1)).validateUserCart(userEmailCaptor.capture());
        assertEquals("test@example.com", userEmailCaptor.getValue(), "User email should match the logged-in user");
    }

    @Test
    public void testOnAuthenticationSuccess_NoCartServiceCall_WhenInvalidUser() {
        // Arrange
        CartService cartService = mock(CartService.class);
        LoginSuccessListener loginSuccessListener = new LoginSuccessListener(cartService);

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn("anonymousUser");

        AuthenticationSuccessEvent event = new AuthenticationSuccessEvent(authentication);

        // Act
        loginSuccessListener.onAuthenticationSuccess(event);

        // Assert
        verify(cartService, never()).validateUserCart(anyString());
    }
}
