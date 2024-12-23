package com.team6.ecommerce.comment;

import jakarta.validation.constraints.Size;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;



@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Setter
@Document(collection = "comments")
public class Comment {

    @Id
    private String id;

    @Indexed
    private String productId;

    private String userId;

    private String content;

    @Size(min = 0, max = 5)
    private int rating; // 1-10, kesinlikle serverside sınırlama ve validation olmalı bunda

    private boolean approved = false;

    private LocalDateTime createdDate;


    public Comment(String productId, String userId, String content, int rating) {
        this.productId = productId;
        this.userId = userId;
        this.content = content;
        this.rating = rating;
    }

    public Comment(String productId, String userId, String content, int rating, boolean approved) {
        this.productId = productId;
        this.userId = userId;
        this.content = content;
        this.rating = rating;
        this.approved = approved;
    }
}