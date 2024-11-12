package com.team6.ecommerce.admin;


import com.team6.ecommerce.user.UserRepository;
import com.team6.ecommerce.user.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@Log4j2
@RequestMapping("/api/admin")
@RestController
public class AdminController {

    private final UserRepository userRepo;
    private final UserService userService;
    //private final OrderService orderService;


//    The user should be able to browse and purchase the products through the website.
//    Additionally, the website should provide an admin interface for managerial tasks.


    @GetMapping("/user/show-all-users")
    public ResponseEntity<?> getUsers(){
        return ResponseEntity.ok().body( userService.getUsers() );
    }


//    @GetMapping("/order/show-all-orders")
//    public ResponseEntity<?> getOrders( @RequestParam(name = "status", required = false) OrderStatus status){
//
//        return ResponseEntity.ok().body( orderService.getAllOrdersAdmin( status ) );
//
//    }

//    @GetMapping("/order/show-orders-by-userid/{id}")
//    public ResponseEntity<?> getOrdersById(
//            @PathVariable String id,
//            @RequestParam( name = "status", required = false) OrderStatus status)
//    {
//        List<Order> orders = orderService.getAllOrdersByUserIdAdmin( id, status );
//        return ResponseEntity.ok().body( orders );
//    }

//    @GetMapping("/users/{userId}")
//    public User getUser(@PathVariable String userId) {
//        // Code to get user details
//        return userService.getUserById(userId);
//    }
//
//    @PostMapping("/users")
//    public User createUser(@RequestBody User user) {
//        // Code to create a new user
//        return userService.createUser(user);
//    }
//
//    @PutMapping("/users/{userId}")
//    public User updateUser(@PathVariable Long userId, @RequestBody User user) {
//        // Code to update user details
//        return userService.updateUser(userId, user);
//    }
//
//    @DeleteMapping("/users/{userId}")
//    public void deleteUser(@PathVariable Long userId) {
//        // Code to delete a user
//        userService.deleteUser(userId);
//    }

//    @PostMapping("/users/{userId}/reset-password")
//    public void resetPassword(@PathVariable String userId) {
//        // Code to reset password
//        userService.resetPassword(userId);
//    }



}
