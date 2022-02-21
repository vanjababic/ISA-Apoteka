package com.example.ISAISA.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class PharmacyMedication {

    @Id
    @SequenceGenerator(name="seq_pharmacy_medication", sequenceName = "seq_pharmacy_medication", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_pharmacy_medication")
    private Integer id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private Pharmacy pharmacy;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private Medication medication;

    @Column
    private Integer quantity;

    @Column
    private Integer price;

    @JsonFormat(pattern="yyyy-MM-dd")
    @Column
    private LocalDate beginPriceValidity;

    @JsonFormat(pattern="yyyy-MM-dd")
    @Column
    private LocalDate endPriceValidity;

    public PharmacyMedication() {
    }

    public PharmacyMedication(Pharmacy pharmacy, Medication medication, Integer quantity, Integer price, LocalDate beginPriceValidity, LocalDate endPriceValidity) {
        this.pharmacy = pharmacy;
        this.medication = medication;
        this.quantity = quantity;
        this.price = price;
        this.beginPriceValidity = beginPriceValidity;
        this.endPriceValidity = endPriceValidity;
    }

    public PharmacyMedication(Pharmacy pharmacy, Medication medication, Integer quantity) {
        this.pharmacy = pharmacy;
        this.medication = medication;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }

    public Medication getMedication() {
        return medication;
    }

    public void setMedication(Medication medication) {
        this.medication = medication;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public LocalDate getBeginPriceValidity() {
        return beginPriceValidity;
    }

    public void setBeginPriceValidity(LocalDate beginPriceValidity) {
        this.beginPriceValidity = beginPriceValidity;
    }

    public LocalDate getEndPriceValidity() {
        return endPriceValidity;
    }

    public void setEndPriceValidity(LocalDate endPriceValidity) {
        this.endPriceValidity = endPriceValidity;
    }
}
