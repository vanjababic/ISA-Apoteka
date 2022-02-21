package com.example.ISAISA.repository;

import com.example.ISAISA.model.Medication;
import com.example.ISAISA.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
}
