package com.example.ISAISA.repository;

import com.example.ISAISA.model.AdminPharmacy;
import com.example.ISAISA.model.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface AdminPharmacyRepository extends JpaRepository<AdminPharmacy, Integer> {

    Set<AdminPharmacy> findAllByPharmacy(Pharmacy pharmacy);
}
