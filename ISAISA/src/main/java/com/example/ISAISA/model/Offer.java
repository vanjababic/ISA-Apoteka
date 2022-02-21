package com.example.ISAISA.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Offer {

    @Id
    @SequenceGenerator(name="seq_offer", sequenceName = "seq_offer", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_offer")
    private Integer id;

    @Column
    private Integer offerPrice;

    @Column
    private Date deliveryDate;

    @Column
    private String statusSupplier;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Orderr orderr;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Supplier supplier;

    public Offer() {
    }

    public Offer(Integer id, Integer offerPrice, Date deliveryDate, String statusSupplier, Orderr orderr, Supplier supplier) {
        this.id = id;
        this.offerPrice = offerPrice;
        this.deliveryDate = deliveryDate;
        this.statusSupplier = statusSupplier;
        this.orderr = orderr;
        this.supplier = supplier;
    }

    public Offer(Integer id, Integer offerPrice, Date deliveryDate, Orderr orderr, Supplier supplier) {
        this.id = id;
        this.offerPrice = offerPrice;
        this.deliveryDate = deliveryDate;
        this.orderr = orderr;
        this.supplier = supplier;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Orderr getOrderr() {
        return orderr;
    }

    public void setOrderr(Orderr orderr) {
        this.orderr = orderr;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public String getStatusSupplier() {
        return statusSupplier;
    }

    public void setStatusSupplier(String statusSupplier) {
        this.statusSupplier = statusSupplier;
    }
}
