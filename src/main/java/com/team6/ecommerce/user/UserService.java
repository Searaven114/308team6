package com.team6.ecommerce.user;



import com.team6.ecommerce.address.Address;
import com.team6.ecommerce.address.AddressDTO;
import com.team6.ecommerce.user.dto.ProfileDTO;
import com.team6.ecommerce.user.dto.UserRegistrationDTO;
import com.team6.ecommerce.user.dto.UserResponseDTO;
import com.team6.ecommerce.exception.UserNotFoundException;
import com.team6.ecommerce.exception.UserRegistrationException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Log4j2
@AllArgsConstructor
@Service
public class UserService{

    private final UserRepository userRepo;
    private final BCryptPasswordEncoder encoder;

    @Secured({"ROLE_ADMIN"})
    public List<User> getUsers(){
        return userRepo.findAll();
    }


    public String registerUser(UserRegistrationDTO dto) throws UserRegistrationException {

        User check = userRepo.findByEmail( dto.getEmail() );

        if (dto.getEmail() == null || dto.getEmail().isEmpty()) {
            throw new UserRegistrationException("Email cannot be null or empty!");
        }

        if (dto.getPassword() == null || dto.getPassword().isEmpty()) {
            throw new UserRegistrationException("Password cannot be null or empty!");
        }

        if (check != null){
            throw new UserRegistrationException("User already exists!");
        }

    //------------------ If and only if the received DTO passes all validation process we proceed from here: ------------------If
        User newUser = new User();

        newUser.setEmail(dto.getEmail());

        log.info("(DEBUG)(UserService.java) Received password : \"" + dto.getPassword() + "\" from user : " + dto.getEmail());
        newUser.setPassword( encoder.encode( dto.getPassword() ) );

        newUser.setName( dto.getName() );

        newUser.setSurname( dto.getSurname() );

        newUser.setPhone( dto.getPhone() );

        newUser.setAge( dto.getAge() );

        newUser.setAddresses( new ArrayList<Address>() ); //todo

        Set<String> roles = new HashSet<>();
        roles.add("ROLE_CUSTOMER"); //ROLE_CUSTOMER, ROLE_ADMIN, ROLE_SALESMANAGER, ROLE_PRODUCTMANAGER
        newUser.setRoles(roles);

        //ilk kayıt ipsi yasa geregi tutmakla yukumlu cogu firma afaik
        String ip = "139.108.14.66";
        newUser.setRegisterIp(ip);

        LocalDateTime temp = LocalDateTime.now();
        newUser.setRegisterDate(temp.toString());

        userRepo.save(newUser);

        log.info("[UserService] User has been saved: " + newUser.toString());

        //return new UserResponseDTO("Success", "User created",null);
        return "User created successfully";
    }



    public ProfileDTO getProfile(String userId) {

        User user = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));

        return ProfileDTO.builder()
                .age(user.getAge())
                .name(user.getName())
                .email(user.getEmail())
                .surname(user.getSurname())
                .phone(user.getPhone())
                .registerDate(user.getRegisterDate())
                .build();
    }


//    public void deleteProfile( String id ){
//        if (id != null){
//            //TODO Burada o userden kalan her şeyin bağlantılı olarak silinmesi gerekiyor yetim kalmaması için,
//            //TODO mesela adresleri, Cartı, mail gönderilecekler listesi,
//            //TODO şimdi fark ettim ki, silmek yerine UserDetails interfacesinden inherit ettiğimiz "isActive" değişkeni var, bunu 1 -> 0 yapmamız lazım
//            //TODO "silinmiş" kabul etmek için, gerçekte de öyle, komple DB den silmek yasal degil, 3 ay muhafaza edilmesi gerekiyor by default, veri istem talebi gelirse
//            //TODO daha uzun süre muhafaza edilebiliyor. biz sadece Boolean isActive = false; yaparız kullanıcıdan silme isteği gelirse.
//        }
//
//        throw new UserNotFoundException("User not found");
//    }


    public ResponseEntity<?> addAddress(String userId, AddressDTO dto) {

        User user = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException("[UserService] User not found"));

        Address address = new Address();
        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setZipCode(dto.getZipCode());
        address.setCountry(dto.getCountry());

        user.getAddresses().add(address);
        userRepo.save(user);

        return ResponseEntity.ok("Address added successfully");
    }



}
