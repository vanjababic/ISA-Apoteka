package com.example.ISAISA.model;

import javax.persistence.Entity;

@Entity
public abstract class Employee extends User{

    public Employee() {
    }

    public Employee(Integer id, String email, String password, String firstName, String lastName, String address, String phone, String city, String country) {
        super(id, email, password, firstName, lastName, address, phone, city, country);
    }
}
