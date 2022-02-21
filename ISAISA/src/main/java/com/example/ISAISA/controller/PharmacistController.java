package com.example.ISAISA.controller;

import com.example.ISAISA.DTO.IdDto;
import com.example.ISAISA.DTO.UserChangeDTO;
import com.example.ISAISA.DTO.FilterEmployeesDTO;
import com.example.ISAISA.DTO.PharmacistDTO;
import com.example.ISAISA.model.AdminPharmacy;
import com.example.ISAISA.model.Dermatologist;
import com.example.ISAISA.model.Pharmacist;
import com.example.ISAISA.model.Pharmacy;
import com.example.ISAISA.service.PharmacistService;
import com.example.ISAISA.service.UserServiceDetails;
import com.example.ISAISA.service.PharmacyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping(value = "/pharmacists")
public class PharmacistController {

    private PharmacistService pharmacistService;
    private PharmacyService pharmacyService;

    @Autowired
    private UserServiceDetails userDetailsService;

    @Autowired
    public void setPharmacistService(PharmacistService pharmacistService) {
        this.pharmacistService = pharmacistService;
    }

    @Autowired
    public void setPharmacyService(PharmacyService pharmacyService) {
        this.pharmacyService = pharmacyService;
    }


    @GetMapping(value="/pharmacist",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PHARMACIST')")
    public ResponseEntity<Pharmacist> getPharmacist() {
        Pharmacist user = (Pharmacist) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping(value="/changePharmacistInfo", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PHARMACIST')")
    public ResponseEntity<Pharmacist> changePharmacistInfo(@RequestBody UserChangeDTO userDTO) {

        Pharmacist user = pharmacistService.changePharmacistInfo(userDTO);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping(value = "/changePasswordFirsttime", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PHARMACIST')")
    public ResponseEntity<?> changePasswordFirsttime(@RequestBody AuthenticationController.PasswordChanger passwordChanger) {

        Pharmacist user = (Pharmacist) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        pharmacistService.changeFlag(user);

        userDetailsService.changePassword(passwordChanger.oldPassword, passwordChanger.newPassword);

        Map<String, String> result = new HashMap<String, String>();
        result.put("result", "success");

        return ResponseEntity.accepted().body(result);
    }

    @PostMapping(value = "/changePass", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PHARMACIST')")
    public ResponseEntity<?> changePassword(@RequestBody AuthenticationController.PasswordChanger passwordChanger) {

        userDetailsService.changePassword(passwordChanger.oldPassword, passwordChanger.newPassword);

        Map<String, String> result = new HashMap<String, String>();
        result.put("result", "success");

        return ResponseEntity.accepted().body(result);
    }

    //**********************************Funkcionalnosti za admina apoteke
    @GetMapping(value="/adminPharmacists")
    @PreAuthorize("hasRole('ADMINPHARMACY')")
    public ResponseEntity<Set<PharmacistDTO>> getPharmacistsByAdminPharmacy() {

        AdminPharmacy user = (AdminPharmacy) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Set<PharmacistDTO> pharmacists = pharmacistService.getPharmacistsByPharmacy(user.getPharmacy());

        return new ResponseEntity<>(pharmacists, HttpStatus.OK);
    }

    @PostMapping(value="/adminPharmacistsSearch", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMINPHARMACY')")
    public ResponseEntity<Set<PharmacistDTO>> getPharmacistsByAdminPharmacyAndFirstNameAndLastName(@RequestBody PharmacistDTO pharmacistDTO) {

        AdminPharmacy user = (AdminPharmacy) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Set<PharmacistDTO> pharmacists = pharmacistService.getPharmacistsByPharmacyAndFirstNameAndLastName(user.getPharmacy(),
                pharmacistDTO.getFirstName(), pharmacistDTO.getLastName());

        return new ResponseEntity<>(pharmacists, HttpStatus.OK);
    }

    @PostMapping(value="/adminPharmacistsAdd", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMINPHARMACY')")
    public ResponseEntity<Pharmacist> savePharmacist(@RequestBody PharmacistDTO pharmacistDTO) {

        AdminPharmacy user = (AdminPharmacy) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        pharmacistDTO.setPharmacy(user.getPharmacy());
        Pharmacist pharmacist = pharmacistService.save(pharmacistDTO);

        return new ResponseEntity<>(pharmacist, HttpStatus.OK);
    }



    @PostMapping(value="/adminPharmacistsFilter", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMINPHARMACY')")
    public ResponseEntity<Set<PharmacistDTO>> filterPharmacists(@RequestBody FilterEmployeesDTO pharmacistDTO) {

        //Pharmacy pharmacy = pharmacyService.findByName(pharmacistDTO.getPharmacyName());
        AdminPharmacy user = (AdminPharmacy) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Pharmacy pharmacy = user.getPharmacy();

        Float ratingUnder = (float) pharmacistDTO.getRatingUnder();
        Float ratingOver = (float) pharmacistDTO.getRatingOver();

        Set<PharmacistDTO> pharmacists = pharmacistService.filterPharmacists(
                ratingOver, ratingUnder, pharmacy);

        return new ResponseEntity<>(pharmacists, HttpStatus.OK);
    }

    @PostMapping(value="/pharmacistDelete", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMINPHARMACY')")
    public void deletePharmacist(@RequestBody IdDto idDto) throws Exception {
        pharmacistService.deletePharmacist(idDto.getId());
    }

    //**********************************Funckionalnosti za pacijenta

    @PostMapping(value="/allPharmacistsFromPharmacy", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<Set<PharmacistDTO>> getAllPharmacistsByPharmacy(@RequestBody IdDto idDto) {

        Pharmacy pharmacy = pharmacyService.findById(idDto.getId());
        Set<PharmacistDTO> pharmacists = pharmacistService.getPharmacistsByPharmacy(pharmacy);

        return new ResponseEntity<>(pharmacists, HttpStatus.OK);
    }

    @GetMapping(value="/allPharmacists", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<Set<PharmacistDTO>> getAll() {

        Set<PharmacistDTO> pharmacists = pharmacistService.getAll();

        return new ResponseEntity<>(pharmacists, HttpStatus.OK);
    }

    @PostMapping(value="/allPharmacistsSearch", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<Set<PharmacistDTO>> getPharmacistsByFirstNameAndLastName(@RequestBody PharmacistDTO pharmacistDTO) {

        Set<PharmacistDTO> pharmacists = pharmacistService.getPharmacistsByFirstNameAndLastName(pharmacistDTO.getFirstName(), pharmacistDTO.getLastName());

        return new ResponseEntity<>(pharmacists, HttpStatus.OK);
    }

    @PostMapping(value="/allPharmacistsFilter", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<Set<PharmacistDTO>> filterAllPharmacists(@RequestBody FilterEmployeesDTO pharmacistDTO) {

        Pharmacy pharmacy = pharmacyService.findByName(pharmacistDTO.getPharmacyName());
        Float ratingUnder = (float) pharmacistDTO.getRatingUnder();
        Float ratingOver = (float) pharmacistDTO.getRatingOver();

        Set<PharmacistDTO> pharmacists = pharmacistService.filterPharmacists(
                ratingOver, ratingUnder, pharmacy);

        return new ResponseEntity<>(pharmacists, HttpStatus.OK);
    }
}
