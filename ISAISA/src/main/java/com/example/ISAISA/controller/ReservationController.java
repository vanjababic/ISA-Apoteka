package com.example.ISAISA.controller;

import com.example.ISAISA.DTO.BooleanDto;
import com.example.ISAISA.DTO.IdDto;
import com.example.ISAISA.DTO.ReservationDto;
import com.example.ISAISA.model.*;
import com.example.ISAISA.repository.PharmacyMedicationRepository;
import com.example.ISAISA.service.EmailSenderService;
import com.example.ISAISA.service.MedicationService;
import com.example.ISAISA.service.PharmacyService;
import com.example.ISAISA.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/reservations")
public class ReservationController {
    private ReservationService reservationService;
    private MedicationService medicationService;
    private PharmacyService pharmacyService;
    private PharmacyMedicationRepository pharmacyMedicationRepository;

    @Autowired
    public void setPharmacyMedicationRepository(PharmacyMedicationRepository pharmacyMedicationRepository) {
        this.pharmacyMedicationRepository = pharmacyMedicationRepository;
    }


    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    public void setReservationService(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Autowired
    public void setMedicationService(MedicationService medicationService) {
        this.medicationService = medicationService;
    }
    @Autowired
    public void setPharmacyService(PharmacyService pharmacyService) {
        this.pharmacyService = pharmacyService;
    }


    @PostMapping(value="/createReservation", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationDto reservationdto) throws Exception {

        Patient user = (Patient) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Medication medication =medicationService.findByName(reservationdto.getName());
        Pharmacy pharmacy = pharmacyService.findById(reservationdto.getId());
        LocalDateTime dateofreservation=reservationdto.getDateofreservation();
        if(reservationdto.getDateofreservation().isBefore(LocalDateTime.now())){
            throw new Exception("uneli ste vreme iz proslosti");
        }
        Reservation reservation1 = new Reservation();
        reservation1.setPatient(user);
        reservation1.setMedication(medication);
        reservation1.setPharmacy(pharmacy);
        reservation1.setDateofreservation(dateofreservation);
        reservation1.setMedicationtaken(false);
        reservationService.save(reservation1);


        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Rezervacija leka");
        mailMessage.setFrom("isaverifikacija@gmail.com");
        mailMessage.setText("uspesno ste rezervisali lek, jedinstveni broj rezervacije je " + reservation1.getId() );

        emailSenderService.sendEmail(mailMessage);

        LocalDate today = LocalDate.now();
        Set<PharmacyMedication> pharmacyMedications = pharmacyMedicationRepository.findAllByPharmacyAndMedication(pharmacy, medication);
        for(PharmacyMedication i: pharmacyMedications){
            if(today.isAfter(i.getBeginPriceValidity()) && today.isBefore(i.getEndPriceValidity())){

                i.setQuantity(i.getQuantity()-1);
                pharmacyMedicationRepository.save(i);
                break;
            }
        }


        return new ResponseEntity<>(reservation1, HttpStatus.OK);
    }
    
    @GetMapping(value="/reservedMedications",produces = MediaType.APPLICATION_JSON_VALUE)// value nije naveden, jer koristimo bazni url
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<List<Reservation>> getReservations() {

        List<Reservation> reservationsList=reservationService.findAll();



        Patient user = (Patient) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Kreiramo listu DTO objekata
        List<Reservation> reservationDtos = new ArrayList<>();

        for (Reservation reservation : reservationsList) {
            Reservation reservationDTO = new Reservation(reservation.getId(),reservation.getPatient(),reservation.getPharmacy(),reservation.getMedication(),reservation.getDateofreservation(),reservation.getMedicationtaken());
                if(reservationDTO.getPatient().getId().equals(user.getId())) {
                    reservationDtos.add(reservationDTO);
                }

        }
        return new ResponseEntity<>(reservationDtos, HttpStatus.OK);
    }

    @PostMapping(value="/cancelreservation", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PATIENT')")
    public void CancelReservation(@RequestBody IdDto idDto) throws Exception {
        Reservation reservation=reservationService.findById(idDto.getId());
        LocalDateTime trenutno_vreme= LocalDateTime.now();
        LocalDateTime date1=reservation.getDateofreservation();
        LocalDateTime date2=date1.plusDays(1);
        if(trenutno_vreme.isAfter(date2)){

            throw new Exception("Nije moguce otkazivanje proslo je 24h");
        }

        else {

            LocalDate today = LocalDate.now();
            Set<PharmacyMedication> pharmacyMedications = pharmacyMedicationRepository.findAllByPharmacyAndMedication(reservation.getPharmacy(), reservation.getMedication());
            for(PharmacyMedication i: pharmacyMedications){
                if(today.isAfter(i.getBeginPriceValidity()) && today.isBefore(i.getEndPriceValidity())){
                    i.setQuantity(i.getQuantity()+1);
                    pharmacyMedicationRepository.save(i);
                    reservationService.delete(reservation);
                    break;
                }
            }

        }

    }


    @PostMapping(value="/checkIfReservationExists", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PHARMACIST')")
    public ResponseEntity<BooleanDto> CheckIfReservationExists(@RequestBody IdDto idDto) throws Exception {

        Pharmacist user = (Pharmacist) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Reservation reservation = reservationService.findReservation(idDto.getId());

        Boolean exists;
        if(reservation!=null){
            Boolean pharmacyCheck = reservationService.checkPharmacy(reservation.getPharmacy(), user.getPharmacy());
            if(pharmacyCheck) {
                exists = true;
            }else {
                exists = false;
            }
        }else {
            exists = false;
        }

        BooleanDto booleanDto = new BooleanDto(exists);
        return new ResponseEntity<>(booleanDto, HttpStatus.OK);
    }


    @PostMapping(value="/giveMedication", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PHARMACIST')")
    public void giveMedication(@RequestBody IdDto idDto) throws Exception {
        reservationService.giveMedication(idDto.getId());

        Reservation reservation = reservationService.findById(idDto.getId());
        Patient patient = reservation.getPatient();

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(patient.getEmail());
        mailMessage.setSubject("Izdavanje leka");
        mailMessage.setFrom("isaverifikacija@gmail.com");
        mailMessage.setText("Lek je izdat "+reservation.getMedication().getName());
        emailSenderService.sendEmail(mailMessage);



    }

}
