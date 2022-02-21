package com.example.ISAISA.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Orderr_Medication {
    @Id
    @SequenceGenerator(name="seq_order", sequenceName = "seq_order", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_order")
    private Integer id;

    @Column
    private Integer amount;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Orderr orderr;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private Medication medication;

    public Orderr_Medication() {
    }

    public Orderr_Medication(Integer id, Integer amount, Orderr orderr, Medication medication) {
        this.id = id;
        this.amount = amount;
        this.orderr = orderr;
        this.medication = medication;
    }

    public Orderr_Medication(Integer amount, Medication medication) {
        this.amount = amount;
        this.medication = medication;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Orderr getOrderr() {
        return orderr;
    }

    public void setOrderr(Orderr orderr) {
        this.orderr = orderr;
    }

    public Medication getMedication() {
        return medication;
    }

    public void setMedication(Medication medication) {
        this.medication = medication;
    }
}
