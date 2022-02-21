package com.example.ISAISA.DTO;

import javax.persistence.Column;

public class UserChangeDTO {

    private String firstName;

    private String lastName;

    private String address;

    private String phone;

    private String city;

    private String country;

    public UserChangeDTO() {
    }

    public UserChangeDTO(String firstName, String lastName, String address, String phone, String city, String country) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
        this.city = city;
        this.country = country;
    }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public String getCity() { return city; }

    public void setCity(String city) { this.city = city; }

    public String getCountry() { return country; }

    public void setCountry(String country) { this.country = country; }
}
