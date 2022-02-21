package com.example.ISAISA.service;

import com.example.ISAISA.DTO.PromotionDTO;
import com.example.ISAISA.model.AdminPharmacy;
import com.example.ISAISA.model.Patient;
import com.example.ISAISA.model.Pharmacy;
import com.example.ISAISA.model.Promotion;
import com.example.ISAISA.repository.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PromotionService {

    private PromotionRepository promotionRepository;
    private EmailSenderService emailSenderService;

    @Autowired
    public void setPromotionRepository(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    @Autowired
    public void setEmailSenderService(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }

    public Promotion createPromotion(PromotionDTO promotionDTO) {
        AdminPharmacy user = (AdminPharmacy) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Pharmacy pharmacy = user.getPharmacy();

        Promotion promotion = new Promotion(promotionDTO.getDescription(), promotionDTO.getValidFrom(), promotionDTO.getValidUntil(), pharmacy);
        promotion = promotionRepository.save(promotion);

        Set<Patient> subscribed_patients = pharmacy.getPatients_promotions();

        for (Patient p : subscribed_patients) {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(p.getEmail());
            mailMessage.setSubject("Promocija u apoteci: "+ pharmacy.getName());
            mailMessage.setFrom("isaverifikacija@gmail.com");
            mailMessage.setText(promotion.getDescription() + "\nPeriod vazenja promocije: " +
                    promotion.getValidFrom() + " do " + promotion.getValidUntil());

            emailSenderService.sendEmail(mailMessage);
        }

        return promotion;
    }
}
