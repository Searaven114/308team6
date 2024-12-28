package com.team6.ecommerce.rating;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends MongoRepository<Rating, String> {

    List<Rating> findByProductId(String productId); // Fetch all ratings for a product

    boolean existsByProductIdAndUserId(String productId, String userId); // Check if user already rated
}
