package com.example.ISAISA.repository;

import com.example.ISAISA.model.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComplaintsRepository extends JpaRepository<Complaint, Integer> {
    List<Complaint> findAllByIshospital (Boolean bool);
}
