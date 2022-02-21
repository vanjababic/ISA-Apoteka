package com.example.ISAISA.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Promotion {

    @Id
    @SequenceGenerator(name="seq_promotion", sequenceName = "seq_promotion", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_promotion")
    private Integer id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDate validFrom;

    @Column(nullable = false)
    private LocalDate validUntil;


    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Pharmacy pharmacy;


    public Promotion() { }

    public Promotion(Integer id, String description, LocalDate validFrom, LocalDate validUntil, Pharmacy pharmacy) {
        this.id = id;
        this.description = description;
        this.validFrom = validFrom;
        this.validUntil = validUntil;
        this.pharmacy = pharmacy;
    }

    public Promotion(String description, LocalDate validFrom, LocalDate validUntil, Pharmacy pharmacy) {
        this.description = description;
        this.validFrom = validFrom;
        this.validUntil = validUntil;
        this.pharmacy = pharmacy;
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public LocalDate getValidFrom() { return validFrom; }

    public void setValidFrom(LocalDate validFrom) { this.validFrom = validFrom; }

    public LocalDate getValidUntil() { return validUntil; }

    public void setValidUntil(LocalDate validUntil) { this.validUntil = validUntil; }

    public Pharmacy getPharmacy() { return pharmacy; }

    public void setPharmacy(Pharmacy pharmacy) { this.pharmacy = pharmacy; }
}
