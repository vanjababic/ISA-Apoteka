package com.example.ISAISA.controller;

import com.example.ISAISA.service.PharmacyMedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/pharmacyMedications")
public class PharmacyMedicationController {
    private PharmacyMedicationService pharmacyMedicationService;

    @Autowired
    public void setPharmacyMedicationService(PharmacyMedicationService pharmacyMedicationService) { this.pharmacyMedicationService = pharmacyMedicationService; }
}
