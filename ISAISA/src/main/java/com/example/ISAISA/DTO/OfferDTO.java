package com.example.ISAISA.DTO;


import com.example.ISAISA.model.Supplier;

import java.util.Date;

public class OfferDTO {

    private Integer offerPrice;

    private Date deliveryDate;

    private Supplier supplier;


    private Integer orderID;

    public OfferDTO(Integer offerPrice, Date deliveryDate, Supplier supplier, Integer orderID) {
        this.offerPrice = offerPrice;
        this.deliveryDate = deliveryDate;
        this.supplier = supplier;
        this.orderID = orderID;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public OfferDTO(Integer offerPrice, Date deliveryDate, Integer orderID) {
        this.offerPrice = offerPrice;
        this.deliveryDate = deliveryDate;
        this.orderID = orderID;
    }


    public OfferDTO() {
    }


    public Integer getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(Integer offerPrice) {
        this.offerPrice = offerPrice;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }


    public Integer getOrderID() {
        return orderID;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }
}
