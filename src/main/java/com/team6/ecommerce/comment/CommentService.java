package com.team6.ecommerce.comment;

import com.team6.ecommerce.comment.dto.CommentDTO;
import com.team6.ecommerce.product.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Log4j2
@AllArgsConstructor
@Service
public class CommentService {


    private final CommentRepository commentRepo;
    private final ProductRepository productRepo;

    public void addComment(String userId,CommentDTO dto) {

        Comment comment = new Comment();

        comment.setProductId(dto.getProductId());
        comment.setContent(dto.getContent());
        comment.setUserId(userId);
        comment.setRating(dto.getRating());
        comment.setApproved(false);
        comment.setCreatedDate(LocalDateTime.now());



        commentRepo.save(comment);
        log.info("[CommentService] Comment added: {}", comment);
    }

    public void addRating(String userId,CommentDTO dto) {

        int rating_value = dto.getRating();
        Comment rating = new Comment(dto.getProductId(), userId, null, rating_value);

        rating.setApproved(true); //since ratings do not need to be approved
        rating.setCreatedDate(LocalDateTime.now());

        commentRepo.save(rating);
        log.info("[CommentService] Rating added: {}", rating.getRating());
    }


    public List<Comment> getApprovedComments(String productId) {
        return commentRepo.findByProductIdAndApprovedTrue(productId);
    }

    // Admin or product manager can view all comments (including unapproved)
   public List<Comment> getAllCommentsForProduct(String productId) {
       return commentRepo.findByProductId(productId);
    }

    // Approve a comment
   public Comment approveComment(String commentId) {

        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        comment.setApproved(true);
        return commentRepo.save(comment);
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
}

