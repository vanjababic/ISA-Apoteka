package com.example.ISAISA.DTO;

public class FilterEmployeesDTO {

    private Float ratingOver;
    private Float ratingUnder;
    private String pharmacyName;

    public FilterEmployeesDTO() {
    }

    public FilterEmployeesDTO(Float ratingOver, Float ratingUnder, String pharmacyName) {
        this.ratingOver = ratingOver;
        this.ratingUnder = ratingUnder;
        this.pharmacyName = pharmacyName;
    }

    public FilterEmployeesDTO(Float ratingOver, Float ratingUnder) {
        this.ratingOver = ratingOver;
        this.ratingUnder = ratingUnder;
    }

    public Float getRatingOver() { return ratingOver; }

    public void setRatingOver(Float ratingOver) { this.ratingOver = ratingOver; }

    public Float getRatingUnder() { return ratingUnder; }

    public void setRatingUnder(Float ratingUnder) { this.ratingUnder = ratingUnder; }

    public String getPharmacyName() { return pharmacyName; }

    public void setPharmacyName(String pharmacyName) { this.pharmacyName = pharmacyName; }
}
