package com.example.ISAISA.repository;

import com.example.ISAISA.model.Medication;
import com.example.ISAISA.model.Pharmacist;
import com.example.ISAISA.model.Pharmacy;
import com.example.ISAISA.model.PharmacyMedication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Set;

public interface PharmacyMedicationRepository extends JpaRepository<PharmacyMedication, Integer> {
    Set<PharmacyMedication> findAllByMedicationAndBeginPriceValidityBeforeAndEndPriceValidityAfter(Medication medication, LocalDate localDate,LocalDate localDate1);
    Set<PharmacyMedication> findAllByPharmacyAndMedication(Pharmacy pharmacy, Medication medication);


    Set<PharmacyMedication> findAllByPharmacy(Pharmacy pharmacy);

    //PharmacyMedication findOneByPharmacyAndMedication(Pharmacy pharmacy, Medication medication);

    PharmacyMedication findOneByPharmacyAndMedicationAndBeginPriceValidityBeforeAndEndPriceValidityAfter(Pharmacy pharmacy, Medication medication, LocalDate before, LocalDate after);

    Set<PharmacyMedication> findByPharmacyAndBeginPriceValidityBeforeAndEndPriceValidityAfter(Pharmacy pharmacy, LocalDate before, LocalDate after);

    //Set<PharmacyMedication> findOneByPharmacyAndBeginPriceValidityLessThanEqualAndEndPriceValidityGreaterThanEqual(Pharmacy pharmacy, LocalDate before, LocalDate after);

    //PharmacyMedication findOneByPharmacyAndMedicationAndBeginPriceValidityLessThanEqualAndEndPriceValidityGreaterThanEqual(Pharmacy pharmacy, Medication medication, LocalDate before, LocalDate after);

    Set<PharmacyMedication> findByPharmacyAndMedicationOrderByBeginPriceValidityDesc(Pharmacy pharmacy, Medication medication);

    Set<PharmacyMedication> findByMedicationOrderByBeginPriceValidityDesc(Medication medication);


}
