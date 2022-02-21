package com.example.ISAISA.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("dermatologist")
public class Dermatologist extends User {

    @JsonIgnore
    @OneToMany(mappedBy = "dermatologist", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Appointment> appointments = new HashSet<Appointment>();

    @JsonIgnore
    @OneToMany(mappedBy = "dermatologist", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Set<Dermatologist_Pharmacyy> dermatologist_pharmacies = new HashSet<Dermatologist_Pharmacyy>();

    @Column
    private Float rating;

    @JsonIgnore
    @OneToMany(mappedBy = "dermatologist", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Vacation> vacations = new HashSet<Vacation>();

    public Set<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }

    public Set<Dermatologist_Pharmacyy> getDermatologist_pharmacies() {
        return dermatologist_pharmacies;
    }

    public void setDermatologist_pharmacies(Set<Dermatologist_Pharmacyy> dermatologist_pharmacies) { this.dermatologist_pharmacies = dermatologist_pharmacies; }

    public Float getRating() { return rating; }

    public void setRating(Float rating) { this.rating = rating; }

    public Dermatologist() {
    }

    public Dermatologist(Integer id, String email, String password, String firstName, String lastName, String address, String phone, String city, String country) {
        super(id, email, password, firstName, lastName, address, phone, city, country);
    }

    public Dermatologist(Integer id, String email, String password, String firstName, String lastName, String address, String phone, String city, String country, Set<Appointment> appointments, Set<Dermatologist_Pharmacyy> dermatologist_pharmacies, Float rating) {
        super(id, email, password, firstName, lastName, address, phone, city, country);
        this.appointments = appointments;
        this.dermatologist_pharmacies = dermatologist_pharmacies;
        this.rating = rating;
    }

    public Set<Vacation> getVacations() { return vacations; }

    public void setVacations(Set<Vacation> vacations) { this.vacations = vacations; }

}
