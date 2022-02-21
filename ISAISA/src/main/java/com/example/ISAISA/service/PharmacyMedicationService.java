package com.example.ISAISA.service;

import com.example.ISAISA.model.Medication;
import com.example.ISAISA.model.Pharmacy;
import com.example.ISAISA.model.PharmacyMedication;
import com.example.ISAISA.repository.PharmacyMedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class PharmacyMedicationService {

    private PharmacyMedicationRepository pharmacyMedicationRepository;

    @Autowired
    public void setPharmacyMedicationRepository(PharmacyMedicationRepository pharmacyMedicationRepository) { this.pharmacyMedicationRepository = pharmacyMedicationRepository; }

    public PharmacyMedication findByPharmacyAndMedication(Pharmacy pharmacy, Medication medication) {
        Set<PharmacyMedication> pharmacyMedications = pharmacyMedicationRepository.findByPharmacyAndMedicationOrderByBeginPriceValidityDesc(pharmacy, medication);
        List<PharmacyMedication> pharmacyMedications1 = new ArrayList<>(pharmacyMedications);
        PharmacyMedication pharmacyMedication = null;
        if (!pharmacyMedications1.isEmpty()){
            pharmacyMedication = pharmacyMedications1.get(0);
        } 

        return pharmacyMedication;
    }

    public Set<PharmacyMedication> findByPharmacy(Pharmacy pharmacy) {
        return this.pharmacyMedicationRepository.findByPharmacyAndBeginPriceValidityBeforeAndEndPriceValidityAfter(pharmacy, LocalDate.now(), LocalDate.now());
    }

}
