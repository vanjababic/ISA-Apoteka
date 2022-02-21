package com.example.ISAISA.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
@Entity
public class Medication {


    @Id
    @SequenceGenerator(name="seq_medication", sequenceName = "seq_medication", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_medication")
    private Integer id;

    @Column(nullable = false, unique = true)
    private String code ;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type_med;

    @Column(nullable = false)
    private String shape_med;

    @Column(nullable = false)
    private String ingredients;

    @Column(nullable = false)
    private String producer;

    @Column
    private Boolean prescription;

    @Column
    private String notes;

    @Column
    private String contraindication;

    @Column
    private Integer recommended_daily_intake;

    @Column
    private Float rating;

    @JsonIgnore
    @OneToMany(mappedBy = "medication", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Orderr_Medication> orderr_medications = new HashSet<Orderr_Medication>();

    @JsonIgnore
    @ManyToMany(mappedBy = "medication")
    private Set<Patient> patients = new HashSet<Patient>();

    @JsonIgnore
    @ManyToMany(mappedBy = "medication",fetch=FetchType.EAGER)
    private Set<Supplier> suppliers = new HashSet<Supplier>();

    @JsonIgnore
    @OneToMany(mappedBy = "medication", fetch = FetchType.EAGER)
    private Set<Reservation> reservations = new HashSet<Reservation>();

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "medication_altmedication", joinColumns = @JoinColumn(name = "medication_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "altmedication_id", referencedColumnName = "id"))
    private Set<Medication> medication = new HashSet<Medication>();

    @JsonIgnore
    @ManyToMany(mappedBy = "medication", fetch = FetchType.EAGER)
    private Set<Medication> alternate_medication = new HashSet<Medication>();

    /*@JsonIgnore
    @ManyToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "med_pharmacies", joinColumns = @JoinColumn(name = "medication_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "pharmacy_id", referencedColumnName = "id"))
    private Set<Pharmacy> pharmacies = new HashSet<Pharmacy>();*/

    //@JsonIgnore
    @OneToMany(mappedBy = "medication", fetch = FetchType.EAGER)
    private Set<PharmacyMedication> pharmacy_medications = new HashSet<PharmacyMedication>();

    @JsonIgnore
    @OneToMany(mappedBy = "prescriptedMedication", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Examination> examinations = new HashSet<Examination>();

    public Medication(){}

    public Medication(Integer id, String code, String name, String type_med, String shape_med, String ingredients, String producer, Boolean prescription, String notes) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.type_med = type_med;
        this.shape_med = shape_med;
        this.ingredients = ingredients;
        this.producer = producer;
        this.prescription = prescription;
        this.notes = notes;

    }

    public Medication(String code, String name, String type_med, String shape_med, String ingredients, String producer, Boolean prescription, String notes) {
        this.code = code;
        this.name = name;
        this.type_med = type_med;
        this.shape_med = shape_med;
        this.ingredients = ingredients;
        this.producer = producer;
        this.prescription = prescription;
        this.notes = notes;
    }

    public Medication( String code, String name, String type_med, String shape_med, String ingredients, String producer, String contraindication, Integer recommended_daily_intake) {

        this.code = code;
        this.name = name;
        this.type_med = type_med;
        this.shape_med = shape_med;
        this.ingredients = ingredients;
        this.producer = producer;
        this.contraindication = contraindication;
        this.recommended_daily_intake = recommended_daily_intake;

    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType_med() {
        return type_med;
    }

    public void setType_med(String type_med) {
        this.type_med = type_med;
    }

    public String getShape_med() {
        return shape_med;
    }

    public void setShape_med(String shape_med) {
        this.shape_med = shape_med;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public Boolean getPrescription() {
        return prescription;
    }

    public void setPrescription(Boolean prescription) {
        this.prescription = prescription;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Set<Medication> getAlternate_medication() {
        return alternate_medication;
    }

    public void setAlternate_medication(Set<Medication> alternate_medication) {
        this.alternate_medication = alternate_medication;
    }

    public Set<Medication> getMedication() {
        return medication;
    }

    public void setMedication(Set<Medication> medication) {
        this.medication = medication;
    }

    /*public Set<Pharmacy> getPharmacies() {
        return pharmacies;
    }

    public void setPharmacies(Set<Pharmacy> pharmacies) {
        this.pharmacies = pharmacies;
    }*/

    public Set<Orderr_Medication> getOrderr_medications() {
        return orderr_medications;
    }

    public void setOrderr_medications(Set<Orderr_Medication> orderr_medications) {
        this.orderr_medications = orderr_medications;
    }

    public Set<PharmacyMedication> getPharmacy_medications() {
        return pharmacy_medications;
    }

    public void setPharmacy_medications(Set<PharmacyMedication> pharmacy_medications) {
        this.pharmacy_medications = pharmacy_medications;
    }

    public String getContraindication() {
        return contraindication;
    }

    public void setContraindication(String contraindication) {
        this.contraindication = contraindication;
    }

    public Integer getRecommended_daily_intake() {
        return recommended_daily_intake;
    }

    public void setRecommended_daily_intake(Integer recommended_daily_intake) {
        this.recommended_daily_intake = recommended_daily_intake;
    }
}
