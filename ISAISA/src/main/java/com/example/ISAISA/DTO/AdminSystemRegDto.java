package com.example.ISAISA.DTO;

public class AdminSystemRegDto {
    private String firstName;

    private String lastName;
    private String password;

    private String address;
    private String email;
    private String city;
    private String country;
    private String phone;
    private Integer pharmacyID;


    public AdminSystemRegDto(String firstName, String lastName, String password, String address, String email, String city, String country, String phone, Integer pharmacyID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.address = address;
        this.email = email;
        this.city = city;
        this.country = country;
        this.phone = phone;
        this.pharmacyID = pharmacyID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AdminSystemRegDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getPharmacyID() {
        return pharmacyID;
    }

    public void setPharmacyID(Integer pharmacyID) {
        this.pharmacyID = pharmacyID;
    }
}
