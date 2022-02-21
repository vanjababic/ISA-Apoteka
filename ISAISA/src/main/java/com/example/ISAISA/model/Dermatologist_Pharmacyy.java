package com.example.ISAISA.model;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
public class Dermatologist_Pharmacyy {


    @Id
    @SequenceGenerator(name="seq_dermatologist_pharmacy", sequenceName = "seq_dermatologist_pharmacy", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_dermatologist_pharmacy")
    private Integer id;

    @Column
    private LocalTime beginofwork;

    @Column
    private LocalTime endofwork;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Pharmacy pharmacy;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Dermatologist dermatologist;

    public Dermatologist_Pharmacyy(LocalTime beginofwork, LocalTime endofwork, Pharmacy pharmacy, Dermatologist dermatologist) {
        this.beginofwork = beginofwork;
        this.endofwork = endofwork;
        this.pharmacy = pharmacy;
        this.dermatologist = dermatologist;
    }

    public Dermatologist_Pharmacyy() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalTime getBeginofwork() {
        return beginofwork;
    }

    public void setBeginofwork(LocalTime beginofwork) {
        this.beginofwork = beginofwork;
    }

    public LocalTime getEndofwork() {
        return endofwork;
    }

    public void setEndofwork(LocalTime endofwork) {
        this.endofwork = endofwork;
    }

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }

    public Dermatologist getDermatologist() {
        return dermatologist;
    }

    public void setDermatologist(Dermatologist dermatologist_id) {
        this.dermatologist = dermatologist_id;
    }

    public Dermatologist_Pharmacyy(Integer id, LocalTime beginofwork, LocalTime endofwork, Pharmacy pharmacy, Dermatologist dermatologist) {
        this.id = id;
        this.beginofwork = beginofwork;
        this.endofwork = endofwork;
        this.pharmacy = pharmacy;
        this.dermatologist = dermatologist;
    }

}
