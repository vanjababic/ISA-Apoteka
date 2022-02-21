package com.example.ISAISA.repository;


import com.example.ISAISA.model.*;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface OfferRepository extends CrudRepository<Offer, String> {
    Offer findOneByOrderrAndSupplier (Orderr orderr, Supplier supplier);

    List<Offer> findAllByStatusSupplier(String status);

    Set<Offer> findByOrderr(Orderr orderr);

    Set<Offer> findAll();

    Offer findOneById(Integer id);

    Set<Offer> findByOrderrAndStatusSupplier(Orderr orderr, String status);
}
