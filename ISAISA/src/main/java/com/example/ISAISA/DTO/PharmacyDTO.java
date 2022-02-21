package com.example.ISAISA.DTO;

public class PharmacyDTO {

    private  Integer id;

    private String name;

    private String address;

    private String description;

    private Float rating;

    public PharmacyDTO(Integer id, String name, String address, String description, Float rating) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.description = description;
        this.rating = rating;
    }

    public PharmacyDTO(String name, String address, String description) {
        this.name = name;
        this.address = address;
        this.description = description;
    }

    public PharmacyDTO(String name) {
        this.name = name;
    }

    public PharmacyDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }
}
