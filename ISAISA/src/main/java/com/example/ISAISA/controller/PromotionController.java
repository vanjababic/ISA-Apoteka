package com.example.ISAISA.controller;

import com.example.ISAISA.DTO.PromotionDTO;
import com.example.ISAISA.model.Promotion;
import com.example.ISAISA.model.User;
import com.example.ISAISA.service.PharmacyService;
import com.example.ISAISA.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/promotions")
public class PromotionController {

    private PromotionService promotionService;

    @Autowired
    public void setPromotionService(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    @PostMapping(value="/createPromotion", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMINPHARMACY')")
    public ResponseEntity<Promotion> createPromotion(@RequestBody PromotionDTO promotionDTO) {
        Promotion promotion = promotionService.createPromotion(promotionDTO);

        return new ResponseEntity<Promotion>(promotion, HttpStatus.CREATED);
    }

}
