package com.team6.ecommerce.user;


import com.github.javafaker.Bool;
import com.team6.ecommerce.address.Address;
import com.team6.ecommerce.address.AddressDTO;
import com.team6.ecommerce.user.dto.*;
import com.team6.ecommerce.exception.UserRegistrationException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.web.bind.annotation.*;

@Log4j2
@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepo;


    @PostMapping(value = "/user/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerUser(@RequestBody @Valid UserRegistrationDTO dto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null ) {
            log.info("User {} is trying to register", authentication.getName());
        }

//        if (authentication != null && authentication.isAuthenticated()) {
//            log.warn("[UserController][registerUser] Attempt to register while already logged in by user: {}", authentication.getName());
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Logged-in users cannot register.");
//        }

        try {
            String response = userService.registerUser(dto);
            if (response.equals("User created successfully")) {
                return ResponseEntity.ok().body(response);
            };
        } catch (UserRegistrationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred");
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
                    log.info("Authority: " + authority.getAuthority());
                });
            }

            ProfileDTO profile = userService.getProfile();
            return ResponseEntity.ok().body(profile);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("USER NOT FOUND");
        }
    }

    @GetMapping("/user/address")
    public ResponseEntity<?> getUserAddress() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                log.warn("Unauthorized access: No authentication.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated");
            }

            if (!(authentication.getPrincipal() instanceof User)) {
                log.warn("Invalid principal type: {}", authentication.getPrincipal());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid user type");
            }

            User user = (User) authentication.getPrincipal();

            Address address = user.getAddresses().get( user.getAddresses().size() - 1);

            AddressDTO dto = AddressDTO.builder().street(address.getStreet())
                    .city(address.getStreet())
                    .zipCode(address.getZipCode())
                    .country( address.getCountry())
                    .notes( address.getNotes())
                    .build();

            if (address == null) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User has not added an address");
            }

            return ResponseEntity.ok().body(dto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
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


//    @DeleteMapping("/user/delete")
//    public ResponseEntity<?> deleteOwnProfile() {
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String mail = authentication.getName();
//
//        User user = userRepo.findByEmail(mail);
//
//        if (user == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
//        }
//
//        userService.deleteProfile(user.getId());
//        return ResponseEntity.ok("Your profile has been deleted successfully");
//    }

    //todo register -> add address -> checkout seneryosu
    @PostMapping("/account/address")
    public ResponseEntity<?> addAddressToAccount(@RequestBody @Valid AddressDTO dto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            log.warn("Unauthorized access: No authentication.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated");
        }

        if (!(auth.getPrincipal() instanceof User)) {
            log.warn("Invalid principal type: {}", auth.getPrincipal());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid user type");
        }

        User user = (User) auth.getPrincipal();

        if (user == null) {
            log.error("Unexpected error: Authenticated principal is null");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authenticated user not found");
        }

        log.info("User {} is adding a new address", user.getEmail());
        return userService.addAddress(user.getId(), dto);
    }





    @GetMapping("/user/authcheck")
    public Boolean checkAuthStatus(){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null){

            log.info("[UserController][checkAuthStatus] auth fail reported due to auth being null.");
            return false;

        }

        if (!auth.isAuthenticated()){
            log.info("[UserController][checkAuthStatus] auth fail reported due to isAuthentication being false.");
            return false;
        }

        log.info("[UserController][checkAuthStatus] auth succeeded.");
        return true;
    }




}
