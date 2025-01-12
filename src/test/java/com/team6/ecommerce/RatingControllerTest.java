package com.team6.ecommerce;

import com.team6.ecommerce.rating.RatingController;
import com.team6.ecommerce.rating.RatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RatingControllerTest {

    @InjectMocks
    private RatingController ratingController;

    @Mock
    private RatingService ratingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    /*
    @Test
    void testAddRating() {
        String productId = "product123";
        int ratingValue = 5;

        when(ratingService.addRating(anyString(), eq(productId), eq(ratingValue)))
                .thenReturn("Rating added successfully.");

        ResponseEntity<String> response = ratingController.addRating(productId, ratingValue);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Rating added successfully.", response.getBody());
    }
    */
    @Test
    void testGetAverageRating() {
        String productId = "product123";

        when(ratingService.calculateAverageRating(productId)).thenReturn(4.5);

        ResponseEntity<Double> response = ratingController.getAverageRating(productId);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(4.5, response.getBody());
    }
}

