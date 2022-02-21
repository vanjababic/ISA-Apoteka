package com.example.ISAISA.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalTime;

import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("pharmacist")
public class Pharmacist extends User {

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Pharmacy pharmacy;

    @Column
    private Float rating;

    @Column
    private LocalTime beginofwork;

    @Column
    private LocalTime endofwork;

    @JsonIgnore
    @OneToMany(mappedBy = "pharmacist", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private Set<Counseling> counselings = new HashSet<Counseling>();

    @JsonIgnore
    @OneToMany(mappedBy = "pharmacist", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Vacation> vacations = new HashSet<Vacation>();

    public Pharmacist(String email, String password, String firstName, String lastName, String address, String phone, String city, String country, Pharmacy pharmacy, Float rating, LocalTime beginofwork, LocalTime endofwork) {
        super(email, password, firstName, lastName, address, phone, city, country);
        this.pharmacy = pharmacy;
        this.rating = rating;
        this.beginofwork = beginofwork;
        this.endofwork = endofwork;
    }

    public LocalTime getBeginofwork() {
        return beginofwork;
    }

    public void setBeginofwork(LocalTime beginofwork) {
        this.beginofwork = beginofwork;
    }

    public LocalTime getEndofwork() {
        return endofwork;
    }

    public void setEndofwork(LocalTime endofwork) {
        this.endofwork = endofwork;
    }

    public Pharmacist() {
    }

    public Pharmacist(Integer id, String email, String password, String firstName, String lastName, String address, String phone, String city, String country, Pharmacy pharmacy, Float rating) {
        super(id, email, password, firstName, lastName, address, phone, city, country);
        this.pharmacy = pharmacy;
        this.rating = rating;
    }

    public Float getRating() { return rating; }

    public void setRating(Float rating) { this.rating = rating; }

    public Pharmacy getPharmacy() { return pharmacy; }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }

    public Set<Counseling> getCounselings() { return counselings; }

    public void setCounselings(Set<Counseling> counselings) { this.counselings = counselings; }

    public Set<Vacation> getVacations() { return vacations; }

    public void setVacations(Set<Vacation> vacations) { this.vacations = vacations; }
}
