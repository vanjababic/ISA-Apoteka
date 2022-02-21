package com.example.ISAISA.DTO;

public class SaveAppointment {

    Integer id;
    Integer appointmentId;

    public SaveAppointment() {
    }

    public SaveAppointment(Integer id, Integer appointmentId) {
        this.id = id;
        this.appointmentId = appointmentId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }
}
