package com.example.ISAISA.controller;

import com.example.ISAISA.DTO.*;
import com.example.ISAISA.model.*;
import com.example.ISAISA.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value="/counselings")
public class CounselingController {

    private CounselingService counselingService;

    private PharmacistService pharmacistService;

    private PharmacyService pharmacyService;

    @Autowired
    public void setPharmacyService(PharmacyService pharmacyService){this.pharmacyService=pharmacyService;}
    @Autowired
    public void setPharmacistService(PharmacistService pharmacistService){this.pharmacistService=pharmacistService;}

    @Autowired
    private EmailSenderService emailSenderService;


    @Autowired
    public void setCounselingService(CounselingService counselingService) {
        this.counselingService = counselingService;
    }

    @PostMapping(value="/checkIfCounselingExists", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PHARMACIST')")
    public ResponseEntity<IdDto> checkIfCounselingExists(@RequestBody IdDto PatientIdDto) throws Exception {

        //Integer id = Integer.parseInt(idPatient);

        Pharmacist user = (Pharmacist) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Counseling checkIfPatientHasCounselingNow = counselingService.ifPatientHasCounseling(PatientIdDto.getId(), user);

        if(checkIfPatientHasCounselingNow != null){
            Boolean checkPharmacy = counselingService.checkPharmacy(user);
            if(checkPharmacy){
                IdDto coundelingIdDto = new IdDto(checkIfPatientHasCounselingNow.getId());
                return new ResponseEntity<>(coundelingIdDto, HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(null, HttpStatus.OK);
            }
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }

        /*AppointmentIdDto idDto = new AppointmentIdDto(5);
        return new ResponseEntity<>(idDto, HttpStatus.OK);*/
    }

    @PostMapping(value="/penalPatient", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PHARMACIST')")
    public ResponseEntity<IdDto> penalPatient(@RequestBody IdDto counselingIdDto) throws Exception {

        Integer idPatient = counselingService.penalPatient(counselingIdDto.getId());
        IdDto id = new IdDto(idPatient);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @PostMapping(value="/createCounselingPharmacist", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PHARMACIST')")
    public ResponseEntity<BooleanDto> createCounselingPharmacist(@RequestBody CreateAppointmentDto createCounselingDto) throws Exception {

        Pharmacist user = (Pharmacist) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Boolean counseling = counselingService.createCounselingPharmacist(user, createCounselingDto.getId(),  createCounselingDto.getStartOfAppointment(), createCounselingDto.getEndOfAppointment(), createCounselingDto.getPrice());

        Patient patient = counselingService.findPatient(createCounselingDto.getId());

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(patient.getEmail());
        mailMessage.setSubject("Zakazivanje termina");
        mailMessage.setFrom("isaverifikacija@gmail.com");
        mailMessage.setText("Pregled Vam je zakazan.");
        emailSenderService.sendEmail(mailMessage);

        BooleanDto booleanDto = new BooleanDto(counseling);


        return new ResponseEntity<>(booleanDto, HttpStatus.OK);
    }


    @GetMapping(value="/getCounselingsWeek",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PHARMACIST')")
    public ResponseEntity<List<CalendarDTO>> getCounselingsWeek() {

        Pharmacist user = (Pharmacist) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<CalendarDTO> calendarDTOS = counselingService.getCounselingsWeek(user);

        return new ResponseEntity<>(calendarDTOS, HttpStatus.OK);
    }

    @GetMapping(value="/getCounselingsMonth",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PHARMACIST')")
    public ResponseEntity<List<CalendarDTO>> getCounselingsMonth() {

        Pharmacist user = (Pharmacist) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<CalendarDTO> calendarDTOS = counselingService.getCounselingsMonth(user);

        return new ResponseEntity<>(calendarDTOS, HttpStatus.OK);
    }

    @GetMapping(value="/getCounselingsYear",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PHARMACIST')")
    public ResponseEntity<List<CalendarDTO>> getCounselingsYear() {

        Pharmacist user = (Pharmacist) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<CalendarDTO> calendarDTOS = counselingService.getCounselingsYear(user);

        return new ResponseEntity<>(calendarDTOS, HttpStatus.OK);
    }


    @GetMapping(value="/getCounselingsPharmacy",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMINPHARMACY')")
    public ResponseEntity<Set<Counseling>> getCounselingsByPharmacy() {

        AdminPharmacy user = (AdminPharmacy) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Set<Counseling> counselings = counselingService.getCounselingByPharmacyAfterNow(user.getPharmacy());

        return new ResponseEntity<>(counselings, HttpStatus.OK);
    }

    @PostMapping(value="/changeCounselingPrice", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMINPHARMACY')")
    public ResponseEntity<Counseling> changeCounselingPrice(@RequestBody IdPriceDTO idPriceDTO) {

        Counseling counseling = counselingService.changeCounselingPrice(idPriceDTO.getPrice(), idPriceDTO.getId());

        return new ResponseEntity<>(counseling, HttpStatus.OK);
    }
    @PostMapping(value="/pharmacybydate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<Set<Pharmacy>> findPharmacybyfreepharmacist(@RequestBody BeginofappointmentDto beginofappoinment) throws Exception {
        if(beginofappoinment.getBeginofappointment().isBefore(LocalDateTime.now()))
        {
            throw new Exception("izabrali ste vreme iz proslosti");
        }
        Set<Pharmacy> pharmacies =new HashSet<>();
        List<Pharmacist> pharmacist=pharmacistService.findfreepharmacist(beginofappoinment);
        for(Pharmacist pharmacist1:pharmacist){
            Pharmacy pharmacy1 = pharmacyService.findbyPharmacist(pharmacist1);
            pharmacies.add(pharmacy1);
        }
        return new ResponseEntity<>(pharmacies, HttpStatus.OK);
    }

    @PostMapping(value="/sortpharmacy",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<Set<Pharmacy>> sortpharmacy(@RequestBody BeginofappointmentDto beginofappoinment) throws Exception {
        if(beginofappoinment.getBeginofappointment().isBefore(LocalDateTime.now()))
        {
            throw new Exception("izabrali ste vreme iz proslosti");
        }
        Set<Pharmacy> pharmacies =new HashSet<>();
        List<Pharmacist> pharmacist=pharmacistService.findfreepharmacist(beginofappoinment);
        for(Pharmacist pharmacist1:pharmacist){
            Pharmacy pharmacy1 = pharmacyService.findbyPharmacist(pharmacist1);
            pharmacies.add(pharmacy1);
        }
        Set<Pharmacy> pharmaciesDTOS = new HashSet<>();
        ArrayList<Float> lista_naziva = new ArrayList<>();

        for (Pharmacy pharmacy : pharmacies) {
            lista_naziva.add(pharmacy.getRating());

        }
        java.util.Collections.sort(lista_naziva);
        for(Float naz: lista_naziva){
            Pharmacy pharmacy=pharmacyService.findByRating(naz);
            pharmaciesDTOS.add(pharmacy);
        }

        return new ResponseEntity<>(pharmaciesDTOS, HttpStatus.OK);
    }

    @PostMapping(value="/pharmacistbypharmacy", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<Set<Pharmacist>> findPharmacistbypharmacy(@RequestBody BeginofappointmentDto idDto) throws Exception {

        Pharmacy pharmacy = pharmacyService.findById(idDto.getId());

        Set<Pharmacist> pharmacist_u_apoteci = pharmacistService.findbyPharmacy(pharmacy);
        List<Pharmacist> slobodni_pharmacisti = pharmacistService.findfreepharmacist(idDto);
        Set<Pharmacist> pharmacies = new HashSet<>();
        for(Pharmacist pharmacist1:pharmacist_u_apoteci){
            for(Pharmacist pharmacist2 :slobodni_pharmacisti){
                if(pharmacist1.equals(pharmacist2)){
                    pharmacies.add(pharmacist1);
                }
            }
        }


        return new ResponseEntity<>(pharmacies, HttpStatus.OK);
    }

    @PostMapping(value="/sortpharmacistbypharmacy", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<Set<Pharmacist>> sortpharmacistPharmacistbypharmacy(@RequestBody BeginofappointmentDto idDto) throws Exception {

        Pharmacy pharmacy = pharmacyService.findById(idDto.getId());

        Set<Pharmacist> pharmacist_u_apoteci = pharmacistService.findbyPharmacy(pharmacy);
        List<Pharmacist> slobodni_pharmacisti = pharmacistService.findfreepharmacist(idDto);
        Set<Pharmacist> pharmacies = new HashSet<>();
        for(Pharmacist pharmacist1:pharmacist_u_apoteci){
            for(Pharmacist pharmacist2 :slobodni_pharmacisti){
                if(pharmacist1.equals(pharmacist2)){
                    pharmacies.add(pharmacist1);
                }
            }
        }
        Set<Pharmacist> pharmaciesDTOS = new HashSet<>();
        ArrayList<Float> lista_naziva = new ArrayList<>();

        for (Pharmacist pharmacist : pharmacies) {
            lista_naziva.add(pharmacist.getRating());

        }
        java.util.Collections.sort(lista_naziva);
        for(Float naz: lista_naziva){
            Pharmacist pharmacist=pharmacistService.findByRatings(naz);
            pharmaciesDTOS.add(pharmacist);
        }


        return new ResponseEntity<>(pharmaciesDTOS, HttpStatus.OK);
    }

    @PostMapping(value="/createCounseling", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<Counseling> createCouncseling(@RequestBody BeginofappointmentDto councselingDto) throws Exception {

        Patient user = (Patient) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Counseling councseling = new Counseling();
        Pharmacist pharmacist=pharmacistService.findById(councselingDto.getId());
        LocalDateTime krajpregleda=councselingDto.getBeginofappointment().plusMinutes(20);

        councseling.setBeginofappointment(councselingDto.getBeginofappointment());
        councseling.setEndofappointment(krajpregleda);
        councseling.setPharmacist(pharmacist);
        councseling.setPatient(user);
        councseling.setPrice(200);
        counselingService.save(councseling);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Rezervacija termina kod farmaceuta");
        mailMessage.setFrom("isaverifikacija@gmail.com");
        mailMessage.setText("uspesno ste zakazali pregled kod farmaceuta");

        emailSenderService.sendEmail(mailMessage);

        return new ResponseEntity<>(councseling, HttpStatus.OK);
    }

    @GetMapping(value="/reservedcounseling",produces = MediaType.APPLICATION_JSON_VALUE)// value nije naveden, jer koristimo bazni url
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<List<Counseling>> geReservedAppointments() {

        List<Counseling> appointmentList = counselingService.findAll();
        Patient user = (Patient) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Kreiramo listu DTO objekata
        List<Counseling> appointmentsDTOS = new ArrayList<>();


        for (Counseling counseling : appointmentList) {
            if(counseling.getPatient().getId().equals(user.getId())) {
                appointmentsDTOS.add(counseling);
            }

        }
        return new ResponseEntity<>(appointmentsDTOS, HttpStatus.OK);
    }

    @PostMapping(value="/cancelcounseling", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PATIENT')")
    public void CancelReservation(@RequestBody IdDto idDto) throws Exception {
        Counseling reservation=counselingService.findonebyid(idDto.getId());
        LocalDateTime trenutno_vreme= LocalDateTime.now();
        LocalDateTime date1=reservation.getBeginofappointment();
        LocalDateTime date2=date1.plusDays(1);
        if(trenutno_vreme.isAfter(date2)){

            throw new Exception("Nije moguce otkazivanje proslo je 24h");
        }

        else {
            counselingService.delete(reservation);

        }

    }

    @GetMapping(value="/reservedcounselingbyprice",produces = MediaType.APPLICATION_JSON_VALUE)// value nije naveden, jer koristimo bazni url
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<Set<Counseling>> geReservedAppointmentsbyprice() {


        Patient user = (Patient) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Set<Counseling> appointmentList = counselingService.findAllOrderByPrice(user);
        // Kreiramo listu DTO objekata



        return new ResponseEntity<>(appointmentList, HttpStatus.OK);
    }
}