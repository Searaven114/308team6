package com.team6.ecommerce;

import com.team6.ecommerce.rating.Rating;
import com.team6.ecommerce.rating.RatingRepository;
import com.team6.ecommerce.rating.RatingService;
import com.team6.ecommerce.order.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RatingServiceTest {

    @InjectMocks
    private RatingService ratingService;

    @Mock
    private RatingRepository ratingRepo;

    @Mock
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /*
    @Test
    void testAddRating_Success() {
        String userId = "user123";
        String productId = "product123";
        int ratingValue = 4;

        // Mock purchase validation
        when(orderService.fetchOrdersByUserId(userId)).thenReturn(Collections.singletonList());

        // Mock rating save
        when(ratingRepo.save(any(Rating.class))).thenAnswer(invocation -> invocation.getArgument(0));

        String result = ratingService.addRating(userId, productId, ratingValue);

        assertEquals("Rating added successfully.", result);
        verify(ratingRepo, times(1)).save(any(Rating.class));
    }
    */
    @Test
    void testAddRating_UserNotPurchasedProduct() {
        String userId = "user123";
        String productId = "product123";
        int ratingValue = 3;

        // Mock purchase validation failure
        when(orderService.fetchOrdersByUserId(userId)).thenReturn(Collections.emptyList());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            ratingService.addRating(userId, productId, ratingValue);
        });

        assertEquals("User has not purchased this product.", exception.getMessage());
        verify(ratingRepo, never()).save(any(Rating.class));
    }

    @Test
    void testCalculateAverageRating_NoRatings() {
        String productId = "product123";

        // Mock no ratings for the product
        when(ratingRepo.findByProductId(productId)).thenReturn(Collections.emptyList());

        double averageRating = ratingService.calculateAverageRating(productId);

        assertEquals(0.0, averageRating);
    }
}
