package com.example.ISAISA.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Pharmacy {

    @Id
    @SequenceGenerator(name="seq_pharmacy", sequenceName = "seq_pharmacy", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_pharmacy")
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String address;

    @Column
    private String description;

    @Column
    private Float rating;

    @JsonIgnore
    @OneToMany(mappedBy = "pharmacy", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Complaint> complaints;



    @JsonIgnore
    @OneToMany(mappedBy = "pharmacy", fetch = FetchType.EAGER)
    private Set<Reservation> reservations = new HashSet<Reservation>();


    @JsonIgnore
    @OneToMany(mappedBy = "pharmacy", fetch = FetchType.LAZY)
    private Set<AdminPharmacy> adminPharmacySet = new HashSet<AdminPharmacy>();

    /*@JsonIgnore
    @ManyToMany(mappedBy = "pharmacies", fetch = FetchType.EAGER)
    private Set<Medication> medication = new HashSet<Medication>();*/
    @JsonIgnore
    @OneToMany(mappedBy = "pharmacy", fetch = FetchType.EAGER)
    private Set<PharmacyMedication> pharmacy_medications = new HashSet<PharmacyMedication>();


    @JsonIgnore
    @OneToMany(mappedBy = "pharmacy", fetch = FetchType.LAZY)
    private Set<Pharmacist> pharmacists = new HashSet<Pharmacist>();

    @JsonIgnore
    @OneToMany(mappedBy = "pharmacy", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Set<Dermatologist_Pharmacyy> dermatologist_pharmacies = new HashSet<Dermatologist_Pharmacyy>();

    @JsonIgnore
    @OneToMany(mappedBy = "pharmacy_appointment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Appointment> appointments = new HashSet<Appointment>();

    @JsonIgnore
    @OneToMany(mappedBy = "pharmacy", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Promotion> promotions = new HashSet<Promotion>();

    @JsonIgnore
    @ManyToMany(mappedBy = "pharmacies_promotions", fetch = FetchType.EAGER)
    private Set<Patient> patients_promotions = new HashSet<Patient>();

    public Set<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "dermatologist_pharmacy", joinColumns = @JoinColumn(name = "pharmacy_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "dermatologist_id", referencedColumnName = "id"))
    private Set<Dermatologist> dermatologists = new HashSet<Dermatologist>();


    public Pharmacy() {
    }

    public Pharmacy(Integer id, String name, String address, String description, Float rating) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.description = description;
        this.rating = rating;
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public Set<AdminPharmacy> getAdminPharmacySet() { return adminPharmacySet; }

    public void setAdminPharmacySet(Set<AdminPharmacy> adminPharmacySet) { this.adminPharmacySet = adminPharmacySet; }

    public Float getRating() { return rating; }

    public void setRating(Float rating) { this.rating = rating; }

    /*public Set<Medication> getMedication() {
        return medication;
    }

    public void setMedication(Set<Medication> medication) {
        this.medication = medication;
    }*/

    public Set<PharmacyMedication> getPharmacy_medications() {
        return pharmacy_medications;
    }

    public void setPharmacy_medications(Set<PharmacyMedication> pharmacy_medications) {
        this.pharmacy_medications = pharmacy_medications;
    }

    public Set<Pharmacist> getPharmacists() {
        return pharmacists;
    }

    public void setPharmacists(Set<Pharmacist> pharmacists) {
        this.pharmacists = pharmacists;
    }

    public Set<Dermatologist> getDermatologists() {
        return dermatologists;
    }

    public void setDermatologists(Set<Dermatologist> dermatologists) {
        this.dermatologists = dermatologists;
    }

    public Set<Patient> getPatients_promotions() {
        return patients_promotions;
    }

    public void setPatients_promotions(Set<Patient> patients_promotions) {
        this.patients_promotions = patients_promotions;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }

    public Set<Dermatologist_Pharmacyy> getDermatologist_pharmacies() {
        return dermatologist_pharmacies;
    }

    public void setDermatologist_pharmacies(Set<Dermatologist_Pharmacyy> dermatologist_pharmacies) {
        this.dermatologist_pharmacies = dermatologist_pharmacies;
    }

    public Set<Promotion> getPromotions() {
        return promotions;
    }

    public void setPromotions(Set<Promotion> promotions) {
        this.promotions = promotions;
    }

    @Override
    public String toString() {
        return "name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
