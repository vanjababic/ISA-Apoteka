package com.example.ISAISA.model;

public class PatientDto {

    private String namee;

    private String lastName;

    private String password;

    private String positionn;

    private String adress;

    private String email;

    private String phoneNumber;

    private String country;

    private String city;


    public PatientDto(String namee, String lastName, String password, String positionn, String adress, String email, String phoneNumber, String country, String city) {
        this.namee = namee;
        this.lastName = lastName;
        this.password = password;
        this.positionn = positionn;
        this.adress = adress;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.country = country;
        this.city = city;
    }

    public String getNamee() {
        return namee;
    }

    public void setNamee(String namee) {
        this.namee = namee;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPositionn() {
        return positionn;
    }

    public void setPositionn(String positionn) {
        this.positionn = positionn;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
