package com.example.ISAISA.DTO;

public class VacationDenyDTO {

    private Integer id;

    private String reasonDenied;

    public VacationDenyDTO(Integer id, String reasonDenied) {
        this.id = id;
        this.reasonDenied = reasonDenied;
    }

    public VacationDenyDTO() {
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getReasonDenied() { return reasonDenied; }

    public void setReasonDenied(String reasonDenied) { this.reasonDenied = reasonDenied; }
}
