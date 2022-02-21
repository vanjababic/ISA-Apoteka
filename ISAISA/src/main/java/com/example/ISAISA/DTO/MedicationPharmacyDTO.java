package com.example.ISAISA.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import java.time.LocalDate;

public class MedicationPharmacyDTO {

    private Integer id;

    private String code ;

    private String name;

    private String producer;

    private Integer quantity;

    private Integer price;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate beginPriceValidity;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate endPriceValidity;

    public MedicationPharmacyDTO() {
    }

    public MedicationPharmacyDTO(String code, String name, String producer, Integer quantity, Integer price) {
        this.code = code;
        this.name = name;
        this.producer = producer;
        this.quantity = quantity;
        this.price = price;
    }

    public MedicationPharmacyDTO(Integer id, String code, String name, String producer, Integer quantity, Integer price) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.producer = producer;
        this.quantity = quantity;
        this.price = price;
    }

    public MedicationPharmacyDTO(Integer id, String code, String name, String producer, Integer quantity, Integer price, LocalDate beginPriceValidity, LocalDate endPriceValidity) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.producer = producer;
        this.quantity = quantity;
        this.price = price;
        this.beginPriceValidity = beginPriceValidity;
        this.endPriceValidity = endPriceValidity;
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getCode() { return code; }

    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getProducer() { return producer; }

    public void setProducer(String producer) { this.producer = producer; }

    public Integer getQuantity() { return quantity; }

    public void setQuantity(Integer quantity) {this.quantity = quantity; }

    public Integer getPrice() { return price; }

    public void setPrice(Integer price) { this.price = price; }

    public LocalDate getBeginPriceValidity() {
        return beginPriceValidity;
    }

    public void setBeginPriceValidity(LocalDate beginPriceValidity) {
        this.beginPriceValidity = beginPriceValidity;
    }

    public LocalDate getEndPriceValidity() {
        return endPriceValidity;
    }

    public void setEndPriceValidity(LocalDate endPriceValidity) {
        this.endPriceValidity = endPriceValidity;
    }
}
