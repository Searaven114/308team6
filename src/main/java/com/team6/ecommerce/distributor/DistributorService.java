package com.team6.ecommerce.distributor;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class DistributorService {

    private final DistributorRepository distributorRepo;


    public List<Distributor> findAll() {
        return distributorRepo.findAll();
    }

    public Optional<Distributor> findById(String id) {
        return distributorRepo.findById(id);
    }

    public Distributor save(Distributor distributor) {
        return distributorRepo.save(distributor);
    }

    public void deleteById(String id) {
        distributorRepo.deleteById(id);
    }

}


