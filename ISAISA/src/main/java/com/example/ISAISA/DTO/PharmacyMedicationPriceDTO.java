package com.example.ISAISA.DTO;

import com.example.ISAISA.model.Pharmacy;

public class PharmacyMedicationPriceDTO {
    private String name;
    private Integer price;

    public PharmacyMedicationPriceDTO(String name, Integer price) {
        this.name = name;
        this.price = price;
    }

    public PharmacyMedicationPriceDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
