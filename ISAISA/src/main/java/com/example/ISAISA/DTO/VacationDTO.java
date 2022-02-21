package com.example.ISAISA.DTO;


import java.time.LocalDateTime;

public class VacationDTO {

    LocalDateTime beginVacation;
    LocalDateTime endVacation;
    Integer absence;

    public VacationDTO() {
    }

    public VacationDTO(LocalDateTime beginVacation, LocalDateTime endVacation, Integer absence) {
        this.beginVacation = beginVacation;
        this.endVacation = endVacation;
        this.absence = absence;
    }

    public LocalDateTime getBeginVacation() {
        return beginVacation;
    }

    public void setBeginVacation(LocalDateTime beginVacation) {
        this.beginVacation = beginVacation;
    }

    public LocalDateTime getEndVacation() {
        return endVacation;
    }

    public void setEndVacation(LocalDateTime endVacation) {
        this.endVacation = endVacation;
    }

    public Integer getAbsence() {
        return absence;
    }

    public void setAbsence(Integer absence) {
        this.absence = absence;
    }
}
