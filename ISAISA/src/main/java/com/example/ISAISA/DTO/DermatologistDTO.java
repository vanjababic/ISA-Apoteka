package com.example.ISAISA.DTO;

import com.example.ISAISA.model.Pharmacy;

import java.time.LocalTime;
import java.util.Set;

public class DermatologistDTO {

    private Integer id;

    private String firstName;

    private String lastName;

    private String email;

    private Float rating;

    private Set<Pharmacy> pharmacies;

    private LocalTime beginOfWork;

    private LocalTime endOfWork;

    public DermatologistDTO(Integer id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public DermatologistDTO(Integer id, String firstName, String lastName, Float rating) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.rating = rating;
    }

    public DermatologistDTO(Integer id, String firstName, String lastName, Float rating, Set<Pharmacy> pharmacies) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.rating = rating;
        this.pharmacies = pharmacies;
    }

    public DermatologistDTO(Integer id, String firstName, String lastName, Float rating, Set<Pharmacy> pharmacies, LocalTime beginOfWork, LocalTime endOfWork) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.rating = rating;
        this.pharmacies = pharmacies;
        this.beginOfWork = beginOfWork;
        this.endOfWork = endOfWork;
    }
    public DermatologistDTO(String firstName, String lastName, Float rating, Set<Pharmacy> pharmacies) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.rating = rating;
        this.pharmacies = pharmacies;
    }

    public DermatologistDTO() {
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public Float getRating() { return rating; }

    public void setRating(Float rating) { this.rating = rating; }

    public Set<Pharmacy> getPharmacies() { return pharmacies; }

    public void setPharmacies(Set<Pharmacy> pharmacies) { this.pharmacies = pharmacies; }

    public LocalTime getBeginOfWork() { return beginOfWork; }

    public void setBeginOfWork(LocalTime beginOfWork) { this.beginOfWork = beginOfWork; }

    public LocalTime getEndOfWork() { return endOfWork; }

    public void setEndOfWork(LocalTime endOfWork) { this.endOfWork = endOfWork; }
}
