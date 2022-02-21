package com.example.ISAISA.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("adminsystem")
public class AdminSystem extends User {

    public AdminSystem() {
    }

    public AdminSystem(Integer id, String email, String password, String firstName, String lastName, String address, String phone, String city, String country) {
        super(id, email, password, firstName, lastName, address, phone, city, country);
    }
}
