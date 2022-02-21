package com.example.ISAISA.DTO;

import com.example.ISAISA.model.AdminPharmacy;
import com.example.ISAISA.model.Medication;
import com.example.ISAISA.model.Offer;
import com.example.ISAISA.model.Orderr_Medication;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OrderDTO {

    private Integer id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date dateDeadline;

    private String statusAdmin;

    private List<Integer> med_ids;

    private List<Medication> meds;

    private List<Integer> amounts;

    private AdminPharmacy adminPharmacy;

    public OrderDTO(List<Medication> meds, List<Integer> amounts) {
        this.meds = meds;
        this.amounts = amounts;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateDeadline() {
        return dateDeadline;
    }

    public void setDateDeadline(Date dateDeadline) {
        this.dateDeadline = dateDeadline;
    }

    public String getStatusAdmin() {
        return statusAdmin;
    }

    public void setStatusAdmin(String statusAdmin) {
        this.statusAdmin = statusAdmin;
    }

    public List<Medication> getMeds() { return meds; }

    public void setMeds(List<Medication> meds) { this.meds = meds; }

    public AdminPharmacy getAdminPharmacy() {
        return adminPharmacy;
    }

    public void setAdminPharmacy(AdminPharmacy adminPharmacy) {
        this.adminPharmacy = adminPharmacy;
    }

    public List<Integer> getAmounts() { return amounts; }

    public void setAmounts(List<Integer> amounts) { this.amounts = amounts; }

    public List<Integer> getMed_ids() {
        return med_ids;
    }

    public void setMed_ids(List<Integer> med_ids) {
        this.med_ids = med_ids;
    }
}
