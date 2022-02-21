package com.example.ISAISA.DTO;

import java.util.List;

public class MedicationsForPrescriptionDto {

    private String name;

    public MedicationsForPrescriptionDto(String name) {
        this.name = name;
    }

    public MedicationsForPrescriptionDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
