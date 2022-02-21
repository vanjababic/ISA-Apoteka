package com.example.ISAISA.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("supplier")
public class Supplier extends User{

    public Supplier() {
    }

    public Supplier(Integer id, String email, String password, String firstName, String lastName, String address, String phone, String city, String country) {
        super(id, email, password, firstName, lastName, address, phone, city, country);
    }

    @JsonIgnore
    @OneToMany(mappedBy = "supplier", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Offer> offers = new HashSet<Offer>();


    @JsonIgnore
    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name = "supplier_medication", joinColumns = @JoinColumn(name = "supplier_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "medication_id", referencedColumnName = "id"))
    private Set<Medication> medication = new HashSet<Medication>();

    public Set<Offer> getOffers() {
        return offers;
    }

    public void setOffers(Set<Offer> offers) {
        this.offers = offers;
    }

    public Set<Medication> getMedication() {
        return medication;
    }

    public void setMedication(Set<Medication> medication) {
        this.medication = medication;
    }
}
