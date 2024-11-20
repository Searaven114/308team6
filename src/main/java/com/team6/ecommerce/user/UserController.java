package com.team6.ecommerce.user;


import com.team6.ecommerce.address.AddressDTO;
import com.team6.ecommerce.user.dto.*;
import com.team6.ecommerce.exception.UserRegistrationException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Log4j2
@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepo;


    @PostMapping(value = "/user", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerUser(@RequestBody @Valid UserRegistrationDTO dto) {
        try {
            UserResponseDTO response = userService.registerUser(dto);
            return ResponseEntity.ok().body(response);
        } catch (UserRegistrationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

//    @PostMapping(value = "/user/update", consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> updateUser(@RequestBody @Valid )


    @Secured({"ROLE_CUSTOMER", "ROLE_ADMIN", "ROLE_SALESMANAGER", "ROLE_PRODUCTMANAGER", "PRODUCTMANAGER"})
    @GetMapping("/user/profile")
    public ResponseEntity<?> showProfile() {
        try {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                authentication.getAuthorities().forEach(authority -> {
                    log.debug("Authority: " + authority.getAuthority());
                });
            }

            ProfileDTO profile = userService.getProfile();
            return ResponseEntity.ok().body(profile);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("USER NOT FOUND");
        }
    }

    //TODO BUNU ADMIN CONTROLLERINE TAŞI ! USERSERVICE ORADA CAGRILSIN
//    @Secured({"ROLE_ADMIN"})
//    @DeleteMapping("/user/{id}")
//    public ResponseEntity<?> deleteUserByAdmin(@PathVariable(required = true) String id) {
//
//
//        User userToDelete = userRepo.findById(id).orElse(null);
//
//        if (userToDelete == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
//        }
//
//        userService.deleteProfileAdmin(id);
//        return ResponseEntity.ok("User deleted successfully");
//    }


    @DeleteMapping("/user")
    public ResponseEntity<?> deleteOwnProfile() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String mail = authentication.getName();

        User user = userRepo.findByEmail(mail);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        userService.deleteProfile(user.getId());
        return ResponseEntity.ok("Your profile has been deleted successfully");
    }


    @PostMapping("/user/address")
    public ResponseEntity<?> addAddressToUser(@RequestBody @Valid AddressDTO dto) {

        // Retrieve the authenticated user from the security context
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // Check if the authentication object is null or not authenticated
        if (auth == null || !auth.isAuthenticated()) {
            log.warn("Unauthorized access attempt: No authentication found or user not authenticated");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated");
        }

        // Ensure that the principal is of type User
        if (!(auth.getPrincipal() instanceof User)) {
            log.warn("Unauthorized access attempt: Invalid principal type for user: {}", auth.getPrincipal());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid user type");
        }

        // Cast the principal to the User class
        User user = (User) auth.getPrincipal();

        // Double-check if the user object is null
        if (user == null) {
            log.error("Unexpected error: Authenticated principal is null");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authenticated user not found");
        }

        // Proceed with the service logic and add the address
        log.info("User {} is adding a new address", user.getEmail());
        return userService.addAddress(user.getId(), dto);
    }
    // TODO "If you want more control over validation, you can create a custom @ExceptionHandler method for MethodArgumentNotValidException to catch validation errors globally."











}
