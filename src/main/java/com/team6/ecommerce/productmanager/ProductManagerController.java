package com.team6.ecommerce.productmanager;


import com.team6.ecommerce.category.Category;
import com.team6.ecommerce.category.CategoryService;
import com.team6.ecommerce.comment.Comment;
import com.team6.ecommerce.comment.CommentService;
import com.team6.ecommerce.product.Product;
import com.team6.ecommerce.product.ProductRepository;
import com.team6.ecommerce.product.ProductService;
import com.team6.ecommerce.product.dto.ProductDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pm")
@AllArgsConstructor
@Log4j2
public class ProductManagerController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final ProductRepository productRepo;
    private final CommentService commentService;

//    The product manager is also in the role of delivery department since it controls the stock.
//    This means, the product manager shall view the invoices, products to be delivered, and the corresponding addresses for delivery.
//      A delivery list has the following properties:
//          delivery ID,
//          customer ID,
//          product ID,
//          quantity,
//          total price,
//          delivery address,
//          and a field showing whether the delivery has been completed or not.

    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━KATEGORI KISMI━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//


    //TODO bu direkt Category türünde almamalı sanırım, dto alacak sekilde bu ve servicedeki update metodu degismeli (sanıırm)
    @Secured({"ROLE_ADMIN", "ROLE_PRODUCTMANAGER"})
    @GetMapping("/update-category/{id}")
    public ResponseEntity<?> updateCategory (@PathVariable String id, @RequestBody Category category) {

        return ResponseEntity.ok().body( categoryService.update(id, category));

    }

    @Secured({"ROLE_ADMIN", "ROLE_PRODUCTMANAGER"})
    @GetMapping("/delete-category/{id}")
    public ResponseEntity<?> deleteCategory (@PathVariable String id) {

        return ResponseEntity.ok().body( categoryService.deleteCategory(id));

    }


    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━PRODUCT KISMI━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//

    @Secured({"ROLE_ADMIN", "ROLE_PRODUCTMANAGER"})
    @PostMapping(value = "/product/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addProduct (@RequestBody @Valid ProductDTO productDTO) {

        try {
            Product newProduct = productService.addProduct(productDTO);

            return ResponseEntity.ok(newProduct);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }


    //ChangeStock endpointi



    //━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━COMMENT KISMI━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//

    /**
     * Approve a comment by ID.
     */
    @Secured({"ROLE_PRODUCTMANAGER"})
    @PatchMapping("/comments/{commentId}/approve")
    public ResponseEntity<?> approveComment(@PathVariable String commentId) {
        commentService.approveComment(commentId);
        log.info("[ProductManagerController][approveComment] Comment ID: {} approved successfully.", commentId);
        return ResponseEntity.ok("Comment approved successfully.");
    }

    /**
     * List all unapproved comments.
     */
    @Secured({"ROLE_PRODUCTMANAGER"})
    @GetMapping("/comments/unapproved")
    public ResponseEntity<List<Comment>> listUnapprovedComments() {
        List<Comment> unapprovedComments = commentService.getUnapprovedComments();
        log.info("[ProductManagerController][listUnapprovedComments] Retrieved {} unapproved comments.", unapprovedComments.size());
        return ResponseEntity.ok(unapprovedComments);
    }

    /**
     * List all comments for a product.
     */
    @Secured({"ROLE_PRODUCTMANAGER"})
    @GetMapping("/comments/{productId}")
    public ResponseEntity<List<Comment>> listCommentsByProduct(@PathVariable String productId) {
        List<Comment> comments = commentService.getAllCommentsForProduct(productId);
        log.info("[ProductManagerController][listCommentsByProduct] Retrieved {} comments for product ID: {}", comments.size(), productId);
        return ResponseEntity.ok(comments);
    }







}
