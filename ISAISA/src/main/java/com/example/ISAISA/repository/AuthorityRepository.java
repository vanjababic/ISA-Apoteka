package com.example.ISAISA.repository;

import com.example.ISAISA.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
    Authority findByName(String name);
}

