package com.example.ISAISA.repository;

import com.example.ISAISA.model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;


public interface MedicationRepository extends JpaRepository<Medication, Integer> {

   Medication findOneById(Integer id);

   Set<Medication> findAllByName(String name);

   Medication findByName(String name);

   Medication findByCode(String code);

   Medication findByNameIgnoreCase(String name);


}
