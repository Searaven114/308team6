package com.team6.ecommerce.config.populator;


import com.github.javafaker.Faker;
import com.team6.ecommerce.distributor.Distributor;
import com.team6.ecommerce.distributor.DistributorRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;


@AllArgsConstructor
@Component
public class DistributorPopulator {

    private final DistributorRepository distributorRepo;

    private final Faker fake = new Faker();


    @PostConstruct
    public void init() {

        distributorRepo.deleteAll();

        List<Distributor> distributors = Arrays.asList(

                new Distributor(
                        "1",
                        "Aral A.Ş",
                        fake.phoneNumber().phoneNumber(),
                        fake.address().fullAddress(),
                        fake.internet().url(),
                        true
                ),
                new Distributor(
                        "2",
                        "Bircom",
                        fake.phoneNumber().phoneNumber(),
                        fake.address().fullAddress(),
                        fake.internet().url(),
                        true
                )
        );

        distributorRepo.saveAll(distributors);
    }
}
