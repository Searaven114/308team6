package com.borau.cs308demo.comment.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDTO {

    private String productId;
    private String userId;
    private String content;
    private int rating;
    private String commentId;


}

