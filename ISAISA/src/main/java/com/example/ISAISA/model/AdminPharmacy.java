package com.example.ISAISA.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@DiscriminatorValue("adminpharmacy")
public class AdminPharmacy extends User {

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Pharmacy pharmacy;

    @Transient
    @OneToOne(mappedBy = "adminPharmacy", cascade = CascadeType.ALL)
    private Orderr orderr;

    public AdminPharmacy() {
    }

    public AdminPharmacy(Integer id, String email, String password, String firstName, String lastName, String address, String phone, String city, String country) {
        super(id, email, password, firstName, lastName, address, phone, city, country);
    }

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }
}
