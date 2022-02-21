package com.example.ISAISA.repository;

import com.example.ISAISA.model.Patient;
import com.example.ISAISA.model.Pharmacist;
import com.example.ISAISA.model.Pharmacy;
import com.example.ISAISA.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PatientRepository extends JpaRepository<Patient, Integer> {

    @Override
    Optional<Patient> findById(Integer integer);

    List<Patient> findAllBy();
    Set<Patient> findAllByFirstNameIgnoreCaseAndLastNameIgnoreCase(String firstName, String lastName);

}
