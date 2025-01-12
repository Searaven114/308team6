package com.team6.ecommerce;

import com.team6.ecommerce.comment.Comment;
import com.team6.ecommerce.comment.CommentRepository;
import com.team6.ecommerce.comment.CommentService;
import com.team6.ecommerce.order.OrderService;
import com.team6.ecommerce.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepo;

    @Mock
    private OrderService orderService;

    @Mock
    private UserRepository userRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddComment() {
        String userId = "user123";
        String productId = "product123";
        String content = "Great product!";

        when(orderService.fetchOrdersByUserId(userId)).thenReturn(Collections.emptyList());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            commentService.addComment(userId, productId, content);
        });

        assertEquals("User has not purchased this product.", exception.getMessage());
    }

    @Test
    void testApproveComment() {
        String commentId = "comment123";
        Comment comment = new Comment();
        comment.setId(commentId);
        comment.setApproved(false);

        when(commentRepo.findById(commentId)).thenReturn(Optional.of(comment));

        commentService.approveComment(commentId);

        assertTrue(comment.isApproved());
        verify(commentRepo, times(1)).save(comment);
    }

    @Test
    void testGetUnapprovedComments() {
        when(commentRepo.findByApprovedFalse()).thenReturn(Collections.emptyList());

        List<Comment> comments = commentService.getUnapprovedComments();

        assertNotNull(comments);
        assertTrue(comments.isEmpty());
    }

    @Test
    void testGetApprovedComments() {
        String productId = "product123";

        when(commentRepo.findByProductIdAndApprovedTrue(productId)).thenReturn(Collections.emptyList());

        List<Comment> comments = commentService.getApprovedComments(productId);

        assertNotNull(comments);
        assertTrue(comments.isEmpty());
    }
}
