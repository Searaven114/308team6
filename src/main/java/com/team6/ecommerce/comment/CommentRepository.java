package com.team6.ecommerce.comment;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {

    List<Comment> findByProductIdAndApprovedTrue(String productId);  // Fetch approved comments for a product
    List<Comment> findByProductId(String productId);  // Fetch all comments (for admin approval)
    Boolean existsCommentByUserId(String userId);
}

