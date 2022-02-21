package com.example.ISAISA.DTO;

import com.example.ISAISA.model.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class OrderMedicationDTO {

    private Integer id;

    //@JsonFormat(pattern="yyyy-MM-dd")
    private Date dateDeadline;

    private AdminPharmacy adminPharmacy;

    public OrderMedicationDTO() {
    }

    public OrderMedicationDTO(Integer id, Date dateDeadline) {
        this.id = id;
        this.dateDeadline = dateDeadline;
    }

    public OrderMedicationDTO(Integer id, Date dateDeadline, AdminPharmacy adminPharmacy) {
        this.id = id;
        this.dateDeadline = dateDeadline;
        this.adminPharmacy = adminPharmacy;
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public Date getDateDeadline() { return dateDeadline; }

    public void setDateDeadline(Date dateDeadline) { this.dateDeadline = dateDeadline; }

    public AdminPharmacy getAdminPharmacy() { return adminPharmacy; }

    public void setAdminPharmacy(AdminPharmacy adminPharmacy) { this.adminPharmacy = adminPharmacy; }

}
