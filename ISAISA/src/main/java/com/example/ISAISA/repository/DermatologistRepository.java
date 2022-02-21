package com.example.ISAISA.repository;

import com.example.ISAISA.model.Dermatologist;
import com.example.ISAISA.model.Dermatologist_Pharmacyy;
import com.example.ISAISA.model.Pharmacist;
import com.example.ISAISA.model.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface DermatologistRepository extends JpaRepository<Dermatologist, Integer> {

    Dermatologist findOneById(Integer id);

    Dermatologist findOneByEmail(String email);

    Set<Dermatologist> findAllByFirstNameIgnoreCaseAndLastNameIgnoreCase (String firstName, String lastName);

    Set<Dermatologist> findAllByRatingBetween(Float ratingOver, Float ratingUnder);

    Set<Dermatologist> findAllBy();
}
