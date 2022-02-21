package com.example.ISAISA.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Counseling {

    @Id
    @SequenceGenerator(name="seq_counseling", sequenceName = "seq_counseling", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_counseling")
    private Integer id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private Patient patient;


    @ManyToOne(fetch = FetchType.EAGER)
    private Pharmacist pharmacist;

    @Column
    private LocalDateTime beginofappointment;

    @Column
    private LocalDateTime endofappointment;

    @Column
    private Integer price;


    @OneToOne(mappedBy = "examinationCounseling")
    private Examination counseling;

    @JsonIgnore
    @OneToMany(mappedBy = "newCounseling", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Examination> examinations = new HashSet<Examination>();

    public Counseling() {
    }

    public Counseling(Integer id, Patient patient, Pharmacist pharmacist, LocalDateTime beginofappointment, LocalDateTime endofappointment, Integer price) {
        this.id = id;
        this.patient = patient;
        this.pharmacist = pharmacist;
        this.beginofappointment = beginofappointment;
        this.endofappointment = endofappointment;
        this.price = price;
    }

    public Counseling(Integer id, LocalDateTime beginofappointment, LocalDateTime endofappointment, Pharmacist pharmacist) {
    }

    public Counseling(Integer id, LocalDateTime beginofappointment, LocalDateTime endofappointment, Pharmacist pharmacist, Integer price) {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Pharmacist getPharmacist() {
        return pharmacist;
    }

    public void setPharmacist(Pharmacist pharmacist) {
        this.pharmacist = pharmacist;
    }

    public LocalDateTime getBeginofappointment() {
        return beginofappointment;
    }

    public void setBeginofappointment(LocalDateTime beginofappointment) {
        this.beginofappointment = beginofappointment;
    }

    public LocalDateTime getEndofappointment() {
        return endofappointment;
    }

    public void setEndofappointment(LocalDateTime endofappointment) {
        this.endofappointment = endofappointment;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Examination getCounseling() {
        return counseling;
    }

    public void setCounseling(Examination counseling) {
        this.counseling = counseling;
    }

    public Set<Examination> getExaminations() {
        return examinations;
    }

    public void setExaminations(Set<Examination> examinations) {
        this.examinations = examinations;
    }
}
