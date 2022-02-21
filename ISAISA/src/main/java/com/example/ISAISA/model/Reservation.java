package com.example.ISAISA.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Entity
@DiscriminatorValue("reservation")
public class Reservation {

    @Id
    @SequenceGenerator(name="seq_reservation", sequenceName = "seq_reservation", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_reservation")
    private Integer id;


    @ManyToOne(fetch = FetchType.LAZY)
    private Patient patient;


    @ManyToOne(fetch = FetchType.EAGER)
    private Pharmacy pharmacy;


    @ManyToOne(fetch = FetchType.EAGER)
    private Medication medication;

    @Column
    private LocalDateTime dateofreservation;

    @Column
    private Boolean medicationtaken;

    public Medication getMedication() {
        return medication;
    }

    public void setMedication(Medication medication) {
        this.medication = medication;
    }

    public Reservation(Integer id, Patient patient, Pharmacy pharmacy, LocalDateTime dateofreservation, Boolean medicationtaken) {
        this.id = id;
        this.patient = patient;
        this.pharmacy = pharmacy;
        this.dateofreservation = dateofreservation;
        this.medicationtaken = medicationtaken;
    }

    public Reservation(Integer id, Patient patient, Pharmacy pharmacy, Medication medication, LocalDateTime dateofreservation, Boolean medicationtaken) {
        this.id = id;
        this.patient = patient;
        this.pharmacy = pharmacy;
        this.medication = medication;
        this.dateofreservation = dateofreservation;
        this.medicationtaken = medicationtaken;
    }

    public Reservation() {

    }

    public Boolean getMedicationtaken() {
        return medicationtaken;
    }

    public void setMedicationtaken(Boolean medicationtaken) {
        this.medicationtaken = medicationtaken;
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

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }

    public LocalDateTime getDateofreservation() {
        return dateofreservation;
    }

    public void setDateofreservation(LocalDateTime dateofreservation) {
        this.dateofreservation = dateofreservation;
    }

    public Reservation(Integer id, Patient patient, Pharmacy pharmacy, LocalDateTime dateofreservation) {
        this.id = id;
        this.patient = patient;
        this.pharmacy = pharmacy;
        this.dateofreservation = dateofreservation;
    }
}
