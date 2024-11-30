package com.team6.ecommerce;

import com.team6.ecommerce.user.User;
import com.team6.ecommerce.user.UserRepository;
import com.team6.ecommerce.user.UserService;
import com.team6.ecommerce.user.dto.UserRegistrationDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserRegistrationTest {

//    @Test
//    public void testRegisterUser_Success() {
//        // Arrange
//        UserRepository userRepository = mock(UserRepository.class);
//
//        UserRegistrationDTO registrationDTO = new UserRegistrationDTO();
//        registrationDTO.setEmail("test@example.com");
//        registrationDTO.setPassword("password123");
//        registrationDTO.setFullName("Test User");
//
//        User savedUser = new User();
//        savedUser.setId("user-1");
//        savedUser.setEmail(registrationDTO.getEmail());
//        savedUser.setFullName(registrationDTO.getFullName());
//
//        when(userRepository.save(any(User.class))).thenReturn(savedUser);
//
//        UserService userService = new UserService(userRepository);
//
//        // Act
//        User result = userService.registerUser(registrationDTO);
//
//        // Assert
//        assertNotNull(result, "Saved user should not be null");
//        assertEquals("user-1", result.getId(), "User ID should match");
//        assertEquals(registrationDTO.getEmail(), result.getEmail(), "Email should match");
//        assertEquals(registrationDTO.getFullName(), result.getFullName(), "Full name should match");
//
//        // Verify repository interaction
//        verify(userRepository, times(1)).save(any(User.class));
//    }
//
//    @Test
//    public void testRegisterUser_WhenEmailAlreadyExists() {
//        // Arrange
//        UserRepository userRepository = mock(UserRepository.class);
//
//        UserRegistrationDTO registrationDTO = new UserRegistrationDTO();
//        registrationDTO.setEmail("test@example.com");
//        registrationDTO.setPassword("password123");
//        registrationDTO.setFullName("Test User");
//
//        when(userRepository.existsByEmail(registrationDTO.getEmail())).thenReturn(true);
//
//        UserService userService = new UserService(userRepository);
//
//        // Act & Assert
//        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.registerUser(registrationDTO));
//        assertEquals("Email already exists", exception.getMessage(), "Expected 'Email already exists' exception message");
//
//        // Verify repository interaction
//        verify(userRepository, never()).save(any(User.class));
//    }
}
