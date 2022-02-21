package com.example.ISAISA.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Vacation {

    @Id
    @SequenceGenerator(name="seq_vacation", sequenceName = "seq_vacation", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_vacation")
    private Integer id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column
    private LocalDate dateBeginVacation;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column
    private LocalDate dateEndVacation;

    @Column
    private Boolean absence;

    @Column
    private Boolean approved;

    @ManyToOne(fetch = FetchType.EAGER)
    private Pharmacist pharmacist;

    @ManyToOne(fetch = FetchType.EAGER)
    private Dermatologist dermatologist;

    public Vacation() { }

    public Vacation(Integer id, LocalDate dateBeginVacation, LocalDate dateEndVacation, Boolean approved, Pharmacist pharmacist, Dermatologist dermatologist) {
        this.id = id;
        this.dateBeginVacation = dateBeginVacation;
        this.dateEndVacation = dateEndVacation;
        this.approved = approved;
        this.pharmacist = pharmacist;
        this.dermatologist = dermatologist;
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public LocalDate getDateBeginVacation() { return dateBeginVacation; }

    public void setDateBeginVacation(LocalDate dateBeginVacation) { this.dateBeginVacation = dateBeginVacation; }

    public LocalDate getDateEndVacation() { return dateEndVacation; }

    public void setDateEndVacation(LocalDate dateEndVacation) { this.dateEndVacation = dateEndVacation; }

    public Boolean getApproved() { return approved; }

    public void setApproved(Boolean approved) { this.approved = approved; }

    public Pharmacist getPharmacist() { return pharmacist; }

    public void setPharmacist(Pharmacist pharmacist) { this.pharmacist = pharmacist; }

    public Dermatologist getDermatologist() { return dermatologist; }

    public void setDermatologist(Dermatologist dermatologist) { this.dermatologist = dermatologist; }

    public Boolean getAbsence() {
        return absence;
    }

    public void setAbsence(Boolean absence) {
        this.absence = absence;
    }
}
