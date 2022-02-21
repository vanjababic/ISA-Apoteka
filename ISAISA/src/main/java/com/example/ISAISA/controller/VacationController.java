package com.example.ISAISA.controller;

import com.example.ISAISA.DTO.*;
import com.example.ISAISA.model.*;
import com.example.ISAISA.service.VacationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/vacations")
public class VacationController {

    private VacationService vacationService;

    @Autowired
    public void setVacationService(VacationService vacationService) { this.vacationService = vacationService;  }

    @GetMapping(value="/pharmacistRequests", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMINPHARMACY')")
    public ResponseEntity<Set<Vacation>> getPharmacistVacations() {

        AdminPharmacy user = (AdminPharmacy) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Pharmacy pharmacy = user.getPharmacy();

        Set<Vacation> vacations = this.vacationService.findAllByPharmacistNotNull();
        Set<Vacation> vacationsByPharmacy = new HashSet<>();

        for(Vacation vacation : vacations) {
            if (vacation.getPharmacist().getPharmacy().getId().equals(pharmacy.getId())) {
                vacationsByPharmacy.add(vacation);
            }
        }

        return new ResponseEntity<>(vacationsByPharmacy, HttpStatus.OK);
    }

    @GetMapping(value="/dermatologistRequests", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMINSYSTEM')")
    public ResponseEntity<Set<Vacation>> getDermatologistVacations() {

        Set<Vacation> vacations = this.vacationService.findAllByDermatologistNotNull();

        return new ResponseEntity<>(vacations, HttpStatus.OK);
    }

    @PostMapping(value="/approvePharmacist",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMINPHARMACY')")
    public ResponseEntity<Vacation> approveVacationPharmacist(@RequestBody IdDto idDto){
        Vacation vacation = this.vacationService.findById(idDto.getId());
        vacation = this.vacationService.approveVacationPharmacist(vacation);
        return new ResponseEntity<>(vacation, HttpStatus.OK);
    }

    @PostMapping(value="/approveDermatologist",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMINSYSTEM')")
    public ResponseEntity<Vacation> approveVacationDermatologist(@RequestBody IdDto idDto){
        Vacation vacation = this.vacationService.findById(idDto.getId());
        vacation = this.vacationService.approveVacationDermatologist(vacation);
        return new ResponseEntity<>(vacation, HttpStatus.OK);
    }

    @PostMapping(value="/denyPharmacist",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMINPHARMACY')")
    public ResponseEntity<Vacation> denyVacationPharmacist(@RequestBody VacationDenyDTO vacationDenyDTO){
        Vacation vacation = this.vacationService.findById(vacationDenyDTO.getId());
        String reasonDenied = vacationDenyDTO.getReasonDenied();
        vacation = this.vacationService.denyVacationPharmacist(vacation, reasonDenied);
        return new ResponseEntity<>(vacation, HttpStatus.OK);
    }

    @PostMapping(value="/denyDermatologist",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMINSYSTEM')")
    public ResponseEntity<Vacation> denyVacationDermatologist(@RequestBody VacationDenyDTO vacationDenyDTO){
        Vacation vacation = this.vacationService.findById(vacationDenyDTO.getId());
        String reasonDenied = vacationDenyDTO.getReasonDenied();
        vacation = this.vacationService.denyVacationDermatologist(vacation, reasonDenied);
        return new ResponseEntity<>(vacation, HttpStatus.OK);
    }


    @PostMapping(value="/requestVacationDermatologist", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('DERMATOLOGIST')")
    @Transactional
    public ResponseEntity<BooleanDto> RequestVacationDermatologist(@RequestBody VacationDTO vacationDTO) {
        Dermatologist user = (Dermatologist) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Boolean requestVacation = vacationService.requestVacationDermatologist(user, vacationDTO.getBeginVacation(), vacationDTO.getEndVacation(), vacationDTO.getAbsence());

        BooleanDto booleanDto = new BooleanDto(requestVacation);
        return new ResponseEntity(booleanDto, HttpStatus.OK);
    }

    @PostMapping(value="/requestVacationPharmacist", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PHARMACIST')")
    @Transactional
    public ResponseEntity<BooleanDto> requestVacationPharmacist(@RequestBody VacationDTO vacationDTO) {
        Pharmacist user = (Pharmacist) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Boolean requestVacation = vacationService.requestVacationPharmacist(user, vacationDTO.getBeginVacation(), vacationDTO.getEndVacation(), vacationDTO.getAbsence());

        BooleanDto booleanDto = new BooleanDto(requestVacation);
        return new ResponseEntity(booleanDto, HttpStatus.OK);
    }




}
