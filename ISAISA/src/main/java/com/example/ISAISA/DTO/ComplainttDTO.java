package com.example.ISAISA.DTO;

import com.example.ISAISA.model.Patient;
import com.example.ISAISA.model.User;

public class ComplainttDTO {
    private Integer id;
    private Boolean answered;
    private String question;
    private String reply;
    private String emailUser;
    private String nameHospital;
    private String emailPatient;
    private Boolean isHospital;


    public ComplainttDTO(Integer id, Boolean answered, String question, String reply, String emailUser, String nameHospital, String emailPatient, Boolean isHospital) {
        this.id = id;
        this.answered = answered;
        this.question = question;
        this.reply = reply;
        this.emailUser = emailUser;
        this.nameHospital = nameHospital;
        this.emailPatient = emailPatient;
        this.isHospital = isHospital;
    }



    public ComplainttDTO(Integer id, Boolean answered, String question, String reply, String emailUser, String emailPatient, Boolean isHospital) {
        this.id = id;
        this.answered = answered;
        this.question = question;
        this.reply = reply;
        this.emailUser = emailUser;
        this.emailPatient = emailPatient;
        this.isHospital = isHospital;
    }

    public ComplainttDTO(Integer id, Boolean answered, String question, String reply,Boolean isHospital,String emailPatient, String namePh) {
        this.id = id;
        this.answered = answered;
        this.question = question;
        this.reply = reply;
        this.emailPatient = emailPatient;
        this.isHospital = isHospital;
        this.nameHospital=namePh;
    }

    public Boolean getHospital() {
        return isHospital;
    }

    public void setHospital(Boolean hospital) {
        isHospital = hospital;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getEmailPatient() {
        return emailPatient;
    }

    public String getNameHospital() {
        return nameHospital;
    }

    public void setNameHospital(String nameHospital) {
        this.nameHospital = nameHospital;
    }

    public void setEmailPatient(String emailPatient) {
        this.emailPatient = emailPatient;
    }

    public ComplainttDTO() {
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

}
