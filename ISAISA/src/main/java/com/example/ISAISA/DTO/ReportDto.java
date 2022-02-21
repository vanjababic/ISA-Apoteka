package com.example.ISAISA.DTO;

public class ReportDto {

    private Integer id;
    private String report;

    public ReportDto() {
    }

    public ReportDto(Integer id, String report) {
        this.id = id;
        this.report = report;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }
}
