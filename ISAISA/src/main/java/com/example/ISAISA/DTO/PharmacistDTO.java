package com.example.ISAISA.DTO;

import com.example.ISAISA.model.Pharmacy;

import javax.persistence.Column;
import java.time.LocalTime;

public class PharmacistDTO {

    private Integer id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String address;

    private String phone;

    private String city;

    private String country;

    private Pharmacy pharmacy;

    private Float rating;

    private LocalTime beginofwork;

    private LocalTime endofwork;

    public PharmacistDTO() {
    }

    public PharmacistDTO(Integer id, String firstName, String lastName, Pharmacy pharmacy, Float rating) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.pharmacy = pharmacy;
        this.rating = rating;
    }

    public PharmacistDTO(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public PharmacistDTO(String firstName, String lastName, String email, String password, String address, String phone, String city, String country, Pharmacy pharmacy) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phone = phone;
        this.city = city;
        this.country = country;
        this.pharmacy = pharmacy;
    }

    public PharmacistDTO(String firstName, String lastName, String email, String password, String address, String phone, String city, String country, Pharmacy pharmacy, LocalTime beginofwork, LocalTime endofwork) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phone = phone;
        this.city = city;
        this.country = country;
        this.pharmacy = pharmacy;
        this.beginofwork = beginofwork;
        this.endofwork = endofwork;
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public Pharmacy getPharmacy() { return pharmacy; }

    public void setPharmacy(Pharmacy pharmacy) { this.pharmacy = pharmacy; }

    public Float getRating() { return rating; }

    public void setRating(Float rating) { this.rating = rating; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public String getCity() { return city; }

    public void setCity(String city) { this.city = city; }

    public String getCountry() { return country; }

    public void setCountry(String country) { this.country = country; }

    public LocalTime getBeginofwork() { return beginofwork; }

    public void setBeginofwork(LocalTime beginofwork) { this.beginofwork = beginofwork; }

    public LocalTime getEndofwork() { return endofwork; }

    public void setEndofwork(LocalTime endofwork) { this.endofwork = endofwork; }
}