package com.example.ISAISA.service;

import com.example.ISAISA.repository.Dermatologist_PharmacyyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Dermatologist_PharmacyyService {

    Dermatologist_PharmacyyRepository dermatologist_pharmacyyRepository;

    @Autowired
    public void setDermatologist_pharmacyyRepository(Dermatologist_PharmacyyRepository dermatologist_pharmacyyRepository) {
        this.dermatologist_pharmacyyRepository = dermatologist_pharmacyyRepository;
    }
}
