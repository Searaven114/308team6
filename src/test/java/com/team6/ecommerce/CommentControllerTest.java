package com.team6.ecommerce;

import com.team6.ecommerce.comment.CommentController;
import com.team6.ecommerce.comment.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommentControllerTest {

    @InjectMocks
    private CommentController commentController;

    @Mock
    private CommentService commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddComment() {
        String productId = "product123";
        String content = "Great product!";

        when(commentService.addComment(anyString(), eq(productId), eq(content)))
                .thenReturn("Comment submitted for approval.");

        ResponseEntity<String> response = commentController.addComment(productId, content);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Comment submitted for approval.", response.getBody());
    }

    @Test
    void testApproveComment() {
        String commentId = "comment123";

        doNothing().when(commentService).approveComment(commentId);

        ResponseEntity<String> response = commentController.approveComment(commentId);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Comment approved successfully.", response.getBody());
    }
}
