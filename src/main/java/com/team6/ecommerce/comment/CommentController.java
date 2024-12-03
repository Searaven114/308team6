package com.team6.ecommerce.comment;

import com.team6.ecommerce.constants.Strings;
import com.team6.ecommerce.order.Order;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;

import com.team6.ecommerce.comment.dto.CommentDTO;
import com.team6.ecommerce.user.User;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Log4j2
@AllArgsConstructor
@RestController
@RequestMapping("/api/comments")
public class CommentController {

    //validasyon yetersiz

    private final CommentService commentService;


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/add-comment")
    public ResponseEntity<?> addComment(@RequestBody @Valid CommentDTO dto) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        log.info("[CommentController][addComment] Adding comment for productId: {}", dto.getProductId());

        log.info("[CommentController][addComment] User ID: {} is adding a comment", user.getId());

        String result = commentService.addComment(user.getId(), dto);

        if (result.equals(Strings.COMMENT_ADDED_SUCCESS)){
            return ResponseEntity.ok().body(result);
        } else if (result.equals(Strings.CANNOT_COMMENT_ON_PRODUCT_DUE_TO_NOT_PURCHASED)){

            return ResponseEntity.status(403).body(result);

        } else if (result.equals( Strings.DUPLICATE_COMMENT_BLOCKED)){

            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);

        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    //todo batudaki son kod bu
    /*@PreAuthorize("isAuthenticated()")
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
    }*/

//━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//

    //Asıl kullanılacak bu endpoint, paginated değil komple liste döndürüyor.
    @GetMapping("/{productId}")
    public List<Comment> getApprovedComments(@PathVariable String productId) {

        // Validating productId
        if (productId == null || productId.isEmpty()) {
            log.error("[CommentController][getApprovedComments] Invalid productId provided: {}", productId);
            throw new IllegalArgumentException("Product ID cannot be null or empty");
        }

        log.info("[CommentController][getApprovedComments] Fetching approved comments for productId: {}", productId);
        List<Comment> comments = commentService.getApprovedComments(productId);
        log.info("[CommentController][getApprovedComments] Retrieved {} approved comments for productId: {}", comments.size(), productId);
        return comments;
    }

//━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//

    @Secured({"ROLE_PRODUCTMANAGER", "ROLE_ADMIN"})
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/approve-comment/{commentId}")
    public ResponseEntity<?> approveComment(@RequestParam String commentId) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        commentService.approveComment(commentId);

        log.info("[CommentController][approveComment] Comment ID: {} approved by User ID: {}", commentId, user.getId());

        return ResponseEntity.ok().build();
    }

//━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//

    @GetMapping("/{productId}/get-avg-rating")
    public double getAvgRating(@PathVariable String productId) {
        double avgRating = commentService.calculateAverageRating(productId);

        log.info("[CommentController][getAvgRating] Average rating for productId: {} is {}", productId, avgRating);

        return avgRating;
    }

//━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//

    @GetMapping("/products/avg-ratings")
    public Map<String, Double> getAvgRatings(@RequestParam List<String> productIds) {
        log.info("[CommentController][getAvgRatings] Calculating average ratings for productIds: {}", productIds);
        Map<String, Double> avgRatings = commentService.calculateAverageRatings(productIds);
        log.info("[CommentController][getAvgRatings] Calculated average ratings for {} products", avgRatings.size());
        return avgRatings;
    }
}
