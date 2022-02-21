package com.example.ISAISA.repository;

import com.example.ISAISA.model.Examination;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExaminationRepository extends JpaRepository<Examination, Integer> {

    Examination findOneById(Integer id);
}
