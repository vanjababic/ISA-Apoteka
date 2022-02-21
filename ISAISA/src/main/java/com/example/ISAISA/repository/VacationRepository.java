package com.example.ISAISA.repository;

import com.example.ISAISA.model.Dermatologist;
import com.example.ISAISA.model.Vacation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Set;

public interface VacationRepository extends JpaRepository<Vacation, Integer> {

    Set<Vacation> findAllByPharmacistNotNullAndApprovedNullAndDateBeginVacationAfter(LocalDate now);

    Set<Vacation> findAllByDermatologistNotNullAndApprovedNullAndDateBeginVacationAfter(LocalDate now);

    Vacation findOneById(Integer id);

    Set<Vacation> findAllByDermatologistAndApprovedTrue(Dermatologist dermatologist);

}
