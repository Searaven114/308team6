package com.team6.ecommerce.comment.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDTO {

    private String productId;
    private String content;
    private int rating;

}

