package com.example.ISAISA.repository;

import com.example.ISAISA.model.Dermatologist;
import com.example.ISAISA.model.Dermatologist_Pharmacyy;
import com.example.ISAISA.model.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface Dermatologist_PharmacyyRepository extends JpaRepository<Dermatologist_Pharmacyy, Integer> {

    //List<Dermatologist_Pharmacyy> findAllBy();
    Set<Dermatologist_Pharmacyy> findAllByPharmacy(Pharmacy pharmacy);

    Dermatologist_Pharmacyy findByDermatologistAndPharmacy(Dermatologist dermatologist, Pharmacy pharmacy);

    Set<Dermatologist_Pharmacyy> findAllByDermatologist(Dermatologist dermatologist);
}
