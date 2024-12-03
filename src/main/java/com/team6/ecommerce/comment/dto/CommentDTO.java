package com.team6.ecommerce.comment.dto;

import jakarta.annotation.Nullable;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDTO {

    private String productId;

    @Nullable
    private String content;


    private int rating;


}

