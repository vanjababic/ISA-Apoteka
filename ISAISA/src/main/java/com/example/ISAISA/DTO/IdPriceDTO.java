package com.example.ISAISA.DTO;

import java.time.LocalDate;

public class IdPriceDTO {

    private Integer id;

    private Integer price;

    private LocalDate end;

    private Integer quantity;

    public IdPriceDTO(Integer id, Integer price) {
        this.id = id;
        this.price = price;
    }

    public IdPriceDTO(Integer id, Integer price, LocalDate end) {
        this.id = id;
        this.price = price;
        this.end = end;
    }

    public IdPriceDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public LocalDate getEnd() { return end; }

    public void setEnd(LocalDate end) { this.end = end; }

    public Integer getQuantity() { return quantity; }

    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}
