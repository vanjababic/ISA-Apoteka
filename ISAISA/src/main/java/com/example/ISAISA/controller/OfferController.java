package com.example.ISAISA.controller;

import com.example.ISAISA.DTO.IdDto;
import com.example.ISAISA.DTO.OrderMedicationDTO;
import com.example.ISAISA.model.AdminPharmacy;
import com.example.ISAISA.model.Offer;
import com.example.ISAISA.model.Orderr;
import com.example.ISAISA.service.OfferService;
import com.example.ISAISA.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(value = "/offers")
public class OfferController {

    private OfferService offerService;
    private OrderService orderService;

    @Autowired
    public void setOfferService(OfferService offerService) {
        this.offerService = offerService;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(value="/offersByOrder", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMINPHARMACY')")
    public ResponseEntity<Set<Offer>> getOffersByOrder(@RequestBody IdDto idDto){

        Orderr orderr = orderService.findById(idDto.getId());

        Set<Offer> offers = offerService.findByOrderrAndStatus(orderr);

        return new ResponseEntity<>(offers, HttpStatus.OK);
    }

    @PostMapping(value="/acceptOffer", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMINPHARMACY')")
    public ResponseEntity<Offer> acceptOffer(@RequestBody IdDto idDto) throws Exception {

        Offer offer = offerService.findById(idDto.getId());

        offer = offerService.acceptOffer(offer);

        return new ResponseEntity<>(offer, HttpStatus.OK);
    }

}
