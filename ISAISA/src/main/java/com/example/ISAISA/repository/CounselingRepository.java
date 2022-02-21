package com.example.ISAISA.repository;

import com.example.ISAISA.model.Counseling;
import com.example.ISAISA.model.Patient;
import com.example.ISAISA.model.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import com.example.ISAISA.model.Pharmacist;
import java.util.Set;

public interface CounselingRepository extends JpaRepository<Counseling, Integer> {
    List<Counseling> findAllByPatient(Patient patient);

    Set<Counseling> findByPharmacist(Pharmacist pharmacist);

    Counseling findOneById(Integer id);
    List<Counseling> findAllByPharmacist(Pharmacist pharmacist);
    Set<Counseling> findAllByPharmacistAndBeginofappointmentAfter(Pharmacist pharmacist, LocalDateTime localDate);
    Set<Counseling> findAllByPatientOrderByPrice(Patient patient);

}
