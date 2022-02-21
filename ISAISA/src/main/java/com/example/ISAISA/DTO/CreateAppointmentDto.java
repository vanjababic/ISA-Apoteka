package com.example.ISAISA.DTO;

import java.time.LocalDateTime;

public class CreateAppointmentDto {

    private Integer id;
    private LocalDateTime startOfAppointment;
    private LocalDateTime endOfAppointment;
    private Integer price;

    public CreateAppointmentDto() {
    }

    public CreateAppointmentDto(Integer id, LocalDateTime startOfAppointment, LocalDateTime endOfAppointment, Integer price) {
        this.id = id;
        this.startOfAppointment = startOfAppointment;
        this.endOfAppointment = endOfAppointment;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getStartOfAppointment() {
        return startOfAppointment;
    }

    public void setStartOfAppointment(LocalDateTime startOfAppointment) {
        this.startOfAppointment = startOfAppointment;
    }

    public LocalDateTime getEndOfAppointment() {
        return endOfAppointment;
    }

    public void setEndOfAppointment(LocalDateTime endOfAppointment) {
        this.endOfAppointment = endOfAppointment;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
