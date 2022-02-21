package com.example.ISAISA.DTO;

import com.example.ISAISA.model.Patient;
import com.example.ISAISA.model.User;

public class ComplaintDTO {

    private Boolean answered;
    private String question;
    private String reply;
    private User user;
    private Patient patient;


    public ComplaintDTO(Boolean answered, String question, String reply, User user, Patient patient) {
        this.answered = answered;
        this.question = question;
        this.reply = reply;
        this.user = user;
        this.patient = patient;
    }

    public ComplaintDTO() {
    }

    public Boolean getAnswered() {
        return answered;
    }

    public void setAnswered(Boolean answered) {
        this.answered = answered;
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
}
