package com.example.ISAISA.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;


@Entity
public class Complaint {


    @Id
    @SequenceGenerator(name="seq_complaint", sequenceName = "seq_complaint", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_complaint")
    private Integer id;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;


    @JsonIgnore
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "pharmacy_id", referencedColumnName = "id")
    private Pharmacy pharmacy;

    @Column(nullable = false)
    private String question;
    @Column
    private String reply;
    @Column
    private boolean answered;

    @Column
    private boolean ishospital;

    public Complaint(Integer id, User user, Patient patient, Pharmacy pharmacy, String question, String reply, boolean answered, boolean ishospital) {
        this.id = id;
        this.user = user;
        this.patient = patient;
        this.pharmacy = pharmacy;
        this.question = question;
        this.reply = reply;
        this.answered = answered;
        this.ishospital = ishospital;
    }

    public Complaint(Integer id, User user, Patient patient, String question, String reply, boolean answered) {
        this.id = id;
        this.user = user;
        this.patient = patient;
        this.question = question;
        this.reply = reply;
        this.answered = answered;
    }

    public Complaint() {
    }

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }

    public boolean isIshospital() {
        return ishospital;
    }

    public void setIshospital(boolean ishospital) {
        this.ishospital = ishospital;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }


}
