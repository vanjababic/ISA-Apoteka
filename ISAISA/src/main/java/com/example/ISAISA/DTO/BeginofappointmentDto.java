package com.example.ISAISA.DTO;

import java.time.LocalDateTime;

public class BeginofappointmentDto {
    private Integer id;
    private LocalDateTime beginofappointment;

    public BeginofappointmentDto(Integer id, LocalDateTime beginofappointment) {
        this.id = id;
        this.beginofappointment = beginofappointment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BeginofappointmentDto() {
    }

    public BeginofappointmentDto(LocalDateTime beginofappointment) {
        this.beginofappointment = beginofappointment;
    }

    public LocalDateTime getBeginofappointment() {
        return beginofappointment;
    }

    public void setBeginofappointment(LocalDateTime beginofappointment) {
        this.beginofappointment = beginofappointment;
    }
}
