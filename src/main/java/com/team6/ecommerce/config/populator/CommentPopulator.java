package com.team6.ecommerce.config.populator;

import com.github.javafaker.Faker;

import com.team6.ecommerce.comment.Comment;
import com.team6.ecommerce.user.User;
import com.team6.ecommerce.user.UserRepository;
import com.team6.ecommerce.comment.CommentService;
import com.team6.ecommerce.comment.CommentRepository;
import com.team6.ecommerce.product.ProductRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;



@Log4j2
@AllArgsConstructor
@Component
@DependsOn({"dataPopulator"})
public class CommentPopulator {


    private final ProductRepository productRepo;
    private final CommentRepository commentRepo;
    private final CommentService commentService;
    private final BCryptPasswordEncoder encoder;

    private final Faker fake = new Faker();
    private final UserRepository userRepository;

    @PostConstruct
    public void init() {

        log.info("[CommentPopulator] Clearing Comment Collection.");
        commentRepo.deleteAll();

        List<User> users = userRepository.findAll();



        List<Comment> comments = Arrays.asList(
                new Comment("1", users.get(0).getId(), "Amazing product! Worth every penny.", 4, true),
                new Comment("2", users.get(0).getId(), "The quality is not as expected, but still decent.", 3, true),
                new Comment("3", users.get(2).getId(), "Highly recommend this to everyone.", 5, true),
                new Comment("1", users.get(3).getId(), "Satisfactory performance for the price.", 4, true),
                new Comment("1", users.get(1).getId(), "Not happy with the purchase, avoid if possible.", 2, true),
                new Comment("3", users.get(1).getId(), "Great value for money, very happy!", 5, true),
                new Comment("2", users.get(3).getId(), "It was okay, not too great.", 3, true),
                new Comment("2", users.get(5).getId(), "Exceeded my expectations, fantastic!", 5, true),
                new Comment("3", users.get(6).getId(), "Good product but delivery was delayed.", 4, true),
                new Comment("1", users.get(2).getId(), "Perfect for my needs, absolutely love it.", 5, true),
                new Comment("3", users.get(3).getId(), "Could be better, but still usable.", 3, true),
                new Comment("4", users.get(2).getId(), "Fantastic build quality, would buy again.", 5, true),
                new Comment("3", users.get(5).getId(), "Not bad, but there are better options.", 3, true),
                new Comment("2", users.get(2).getId(), "Loved it, will definitely recommend!", 5, true),
                new Comment("2", users.get(3).getId(), "Just average, nothing extraordinary.", 3, true),
                new Comment("1", users.get(2).getId(), "Superb product, my family loved it!", 5, true),
                new Comment("4", users.get(0).getId(), "Very durable and reliable, highly recommended!", 5, true),
                new Comment("5", users.get(1).getId(), "Not worth the price, disappointing experience.", 2, true),
                new Comment("6", users.get(4).getId(), "Perfect for casual use, good value.", 4, true),
                new Comment("7", users.get(3).getId(), "The performance is great, but the packaging could be better.", 4, true),
                new Comment("8", users.get(5).getId(), "A bit expensive, but the features justify it.", 4, true),
                new Comment("9", users.get(0).getId(), "Doesn't meet expectations, lacks features.", 2, true),
                new Comment("10", users.get(2).getId(), "Incredible product! Will buy again.", 5, true),
                new Comment("11", users.get(4).getId(), "Average quality, wouldn't buy again.", 3, true),
                new Comment("12", users.get(6).getId(), "Exceptional quality, exceeded my expectations.", 5, true),
                new Comment("13", users.get(1).getId(), "Just okay, but could be better for the price.", 3, true),
                new Comment("14", users.get(4).getId(), "Best purchase I've made in a while.", 5, true),
                new Comment("15", users.get(3).getId(), "Satisfied with the product, but delivery was slow.", 3, true),
                new Comment("16", users.get(5).getId(), "Great features, but some bugs need fixing.", 4, true),
                new Comment("17", users.get(0).getId(), "Decent for the price, but there are better options.", 3, true),
                new Comment("18", users.get(6).getId(), "Perfect for my needs, would recommend.", 5, true),
                new Comment("19", users.get(1).getId(), "Average product, expected better performance.", 2, true),
                new Comment("20", users.get(4).getId(), "High-quality product, would recommend to others.", 5, true),
                new Comment("21", users.get(2).getId(), "Meh, it's okay but not great.", 3, true),
                new Comment("22", users.get(3).getId(), "Definitely worth the money, very pleased.", 5, true),
                new Comment("23", users.get(5).getId(), "Wouldn't recommend, lots of issues.", 2, true),
                new Comment("24", users.get(0).getId(), "Solid product, no complaints.", 4, true),
                new Comment("25", users.get(6).getId(), "Exceeded my expectations, definitely buying again.", 5, true),
                new Comment("26", users.get(2).getId(), "The performance is good, but there are better options.", 3, true),
                new Comment("27", users.get(4).getId(), "Love it, the quality is superb!", 5, true),
                new Comment("28", users.get(1).getId(), "I would avoid this product, poor quality.", 1, true),
                new Comment("29", users.get(3).getId(), "Excellent product, I'm very happy with it.", 5, true),
                new Comment("30", users.get(5).getId(), "Quite happy with it, good value for money.", 4, true),
                new Comment("31", users.get(0).getId(), "Totally disappointing, waste of money.", 1, true),
                new Comment("32", users.get(1).getId(), "Terrible quality, didn't work at all.", 1, true),
                new Comment("33", users.get(2).getId(), "Worst purchase I've made in years.", 1, true),
                new Comment("34", users.get(3).getId(), "The product is broken, no good.", 1, true),
                new Comment("35", users.get(4).getId(), "Very bad experience, will never buy again.", 1, true),
                new Comment("36", users.get(5).getId(), "Horrible, would not recommend to anyone.", 1, true),
                new Comment("37", users.get(6).getId(), "The worst product ever, complete failure.", 1, true)
        );




        commentRepo.saveAll(comments);
    }

}