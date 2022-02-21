package com.example.ISAISA.DTO;

public class StatusDTO {
    private String Status;

    public StatusDTO(String status) {
        Status = status;
    }

    public StatusDTO() {
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
