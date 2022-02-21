package com.example.ISAISA.controller;

import com.example.ISAISA.DTO.*;
import com.example.ISAISA.model.*;
import com.example.ISAISA.service.DermatologistService;
import com.example.ISAISA.service.PharmacyService;
import com.example.ISAISA.service.UserServiceDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping(value = "/dermatologists")
public class DermatologistController {

    private DermatologistService dermatologistService;
    private PharmacyService pharmacyService;

    @Autowired
    private UserServiceDetails userDetailsService;

    @Autowired
    public void setDermatologistService(DermatologistService dermatologistService) {
        this.dermatologistService = dermatologistService;
    }

    @Autowired
    public void setPharmacyService(PharmacyService pharmacyService) {
        this.pharmacyService = pharmacyService;
    }

    @GetMapping(value="/dermatologist",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('DERMATOLOGIST')")
    public ResponseEntity<Dermatologist> getDermatologist() {
        Dermatologist user = (Dermatologist) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping(value="/dermatologistChange", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('DERMATOLOGIST')")
    public ResponseEntity<Dermatologist> changeDermatologistInfo(@RequestBody UserChangeDTO userDTO) {

        Dermatologist user = dermatologistService.changeDermatologistInfo(userDTO);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping(value = "/changepass", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('DERMATOLOGIST')")
    public ResponseEntity<?> changePassword(@RequestBody AuthenticationController.PasswordChanger passwordChanger) {

        userDetailsService.changePassword(passwordChanger.oldPassword, passwordChanger.newPassword);



        Map<String, String> result = new HashMap<String, String>();
        result.put("result", "success");

        return ResponseEntity.accepted().body(result);
    }

    @PostMapping(value = "/changePasswordFirsttime", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('DERMATOLOGIST')")
    public ResponseEntity<?> changePasswordFirsttime(@RequestBody AuthenticationController.PasswordChanger passwordChanger) {

        Dermatologist user = (Dermatologist) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        dermatologistService.changeFlag(user);

        userDetailsService.changePassword(passwordChanger.oldPassword, passwordChanger.newPassword);

        Map<String, String> result = new HashMap<String, String>();
        result.put("result", "success");

        return ResponseEntity.accepted().body(result);
    }

    @GetMapping(value="/adminDermatologists", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMINPHARMACY')")
    public ResponseEntity<Set<DermatologistDTO>> getDermatologistsByAdminPharmacy() {

        AdminPharmacy user = (AdminPharmacy) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Pharmacy pharmacy = user.getPharmacy();

        Set<Dermatologist> dermatologists = dermatologistService.getByPharmacy(pharmacy);

        Set<DermatologistDTO> dermatologistDTOS = new HashSet<>();

        for (Dermatologist d : dermatologists) {
            Set<Pharmacy> pharmacies = new HashSet<>();
            Set<Dermatologist_Pharmacyy> dermatologist_pharmacyys = d.getDermatologist_pharmacies();
            for (Dermatologist_Pharmacyy dp : dermatologist_pharmacyys) {
                pharmacies.add(dp.getPharmacy());
            }

            DermatologistDTO dermatologistDTO = new DermatologistDTO(d.getId(), d.getFirstName(), d.getLastName(), d.getRating(), pharmacies);
            dermatologistDTOS.add(dermatologistDTO);
        }

        return new ResponseEntity<>(dermatologistDTOS, HttpStatus.OK);
    }

    @PostMapping(value="/adminDermatologistsSearch",  consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMINPHARMACY')")
    public ResponseEntity<Set<DermatologistDTO>> getDermatologistByAdminPharmacyAndFirstNameAndLastName(@RequestBody DermatologistDTO dermatologistDTO) {

        AdminPharmacy user = (AdminPharmacy) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Set<Dermatologist> dermatologists = dermatologistService.getDermatologistsByPharmacyAndFirstNameAndLastName(user.getPharmacy(), dermatologistDTO.getFirstName(), dermatologistDTO.getLastName());
        Set<DermatologistDTO> dermatologistDTOS = new HashSet<>();

        for (Dermatologist d : dermatologists) {
            Set<Pharmacy> pharmacies = new HashSet<>();
            Set<Dermatologist_Pharmacyy> dermatologist_pharmacyys = d.getDermatologist_pharmacies();
            for (Dermatologist_Pharmacyy dp : dermatologist_pharmacyys) {
                pharmacies.add(dp.getPharmacy());
            }

            DermatologistDTO dermatologistDTO1 = new DermatologistDTO(d.getId(), d.getFirstName(), d.getLastName(), d.getRating(), pharmacies);
            dermatologistDTOS.add(dermatologistDTO1);
        }

        return new ResponseEntity<>(dermatologistDTOS, HttpStatus.OK);
    }

    @PostMapping(value="/adminDermatologistsAdd", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMINPHARMACY')")
    public ResponseEntity<Dermatologist_Pharmacyy> saveDermatologist(@RequestBody DermatologistDTO dermatologistDTO) throws Exception {

        AdminPharmacy user = (AdminPharmacy) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Dermatologist dermatologist = dermatologistService.findByEmail(dermatologistDTO.getEmail());
        Pharmacy pharmacy = user.getPharmacy();
        Dermatologist_Pharmacyy dermatologist_pharmacyy = dermatologistService.addToPharmacy(dermatologist, dermatologistDTO.getBeginOfWork(), dermatologistDTO.getEndOfWork(), pharmacy);

        return new ResponseEntity<>(dermatologist_pharmacyy, HttpStatus.OK);
    }

    @PostMapping(value="/adminDermatologistsFilter", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMINPHARMACY')")
    public ResponseEntity<Set<DermatologistDTO>> filterDermatologists(@RequestBody FilterEmployeesDTO pharmacistDTO) {

        //Pharmacy pharmacy = pharmacyService.findByName(pharmacistDTO.getPharmacyName());
        AdminPharmacy user = (AdminPharmacy) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Pharmacy pharmacy = user.getPharmacy();

        Float ratingUnder = (float) pharmacistDTO.getRatingUnder();
        Float ratingOver = (float) pharmacistDTO.getRatingOver();

        Set<Dermatologist> dermatologists = dermatologistService.getDermatologistsByRatingBetweenAndPharmacyName(ratingOver, ratingUnder, pharmacy);
        Set<DermatologistDTO> dermatologistDTOS = new HashSet<>();

         for (Dermatologist d : dermatologists) {
            Set<Pharmacy> pharmacies = new HashSet<>();
            Set<Dermatologist_Pharmacyy> dermatologist_pharmacyys = d.getDermatologist_pharmacies();
            for (Dermatologist_Pharmacyy dp : dermatologist_pharmacyys) {
                pharmacies.add(dp.getPharmacy());
            }

            DermatologistDTO dermatologistDTO1 = new DermatologistDTO(d.getId(), d.getFirstName(), d.getLastName(), d.getRating(), pharmacies);
            dermatologistDTOS.add(dermatologistDTO1);
        }

        return new ResponseEntity<>(dermatologistDTOS, HttpStatus.OK);
    }

    @PostMapping(value="/dermatologistDelete", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMINPHARMACY')")
    public void deleteDermatologist(@RequestBody IdDto idDto) throws Exception {

        AdminPharmacy user = (AdminPharmacy) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Pharmacy pharmacy = user.getPharmacy();

        dermatologistService.removeDermatologistFromPharmacy(idDto.getId(), pharmacy);
    }

    @GetMapping(value="/allDermatologists", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<Set<DermatologistDTO>> getDermatologistsy() {

        Set<Dermatologist> dermatologists = dermatologistService.getAll();

        Set<DermatologistDTO> dermatologistDTOS = new HashSet<>();

        for (Dermatologist d : dermatologists) {
            Set<Pharmacy> pharmacies = new HashSet<>();
            Set<Dermatologist_Pharmacyy> dermatologist_pharmacyys = d.getDermatologist_pharmacies();
            for (Dermatologist_Pharmacyy dp : dermatologist_pharmacyys) {
                pharmacies.add(dp.getPharmacy());
            }

            DermatologistDTO dermatologistDTO = new DermatologistDTO(d.getId(), d.getFirstName(), d.getLastName(), d.getRating(), pharmacies);
            dermatologistDTOS.add(dermatologistDTO);
        }

        return new ResponseEntity<>(dermatologistDTOS, HttpStatus.OK);
    }

    @PostMapping(value="/allDermatologistsSearch",  consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<Set<DermatologistDTO>> getAllDermatologistByAdminPharmacyAndFirstNameAndLastName(@RequestBody DermatologistDTO dermatologistDTO) {

        Set<Dermatologist> dermatologists = dermatologistService.getDermatologistsByFirstNameAndLastName(dermatologistDTO.getFirstName(), dermatologistDTO.getLastName());
        Set<DermatologistDTO> dermatologistDTOS = new HashSet<>();

        for (Dermatologist d : dermatologists) {
            Set<Pharmacy> pharmacies = new HashSet<>();
            Set<Dermatologist_Pharmacyy> dermatologist_pharmacyys = d.getDermatologist_pharmacies();
            for (Dermatologist_Pharmacyy dp : dermatologist_pharmacyys) {
                pharmacies.add(dp.getPharmacy());
            }

            DermatologistDTO dermatologistDTO1 = new DermatologistDTO(d.getId(), d.getFirstName(), d.getLastName(), d.getRating(), pharmacies);
            dermatologistDTOS.add(dermatologistDTO1);
        }

        return new ResponseEntity<>(dermatologistDTOS, HttpStatus.OK);
    }

    @PostMapping(value="/allDermatologistsFilter", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<Set<DermatologistDTO>> filterAllDermatologists(@RequestBody FilterEmployeesDTO pharmacistDTO) {

        Pharmacy pharmacy = pharmacyService.findByName(pharmacistDTO.getPharmacyName());

        Float ratingUnder = (float) pharmacistDTO.getRatingUnder();
        Float ratingOver = (float) pharmacistDTO.getRatingOver();

        Set<Dermatologist> dermatologists = dermatologistService.filterDermatologists(ratingOver, ratingUnder, pharmacy);
        Set<DermatologistDTO> dermatologistDTOS = new HashSet<>();

        for (Dermatologist d : dermatologists) {
            Set<Pharmacy> pharmacies = new HashSet<>();
            Set<Dermatologist_Pharmacyy> dermatologist_pharmacyys = d.getDermatologist_pharmacies();
            for (Dermatologist_Pharmacyy dp : dermatologist_pharmacyys) {
                pharmacies.add(dp.getPharmacy());
            }

            DermatologistDTO dermatologistDTO1 = new DermatologistDTO(d.getId(), d.getFirstName(), d.getLastName(), d.getRating(), pharmacies);
            dermatologistDTOS.add(dermatologistDTO1);
        }

        return new ResponseEntity<>(dermatologistDTOS, HttpStatus.OK);
    }

    @PostMapping(value="/dermatologistsByPharmacy", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<Set<DermatologistDTO>> getDermatologistsByPharmacy(@RequestBody IdDto idDto) {

        Pharmacy pharmacy = pharmacyService.findById(idDto.getId());

        Set<Dermatologist> dermatologists = dermatologistService.getByPharmacy(pharmacy);

        Set<DermatologistDTO> dermatologistDTOS = new HashSet<>();

        for (Dermatologist d : dermatologists) {
            Set<Pharmacy> pharmacies = new HashSet<>();
            Set<Dermatologist_Pharmacyy> dermatologist_pharmacyys = d.getDermatologist_pharmacies();
            for (Dermatologist_Pharmacyy dp : dermatologist_pharmacyys) {
                pharmacies.add(dp.getPharmacy());
            }

            DermatologistDTO dermatologistDTO = new DermatologistDTO(d.getId(), d.getFirstName(), d.getLastName(), d.getRating(), pharmacies);
            dermatologistDTOS.add(dermatologistDTO);
        }

        return new ResponseEntity<>(dermatologistDTOS, HttpStatus.OK);
    }
}
