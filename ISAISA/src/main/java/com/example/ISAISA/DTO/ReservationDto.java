package com.example.ISAISA.DTO;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public class ReservationDto {
    private Integer id;
    private String name;
    private LocalDateTime dateofreservation;

    public ReservationDto(Integer id, String name, LocalDateTime dateofreservation) {
        this.id = id;
        this.name = name;
        this.dateofreservation = dateofreservation;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDateofreservation() {
        return dateofreservation;
    }

    public void setDateofreservation(LocalDateTime dateofreservation) {
        this.dateofreservation = dateofreservation;
    }
}
