package com.example.ISAISA.DTO;

import java.time.LocalDate;

public class ExaminPatientDto {

    private String name;
    private String lastName;
    private LocalDate date;

    public ExaminPatientDto() {
    }

    public ExaminPatientDto(String name, String lastName, LocalDate date) {
        this.name = name;
        this.lastName = lastName;
        this.date = date;
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
}
