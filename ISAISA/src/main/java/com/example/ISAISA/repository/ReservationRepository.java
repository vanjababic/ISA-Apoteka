package com.example.ISAISA.repository;

import com.example.ISAISA.model.Medication;
import com.example.ISAISA.model.Pharmacy;
import com.example.ISAISA.model.Promotion;
import com.example.ISAISA.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.parameters.P;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    Reservation findOneByid(Integer id);

    Reservation findOneByMedicationAndPharmacy(Medication medication, Pharmacy pharmacy);
}
