package com.example.ISAISA.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Examination {

    @Id
    @SequenceGenerator(name="seq_examination", sequenceName = "seq_examination", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_examination")
    private Integer id;

    @Column
    private String report;

    @Column
    private Integer therapyDuration;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "examinationAppointment_id", referencedColumnName = "id")
    private Appointment examinationAppointment;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "examinationCounseling_id", referencedColumnName = "id")
    private Counseling examinationCounseling;


    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Medication prescriptedMedication;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Appointment newAppointment;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Counseling newCounseling;

    public Examination() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public Integer getTherapyDuration() {
        return therapyDuration;
    }

    public void setTherapyDuration(Integer therapyDuration) {
        this.therapyDuration = therapyDuration;
    }

    public Appointment getExaminationAppointment() {
        return examinationAppointment;
    }

    public void setExaminationAppointment(Appointment examinationAppointment) {
        this.examinationAppointment = examinationAppointment;
    }

    public Medication getPrescriptedMedication() {
        return prescriptedMedication;
    }

    public void setPrescriptedMedication(Medication prescriptedMedication) {
        this.prescriptedMedication = prescriptedMedication;
    }

    public Appointment getNewAppointment() {
        return newAppointment;
    }

    public void setNewAppointment(Appointment newAppointment) {
        this.newAppointment = newAppointment;
    }

    public Counseling getExaminationCounseling() {
        return examinationCounseling;
    }

    public void setExaminationCounseling(Counseling examinationCounseling) {
        this.examinationCounseling = examinationCounseling;
    }

    public Counseling getNewCounseling() {
        return newCounseling;
    }

    public void setNewCounseling(Counseling newCounseling) {
        this.newCounseling = newCounseling;
    }
}
