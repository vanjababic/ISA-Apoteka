package com.example.ISAISA.controller;

import com.example.ISAISA.service.Dermatologist_PharmacyyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/dermatologist_pharmacies")
public class Dermatologist_PharmacyyController {

    private Dermatologist_PharmacyyService dermatologist_pharmacyyService;

    @Autowired
    public void setDermatologist_pharmacyyService(Dermatologist_PharmacyyService dermatologist_pharmacyyService) {
        this.dermatologist_pharmacyyService = dermatologist_pharmacyyService;
    }
}
