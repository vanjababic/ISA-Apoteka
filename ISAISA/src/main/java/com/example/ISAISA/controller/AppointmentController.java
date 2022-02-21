package com.example.ISAISA.controller;

import com.example.ISAISA.DTO.*;
import com.example.ISAISA.model.*;
import com.example.ISAISA.repository.PharmacyRepository;
import com.example.ISAISA.service.AppointmentService;
import com.example.ISAISA.service.DermatologistService;

import com.example.ISAISA.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import java.time.LocalDateTime;
import java.util.Set;

@RestController
@RequestMapping(value="/appointments")
public class AppointmentController {

    private AppointmentService appointmentService;
    private DermatologistService dermatologistService;
    private PharmacyRepository pharmacyRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    public void setAppointmentService(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @Autowired
    public void setDermatologistService(DermatologistService dermatologistService) {
        this.dermatologistService = dermatologistService;
    }

    @Autowired
    public void setPharmacyRepository(PharmacyRepository pharmacyRepository) {
        this.pharmacyRepository = pharmacyRepository;
    }

    @PostMapping(value="/checkIfAppointmentExists", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('DERMATOLOGIST')")
    public ResponseEntity<IdDto> checkIfAppointmentExists(@RequestBody IdDto PatientIdDto) throws Exception {

        //Integer id = Integer.parseInt(idPatient);

        Dermatologist user = (Dermatologist) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Appointment checkIfPatientHasAppointmentNow = appointmentService.ifPatientHasAppointment(PatientIdDto.getId(), user);

        if(checkIfPatientHasAppointmentNow != null){
            Boolean checkPharmacy = appointmentService.checkPharmacy(checkIfPatientHasAppointmentNow, user);
            if(checkPharmacy){
                IdDto appointmentIdDto = new IdDto(checkIfPatientHasAppointmentNow.getId());
                return new ResponseEntity<>(appointmentIdDto, HttpStatus.OK);
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
    @PreAuthorize("hasRole('DERMATOLOGIST')")
    public ResponseEntity<IdDto> penalPatient(@RequestBody IdDto AppointmentIdDto) throws Exception {

        Integer idPatient = appointmentService.penalPatient(AppointmentIdDto.getId());
        IdDto id = new IdDto(idPatient);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }



    @PostMapping(value="/createAvailableAppointment", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMINPHARMACY')")
    public ResponseEntity<Appointment> createAvailableAppointment(@RequestBody AppointmentDTO appointment) throws Exception {

        AdminPharmacy user = (AdminPharmacy) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Pharmacy pharmacy = user.getPharmacy();
        Integer dermatologist_id =appointment.getDermatologist();
        Dermatologist dermatologist = dermatologistService.findById(dermatologist_id);

        LocalDateTime endofwork = appointment.getBeginofappointment().plusMinutes(appointment.getDuration());

        Appointment appointment1 = new Appointment();
        appointment1.setBeginofappointment(appointment.getBeginofappointment());
        appointment1.setEndofappointment(endofwork);
        appointment1.setDermatologist(dermatologist);
        appointment1.setPrice(appointment.getPrice());
        appointment1.setPharmacy_appointment(pharmacy);

        appointment1 = appointmentService.saveAvailable(appointment1);

        return new ResponseEntity<>(appointment1, HttpStatus.OK);
    }

    @GetMapping(value="/unreservedappointment",produces = MediaType.APPLICATION_JSON_VALUE)// value nije naveden, jer koristimo bazni url
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<List<Appointment>> getUnreservedAppointments() {

        List<Appointment> appointmentList = this.appointmentService.findAll();

        // Kreiramo listu DTO objekata
        List<Appointment> appointmentsDTOS = new ArrayList<>();

        for (Appointment appointment : appointmentList) {
            Appointment appointmentDTO = new Appointment(appointment.getId(),appointment.getPatient(),appointment.getDermatologist(),appointment.getBeginofappointment(),appointment.getEndofappointment(),appointment.getPrice());
            if(appointmentDTO.getPatient()==null) {
                appointmentsDTOS.add(appointmentDTO);
            }
        }
        return new ResponseEntity<>(appointmentsDTOS, HttpStatus.OK);
    }

    @PostMapping(value="/reserveappointment", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<Appointment> Reserveappointment(@RequestBody IdDto idDto) {
        Patient user = (Patient) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Appointment appointment=appointmentService.findById(idDto.getId());
        appointment.setPatient(user);
        appointmentService.save(appointment);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Rezervacija termina");
        mailMessage.setFrom("isaverifikacija@gmail.com");
        mailMessage.setText("uspesno ste zakazali pregled");
        emailSenderService.sendEmail(mailMessage);

        return new ResponseEntity(appointment, HttpStatus.OK);
    }

    @GetMapping(value="/reservedappointment",produces = MediaType.APPLICATION_JSON_VALUE)// value nije naveden, jer koristimo bazni url
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<List<Appointment>> geReservedAppointments() {
        List<Appointment> appointmentList = this.appointmentService.findAll();
        Patient user = (Patient) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Kreiramo listu DTO objekata
        List<Appointment> appointmentsDTOS = new ArrayList<>();

        for (Appointment appointment : appointmentList) {
            Appointment appointmentDTO = new Appointment(appointment.getId(),appointment.getPatient(),appointment.getDermatologist(),appointment.getBeginofappointment(),appointment.getEndofappointment(),appointment.getPrice());
          if(appointmentDTO.getPatient()!=null){
            if(appointmentDTO.getPatient().getId().equals(user.getId())) {
                appointmentsDTOS.add(appointmentDTO);
            }
          }

        }
        return new ResponseEntity<>(appointmentsDTOS, HttpStatus.OK);
    }

    @PostMapping(value="/cancelappointment", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<Appointment> Cancelppointment(@RequestBody IdDto idDto) throws Exception {
        Appointment appointment=appointmentService.findById(idDto.getId());
        LocalDateTime trenutno_vreme=LocalDateTime.now();
        LocalDateTime date1=appointment.getBeginofappointment();
        LocalDateTime date2=date1.minusDays(1);
        if(trenutno_vreme.isAfter(date2)){

            throw new Exception("Nije moguce otkazivanje proslo je 24h");
        }

        else {
            appointment.setPatient(null);
            appointmentService.save(appointment);

        }

        return new ResponseEntity(appointment, HttpStatus.OK);
    }

    @PostMapping(value="/existingAppointmentsDermatologist", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('DERMATOLOGIST')")
    public ResponseEntity<List<Appointment>> existingFreeAppointmentsDermatologist(@RequestBody IdDto examinationId) throws Exception {

        Dermatologist user = (Dermatologist) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Appointment> appointments = appointmentService.findFreeAppointmentsForDermatologist(user, examinationId.getId());


        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }


    @PostMapping(value="/saveAppointmentDermatologist", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('DERMATOLOGIST')")
    public ResponseEntity<Appointment> saveAppointmentDermatologist(@RequestBody SaveAppointment saveAppointment) throws Exception {

        Dermatologist user = (Dermatologist) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Appointment appointment = appointmentService.saveAppointmentDermatologist(saveAppointment.getId(), saveAppointment.getAppointmentId());

        Patient patient = appointmentService.findPatient(saveAppointment.getId());

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(patient.getEmail());
        mailMessage.setSubject("Zakazivanje termina");
        mailMessage.setFrom("isaverifikacija@gmail.com");
        mailMessage.setText("Pregled Vam je zakazan.");
        emailSenderService.sendEmail(mailMessage);

        return new ResponseEntity<>(appointment, HttpStatus.OK);
    }

    @PostMapping(value="/createAppointmentDermatologist", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('DERMATOLOGIST')")
    public ResponseEntity<BooleanDto> createAppointmentDermatologist(@RequestBody CreateAppointmentDto createAppointmentDto) throws Exception {

        Dermatologist user = (Dermatologist) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Boolean appointment = appointmentService.createAppointmentDermatologist(user, createAppointmentDto.getId(),  createAppointmentDto.getStartOfAppointment(), createAppointmentDto.getEndOfAppointment(), createAppointmentDto.getPrice());

        BooleanDto booleanDto = new BooleanDto(appointment);

        if(appointment) {
            Patient patient = appointmentService.findPatient(createAppointmentDto.getId());
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(patient.getEmail());
            mailMessage.setSubject("Zakazivanje termina");
            mailMessage.setFrom("isaverifikacija@gmail.com");
            mailMessage.setText("Pregled Vam je zakazan.");

            emailSenderService.sendEmail(mailMessage);
        }

        return new ResponseEntity<>(booleanDto, HttpStatus.OK);
    }

    @GetMapping(value="/getAppointmentsWeek",produces = MediaType.APPLICATION_JSON_VALUE)// value nije naveden, jer koristimo bazni url
    @PreAuthorize("hasRole('DERMATOLOGIST')")
    public ResponseEntity<List<CalendarDTO>> getAppointmentsWeek() {

        Dermatologist user = (Dermatologist) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<CalendarDTO> calendarDTOS = appointmentService.getAppointmentsWeek(user);

        return new ResponseEntity<>(calendarDTOS, HttpStatus.OK);
    }

    @GetMapping(value="/getAppointmentsMonth",produces = MediaType.APPLICATION_JSON_VALUE)// value nije naveden, jer koristimo bazni url
    @PreAuthorize("hasRole('DERMATOLOGIST')")
    public ResponseEntity<List<CalendarDTO>> getAppointmentsMonth() {

        Dermatologist user = (Dermatologist) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<CalendarDTO> calendarDTOS = appointmentService.getAppointmentsMonth(user);

        return new ResponseEntity<>(calendarDTOS, HttpStatus.OK);
    }

    @GetMapping(value="/getAppointmentsYear",produces = MediaType.APPLICATION_JSON_VALUE)// value nije naveden, jer koristimo bazni url
    @PreAuthorize("hasRole('DERMATOLOGIST')")
    public ResponseEntity<List<CalendarDTO>> getAppointmentsYear() {

        Dermatologist user = (Dermatologist) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<CalendarDTO> calendarDTOS = appointmentService.getAppointmentsYear(user);

        return new ResponseEntity<>(calendarDTOS, HttpStatus.OK);
    }


    @GetMapping(value="/getAppointmentsPharmacy",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMINPHARMACY')")
    public ResponseEntity<Set<Appointment>> getAppointmentsByPharmacy() {

        AdminPharmacy user = (AdminPharmacy) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Set<Appointment> appointments = appointmentService.getAppointmentByPharmacyAfterNow(user.getPharmacy());

        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @PostMapping(value="/changeAppointmentPrice", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMINPHARMACY')")
    public ResponseEntity<Appointment> changeAppointmentPrice(@RequestBody IdPriceDTO idPriceDTO){

        Appointment appointment = appointmentService.changeAppointment(idPriceDTO.getPrice(), idPriceDTO.getId());

        return new ResponseEntity<>(appointment, HttpStatus.OK);
    }

    @PostMapping(value="/availableAppointmentsByPharmacy", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<Set<Appointment>> getAvailableAppointments(@RequestBody IdDto idDto){

        Pharmacy pharmacy = pharmacyRepository.findOneById(idDto.getId());
        Set<Appointment> appointments = this.appointmentService.getAvailableAppointmentsByPharmacy(pharmacy);

        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }



    @GetMapping(value="/reservedappointmentsbyprice",produces = MediaType.APPLICATION_JSON_VALUE)// value nije naveden, jer koristimo bazni url
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<Set<Appointment>> geReservedAppointmentsbypricee() {


        Patient user = (Patient) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Set<Appointment> appointmentList = appointmentService.findpatientorderbyprice(user);
        // Kreiramo listu DTO objekata



        return new ResponseEntity<>(appointmentList, HttpStatus.OK);
    }

}
