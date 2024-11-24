package com.team6.ecommerce.config.populator;


/*
import com.github.javafaker.Faker;
import com.team6.ecommerce.address.Address;
import com.team6.ecommerce.category.CategoryRepository;
import com.team6.ecommerce.distributor.DistributorRepository;
import com.team6.ecommerce.product.ProductRepository;
import com.team6.ecommerce.user.User;
import com.team6.ecommerce.user.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Log4j2
@AllArgsConstructor
@Component
public class UserPopulator {

    private final UserRepository userRepo;
    private final BCryptPasswordEncoder encoder;
    private final ProductRepository productRepo;
    private final DistributorRepository distributorRepo;
    private final CategoryRepository categoryRepo;

    private final Faker fake = new Faker();

    @PostConstruct
    public void init() {

    // Clean the collection to avoid duplicates on restart
    userRepo.deleteAll();
    log.info("[UserPopulator] Clearing User Collection.");


    // Create fake users with addresses
    List<User> users = Arrays.asList(
            new User("fuat", encoder.encode("avni"), "fuat", "05665127700"),
            new User("admin", encoder.encode("adminpw"), "admin@example.com", fake.phoneNumber().phoneNumber()),
            new User("salesmanager", encoder.encode("salespw"), "sales@example.com", fake.phoneNumber().phoneNumber()),
            new User("productmanager", encoder.encode("productpw"), "product@example.com", fake.phoneNumber().phoneNumber()),
            new User("customer1", encoder.encode("customerpw"), "customer1@example.com", fake.phoneNumber().phoneNumber()),
            new User("customer2", encoder.encode("customerpw"), "customer2@example.com", fake.phoneNumber().phoneNumber()),
            new User("customer3", encoder.encode("customerpw"), "customer3@example.com", fake.phoneNumber().phoneNumber())
    );

        for (User user : users) {

            Set<String> roles = new HashSet<>();

            // Assigning roles
            if ("admin@example.com".equals(user.getUsername())) {
                roles.add("ROLE_ADMIN");
                roles.add("ROLE_SALESMANAGER");
                roles.add("ROLE_PRODUCTMANAGER");
                roles.add("ROLE_CUSTOMER");

            } else if ("sales@example.com".equals(user.getUsername())) {
                roles.add("ROLE_SALESMANAGER");
                roles.add("ROLE_CUSTOMER");

            } else if ("product@example.com".equals(user.getUsername())) {
                roles.add("ROLE_PRODUCTMANAGER");
                roles.add("ROLE_CUSTOMER");
            } else {
                roles.add("ROLE_CUSTOMER");
            }
            user.setRoles(roles);

            //Setting mock IPv4 address
            user.setRegisterDate(LocalDateTime.now().toString());
            user.setRegisterIp(fake.internet().ipV4Address());

            //Setting mock taxId
            String taxId = fake.idNumber().valid();
            user.setTaxId(taxId);

            //Setting mock age
            String age = fake.number().digits(3);
            user.setAge(age);

            List<Address> addresses = new ArrayList<>();

            int number = fake.number().numberBetween(1, 10);

            if (number >= 4) {
                addresses.add(new Address(fake.address().streetName(), fake.address().city(), fake.address().zipCode(), fake.address().country()));

            } else if (number < 3) {
                addresses.add(new Address(fake.address().streetName(), fake.address().city(), fake.address().zipCode(), fake.address().country()));
                addresses.add(new Address("Home",fake.address().streetName(), fake.address().city(), fake.address().zipCode(), fake.address().country(), fake.lorem().characters(30)));

            } else {
                addresses.add(new Address(fake.address().streetName(), fake.address().city(), fake.address().zipCode(), fake.address().country()));
                addresses.add(new Address(fake.address().streetName(), fake.address().city(), fake.address().zipCode(), fake.address().country()));
                addresses.add(new Address("Home",fake.address().streetName(), fake.address().city(), fake.address().zipCode(), fake.address().country(), fake.lorem().characters(25)));
            }

            //Setting mock addresses
            user.setAddresses(addresses);

        }


        // Save users with addresses
        userRepo.saveAll(users);

    }
}*/
