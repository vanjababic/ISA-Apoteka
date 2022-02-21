package com.example.ISAISA.DTO;

import com.example.ISAISA.model.Supplier;

import java.util.Date;

public class OrderrDTO {
    private Integer id;

    private Date dateDeadline;

    private String statusSupplier;

    private String adminEmail;

    private String pharmacyName;

    private String pharmacyAddress;

    private Integer offer;

    private Supplier supplier;

    public OrderrDTO(Integer id, Date dateDeadline, String statusSupplier, String adminEmail, String pharmacyName, String pharmacyAddress, Integer offer, Supplier supplier) {
        this.id = id;
        this.dateDeadline = dateDeadline;
        this.statusSupplier = statusSupplier;
        this.adminEmail = adminEmail;
        this.pharmacyName = pharmacyName;
        this.pharmacyAddress = pharmacyAddress;
        this.offer = offer;
        this.supplier = supplier;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public OrderrDTO(Integer id, Date dateDeadline, String statusSupplier, String adminEmail, String pharmacyName, String pharmacyAddress, Integer offer) {
        this.id = id;
        this.dateDeadline = dateDeadline;
        this.statusSupplier = statusSupplier;
        this.adminEmail = adminEmail;
        this.pharmacyName = pharmacyName;
        this.pharmacyAddress = pharmacyAddress;
        this.offer=offer;
    }


    public Integer getOffer() {
        return offer;
    }

    public void setOffer(Integer offer) {
        this.offer = offer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateDeadline() {
        return dateDeadline;
    }

    public void setDateDeadline(Date dateDeadline) {
        this.dateDeadline = dateDeadline;
    }

    public String getStatusSupplier() {
        return statusSupplier;
    }

    public void setStatusSupplier(String statusSupplier) {
        this.statusSupplier = statusSupplier;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String getPharmacyName() {
        return pharmacyName;
    }

    public void setPharmacyName(String pharmacyName) {
        this.pharmacyName = pharmacyName;
    }

    public String getPharmacyAddress() {
        return pharmacyAddress;
    }

    public void setPharmacyAddress(String pharmacyAddress) {
        this.pharmacyAddress = pharmacyAddress;
    }

    public OrderrDTO() {
    }
}
