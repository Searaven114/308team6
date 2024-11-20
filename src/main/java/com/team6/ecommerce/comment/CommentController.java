package com.team6.ecommerce.comment;
import org.springframework.security.access.AccessDeniedException;

import com.team6.ecommerce.comment.dto.CommentDTO;
import com.team6.ecommerce.user.User;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Set;


@AllArgsConstructor
@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

//        private String productId;
//        private String userId;
//        private String content;
//        private int rating;

    @PostMapping("/add-comment")
    public Comment addComment(@RequestBody @Valid CommentDTO dto) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        commentService.addComment(user.getId(), dto);

        return null;
    }

    //rating conteni null olan bir comment objesi gibi muamele gorecek
    @PostMapping("/add-rating")
    public Comment addRating(@RequestBody @Valid CommentDTO dto) {


        if (dto.getContent() != null && !dto.getContent().isEmpty()) {
            throw new IllegalArgumentException("Rating should not include comment content.");
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        commentService.addRating(user.getId(), dto);

        return null;
    }

    @GetMapping("/{productId}")
    public List<Comment> getApprovedComments(@PathVariable String productId) {
        return commentService.getApprovedComments(productId);
    }


    @GetMapping("/{productId}-all")
    public List<Comment> getAllComments(@PathVariable String productId) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        Set<String> user_roles = user.getRoles();

        if (user_roles.contains("ROLE_ADMIN")) {
            return commentService.getAllCommentsForProduct(productId);

        }
        else {
            throw new AccessDeniedException("Only admins can view comments.");
        }
    }


    @PostMapping("/approve-comment")
    public Comment approveComment(@RequestBody @Valid CommentDTO dto) {



        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        Set<String> user_roles = user.getRoles();

        if (user_roles.contains("ROLE_ADMIN")) {
            commentService.approveComment(dto.getCommentId());
            return null;
        }

        else {
            throw new AccessDeniedException("Only admins can approve comments.");
        }


    }

    @GetMapping("/{productId}/get-avg-rating")
    public double getAvgRating(@PathVariable String productId) {

        return commentService.calculateAverageRating(productId);
    }

    @GetMapping("/products/avg-ratings")
    public Map<String, Double> getAvgRatings(@RequestParam List<String> productIds) {
        return commentService.calculateAverageRatings(productIds);
    }

}








//@AllArgsConstructor
//@RestController
//@RequestMapping("/api/comments")
//public class CommentController {
//
//    private final CommentService commentService;
//
////        private String productId;
////        private String userId;
////        private String content;
////        private int rating;
//
//    @PostMapping("/")
//    public Comment addComment(@RequestBody @Valid CommentDTO dto) {
//
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        User user = (User) auth.getPrincipal();
//
//        commentService.addComment(user.getId(), dto);
//
//        return null;
//    }
//
//    //rating conteni null olan bir comment objesi gibi muamele gorecek
//    @PostMapping("/add-rating")
//    public Comment addRating(@RequestBody @Valid CommentDTO dto) {
//
//
//        if (dto.getContent() != null && !dto.getContent().isEmpty()) {
//            throw new IllegalArgumentException("Rating should not include comment content.");
//        }
//
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        User user = (User) auth.getPrincipal();
//
//        commentService.addRating(user.getId(), dto);
//
//        return null;
//    }
//
//    @GetMapping("/{productId}")
//    public List<Comment> getApprovedComments(@PathVariable String productId) {
//        return commentService.getApprovedComments(productId);
//    }
//
//
//    @GetMapping("/{productId}-all")
//    public List<Comment> getAllComments(@PathVariable String productId) {
//
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        User user = (User) auth.getPrincipal();
//        Set<String> user_roles = user.getRoles();
//
//        if (user_roles.contains("admin")) {
//            return commentService.getAllCommentsForProduct(productId);
//
//        }
//        else {
//            throw new AccessDeniedException("Only admins can view comments.");
//        }
//    }
//
//
//    @PostMapping("/approve-comment")
//    public Comment approveComment(@RequestBody @Valid CommentDTO dto) {
//
//
//
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        User user = (User) auth.getPrincipal();
//        Set<String> user_roles = user.getRoles();
//
//        if (user_roles.contains("admin")) {
//            commentService.approveComment(dto.getCommentId());
//            return null;
//        }
//        else {
//            throw new AccessDeniedException("Only admins can approve comments.");
//        }
//
//
//    }
//
//    @GetMapping("/{productId}/-get-avg-rating")
//    public double getAvgRating(@PathVariable String productId) {
//
//        return commentService.calculateAverageRating(productId);
//    }
//
//}
