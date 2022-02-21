package com.example.ISAISA.DTO;

import com.example.ISAISA.model.Pharmacy;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class AppointmentDTO {

    private Integer dermatologist;

    private Pharmacy pharmacy;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private LocalDateTime beginofappointment;

    private Integer duration;

    private Integer price;


    public AppointmentDTO() {
    }

    public AppointmentDTO(Integer dermatologist, Pharmacy pharmacy, LocalDateTime beginofappointment, Integer duration, Integer price) {
        this.dermatologist = dermatologist;
        this.pharmacy = pharmacy;
        this.beginofappointment = beginofappointment;
        this.duration = duration;
        this.price = price;
    }

    public Integer getDermatologist() {
        return dermatologist;
    }

    public void setDermatologist(Integer dermatologist) {
        this.dermatologist = dermatologist;
    }

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }

    public LocalDateTime getBeginofappointment() {
        return beginofappointment;
    }

    public void setBeginofappointment(LocalDateTime beginofappointment) { this.beginofappointment = beginofappointment; }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
