package com.example.ISAISA.DTO;

import javax.persistence.Column;
import java.time.LocalDate;

public class PromotionDTO {

    private String description;

    private LocalDate validFrom;

    private LocalDate validUntil;

    public PromotionDTO() {
    }

    public PromotionDTO(String description, LocalDate validFrom, LocalDate validUntil) {
        this.description = description;
        this.validFrom = validFrom;
        this.validUntil = validUntil;
    }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public LocalDate getValidFrom() { return validFrom; }

    public void setValidFrom(LocalDate validFrom) { this.validFrom = validFrom; }

    public LocalDate getValidUntil() { return validUntil; }

    public void setValidUntil(LocalDate validUntil) { this.validUntil = validUntil; }
}
