package com.example.ISAISA.repository;

import com.example.ISAISA.model.Medication;
import com.example.ISAISA.model.Orderr;
import com.example.ISAISA.model.Orderr_Medication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface OrderMedicationRepository extends JpaRepository<Orderr_Medication, Integer> {

}
