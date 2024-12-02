package com.team6.ecommerce.comment;

import com.team6.ecommerce.comment.dto.CommentDTO;
import com.team6.ecommerce.order.Order;
import com.team6.ecommerce.order.OrderService;
import com.team6.ecommerce.product.ProductRepository;
import com.team6.ecommerce.user.User;
import com.team6.ecommerce.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import com.team6.ecommerce.constants.Strings;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Log4j2
@AllArgsConstructor
@Service
public class CommentService {

    //logging, validation, exception handling, ve security yok, final demoya olmalı.

    private final CommentRepository commentRepo;
    private final ProductRepository productRepo;
    private final UserRepository userRepo;
    private final OrderService orderService;

    @PreAuthorize("isAuthenticated()")
    public String addComment(String userId, CommentDTO dto) {

        // Validate user existence
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        String productId = dto.getProductId();

        // Fetch user's orders using OrderService
        List<Order> userOrders = orderService.fetchOrdersByUserId(userId);

        // Validate if the user has purchased the product
        boolean hasPurchased = userOrders.stream()
                .flatMap(order -> order.getCart().getCartItems().stream())
                .anyMatch(cartItem -> cartItem.getProduct().getId().equals(productId));

        if (!hasPurchased) {
            log.info("[CommentService][addComment] User {} has not purchased the product {} but attempted to make a comment on it.", userId, productId);
            return Strings.CANNOT_COMMENT_ON_PRODUCT_DUE_TO_NOT_PURCHASED;
        }

        // Create and save the comment
        Comment comment = new Comment();
        comment.setProductId(dto.getProductId());
        comment.setContent(dto.getContent());
        comment.setUserId(userId);
        comment.setRating(dto.getRating());
        comment.setApproved(false);
        comment.setCreatedDate(LocalDateTime.now());

        commentRepo.save(comment);
        log.info("[CommentService][addComment] Comment added: {}", comment);

        return Strings.COMMENT_ADDED_SUCCESS;
    }



    public List<Comment> getApprovedComments(String productId) {
        return commentRepo.findByProductIdAndApprovedTrue(productId);
    }


    @PreAuthorize("isAuthenticated()")
    @Secured({"ROLE_ADMIN", "ROLE_PRODUCTMANAGER"})
    public List<Comment> getAllCommentsForProduct(String productId) {
        return commentRepo.findByProductId(productId);
    }


    @PreAuthorize("isAuthenticated()")
    @Secured({"ROLE_ADMIN", "ROLE_PRODUCTMANAGER"})
    public void approveComment(String commentId) {

        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        log.info("[CommentService][approveComment] Approving comment: {}", comment);

        comment.setApproved(true);
        commentRepo.save(comment);
    }


    // Calculate the average rating for a product
    public double calculateAverageRating(String productId) {
        List<Comment> allComments = commentRepo.findByProductId(productId);
        if (allComments.isEmpty()) {
            return 0.0;
        }
        double totalRating = allComments.stream().mapToInt(Comment::getRating).sum();
        return totalRating / allComments.size();
    }


    public Map<String, Double> calculateAverageRatings(List<String> productIds) {
        Map<String, Double> avgRatings = new HashMap<>();
        for (String productId : productIds) {
            List<Comment> allComments = commentRepo.findByProductId(productId);
            double avgRating = allComments.isEmpty() ? 0.0 :
                    allComments.stream().mapToInt(Comment::getRating).average().orElse(0.0);
            avgRatings.put(productId, avgRating);
        }
        return avgRatings;
    }
}
















//@Log4j2
//@AllArgsConstructor
//@Service
//public class CommentService {
//
//
//    private final CommentRepository commentRepo;
//    private final ProductRepository productRepo;
//
//    public void addComment(String userId,CommentDTO dto) {
//
//        Comment comment = new Comment();
//
//        comment.setProductId(dto.getProductId());
//        comment.setContent(dto.getContent());
//        comment.setUserId(userId);
//        comment.setRating(dto.getRating());
//        comment.setApproved(false);
//        comment.setCreatedDate(LocalDateTime.now());
//
//
//
//        commentRepo.save(comment);
//        log.info("[CommentService] Comment added: {}", comment);
//    }
//
//    public void addRating(String userId,CommentDTO dto) {
//
//        int rating_value = dto.getRating();
//        Comment rating = new Comment(dto.getProductId(), userId, null, rating_value);
//
//        rating.setApproved(true); //since ratings do not need to be approved
//        rating.setCreatedDate(LocalDateTime.now());
//
//        commentRepo.save(rating);
//        log.info("[CommentService] Rating added: {}", rating.getRating());
//    }
//
//
//    public List<Comment> getApprovedComments(String productId) {
//        return commentRepo.findByProductIdAndApprovedTrue(productId);
//    }
//
//    // Admin or product manager can view all comments (including unapproved)
//   public List<Comment> getAllCommentsForProduct(String productId) {
//       return commentRepo.findByProductId(productId);
//    }
//
//    // Approve a comment
//   public Comment approveComment(String commentId) {
//
//        Comment comment = commentRepo.findById(commentId)
//                .orElseThrow(() -> new RuntimeException("Comment not found"));
//
//        comment.setApproved(true);
//        return commentRepo.save(comment);
//    }
//
//    // Calculate the average rating for a product
//    public double calculateAverageRating(String productId) {
//        List<Comment> allComments = commentRepo.findByProductId(productId);
//        if (allComments.isEmpty()) {
//            return 0.0;
//        }
//        double totalRating = allComments.stream().mapToInt(Comment::getRating).sum();
//        return totalRating / allComments.size();
//    }
//}

