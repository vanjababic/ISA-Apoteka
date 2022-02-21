package com.example.ISAISA.DTO;

import java.time.LocalDate;
import java.time.LocalTime;

public class CalendarDTO {

    private String name;
    private String lastName;
    private LocalDate date;
    private LocalTime beginofappointment;
    private LocalTime endofappointment;

    public CalendarDTO() {
    }

    public CalendarDTO(String name, String lastName, LocalDate date, LocalTime beginofappointment, LocalTime endofappointment) {
        this.name = name;
        this.lastName = lastName;
        this.date = date;
        this.beginofappointment = beginofappointment;
        this.endofappointment = endofappointment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getBeginofappointment() {
        return beginofappointment;
    }

    public void setBeginofappointment(LocalTime beginofappointment) {
        this.beginofappointment = beginofappointment;
    }

    public LocalTime getEndofappointment() {
        return endofappointment;
    }

    public void setEndofappointment(LocalTime endofappointment) {
        this.endofappointment = endofappointment;
    }
}
