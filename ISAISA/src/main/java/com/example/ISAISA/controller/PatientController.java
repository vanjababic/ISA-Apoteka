package com.example.ISAISA.controller;

import com.example.ISAISA.DTO.*;
import com.example.ISAISA.model.*;

import com.example.ISAISA.repository.AppointmentRepository;
import com.example.ISAISA.repository.CounselingRepository;
import com.example.ISAISA.repository.PharmacyRepository;
import com.example.ISAISA.service.ComplaintService;

import com.example.ISAISA.service.MedicationService;

import com.example.ISAISA.service.PatientService;
import com.example.ISAISA.service.UserServiceDetails;
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

import java.util.*;

@RestController
@RequestMapping(value = "/patients")
public class PatientController {

    private PatientService patientService;
    private MedicationService medicationService;


    @Autowired
    private UserServiceDetails userDetailsService;

    @Autowired
    private PharmacyRepository pharmacyRepository;

    @Autowired
    private ComplaintService complaintService;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private CounselingRepository counselingRepository;

    @Autowired
    public void setMedicationService(MedicationService medicationService) {this.medicationService=medicationService;}


    @Autowired
    public void setPatientService(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/{patientId}")
    @PreAuthorize("hasAuthority('ROLE_PATIENT')")
    public Optional<Patient> loadById(@PathVariable Integer patientId) {
        return this.patientService.findById(patientId);
    }

    @GetMapping(value="/patient",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<Patient>getPatient () {
        Patient user = (Patient) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping(value="/patientChangeInfo", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<Patient> changeAdminInfo(@RequestBody UserChangeDTO userDTO) {

        Patient user = patientService.changePatientInfo(userDTO);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping(value="/promotion",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<Patient> subscribePromotion(@RequestBody IdDto idDto) {

        Patient user = (Patient) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Pharmacy pharmacy= pharmacyRepository.getOne(idDto.getId());

        user= patientService.addPromotion(user,pharmacy);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping(value = "/change-password", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<?> changePassword(@RequestBody AuthenticationController.PasswordChanger passwordChanger) {

        userDetailsService.changePassword(passwordChanger.oldPassword, passwordChanger.newPassword);

        Map<String, String> result = new HashMap<String, String>();
        result.put("result", "success");

        return ResponseEntity.accepted().body(result);
    }

    @GetMapping(value="/allpharmaciespromotion",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<Set<Pharmacy>> getAllPharmaciesPromotion() {

        Patient user = (Patient) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Set<Pharmacy> pharmacies = user.getPharmacies_promotions();


        return new ResponseEntity<Set<Pharmacy>>(pharmacies, HttpStatus.OK);
    }

    @GetMapping(value="/allSearchPatientsDerma",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('DERMATOLOGIST')")
    public ResponseEntity<List<PatientSearchDto>> getAllSearchPatientsDoctor() {
        //Dermatologist user = (Dermatologist) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<PatientSearchDto> patients = patientService.getAllSearchPatients();

        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @PostMapping(value="/searchPatientsDerma", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('DERMATOLOGIST')")
    public ResponseEntity<Set<PatientSearchDto>> getSearchPatientByFirstNameAndLastName(@RequestBody PatientSearchDto patientSearchDto) {

        Set<PatientSearchDto> patients = patientService.getPatientByFirstNameAndLastName(patientSearchDto.getFirstName(), patientSearchDto.getLastName());

        return new ResponseEntity<>(patients, HttpStatus.OK);
    }


    @GetMapping(value="/allcomplaintpharmacists")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<List<Pharmacist>> getPharmacistsComplaint() {

        Patient user = (Patient) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Counseling> counselings= counselingRepository.findAllByPatient(user);

        List<Pharmacist> pharmacists= new ArrayList<>();

        for (Counseling counseling: counselings){
            Pharmacist pharmacist=counseling.getPharmacist();
            pharmacists.add(pharmacist);
        }


        return new ResponseEntity<>(pharmacists, HttpStatus.OK);
    }

    @GetMapping(value="/allcomplaintdermatologist")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<List<Dermatologist>> getDermatologistComplaint() {

        Patient user = (Patient) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Set<Appointment> appointments= appointmentRepository.findAllByPatient(user);

        List<Dermatologist> dermatologists= new ArrayList<>();

        for (Appointment appointment: appointments){
            Dermatologist dermatologist=appointment.getDermatologist();
            dermatologists.add(dermatologist);
        }


        return new ResponseEntity<>(dermatologists, HttpStatus.OK);
    }

    @GetMapping(value="/allcomplaintpharmacies")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<Set<Pharmacy>> getPharmacyComplaint() {

        Patient user = (Patient) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Set<Appointment> appointments= appointmentRepository.findAllByPatient(user);

        Set<Pharmacy> pharmacies= new HashSet<>();

        for (Appointment appointment: appointments){
            Pharmacy pharmacy=appointment.getPharmacy_appointment();
            pharmacies.add(pharmacy);
        }
        List<Counseling> counselings= counselingRepository.findAllByPatient(user);
        for (Counseling counseling: counselings){
            Pharmacy pharmacy=counseling.getPharmacist().getPharmacy();
            pharmacies.add(pharmacy);
        }

        return new ResponseEntity<>(pharmacies, HttpStatus.OK);
    }

    @PostMapping("/complaintPharmacist")
    public ResponseEntity<Complaint> addComplaintPharmacist(@RequestBody ReplyDTO replyDTO, UriComponentsBuilder ucBuilder) throws ResourceConflictException, Exception {
        Patient user = (Patient) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Complaint complaint = this.complaintService.saveComplaintPharmacist(replyDTO,user);


        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{userId}").buildAndExpand(complaint.getId()).toUri());
        return new ResponseEntity<Complaint>(complaint, HttpStatus.CREATED);
    }

    @PostMapping("/unsubscribe")
    public ResponseEntity<Patient> unsubscribePromotion(@RequestBody IdDto idDto, UriComponentsBuilder ucBuilder) throws ResourceConflictException, Exception {
        Patient user1 = (Patient) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Pharmacy pharmacy=pharmacyRepository.findOneById(idDto.getId());

        Patient user=patientService.deletePromotion(user1,pharmacy);


        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{userId}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<Patient>(user, HttpStatus.CREATED);
    }

    @PostMapping("/complaintDermatologist")
    public ResponseEntity<Complaint> addComplaintDermatologist(@RequestBody ReplyDTO replyDTO, UriComponentsBuilder ucBuilder) throws ResourceConflictException, Exception {
        Patient user = (Patient) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Complaint complaint = this.complaintService.saveComplaintDermatologist(replyDTO,user);


        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{userId}").buildAndExpand(complaint.getId()).toUri());
        return new ResponseEntity<Complaint>(complaint, HttpStatus.CREATED);
    }

    @PostMapping("/complaintPharmacy")
    public ResponseEntity<Complaint> addComplaintPharmacy(@RequestBody ReplyDTO replyDTO, UriComponentsBuilder ucBuilder) throws ResourceConflictException, Exception {

        Patient user = (Patient) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Complaint complaint = this.complaintService.saveComplaintPharmacy(replyDTO, user);


        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{userId}").buildAndExpand(complaint.getId()).toUri());
        return new ResponseEntity<Complaint>(complaint, HttpStatus.CREATED);
    }

    @GetMapping(value="/allSearchPatientsPharma",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PHARMACIST')")
    public ResponseEntity<List<PatientSearchDto>> getAllSearchPatientsPharma() {
        //Dermatologist user = (Dermatologist) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<PatientSearchDto> patients = patientService.getAllSearchPatients();

        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @PostMapping(value="/searchPatientsPharma", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PHARMACIST')")
    public ResponseEntity<Set<PatientSearchDto>> getSearchPatientByFirstNameAndLastNamePharma(@RequestBody PatientSearchDto patientSearchDto) {

        Set<PatientSearchDto> patients = patientService.getPatientByFirstNameAndLastName(patientSearchDto.getFirstName(), patientSearchDto.getLastName());

        return new ResponseEntity<>(patients, HttpStatus.OK);
    }



    @PostMapping(value="/allergiemedication", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<Patient> Setallergiemedication(@RequestBody StringDto allergiemedication) {

        Medication medication = medicationService.findByName(allergiemedication.getAllergiemedication());
        Patient user = (Patient) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Set<Medication> medicationList = user.getMedication();
        medicationList.add(medication);
        user.setMedication(medicationList);
        patientService.save(user);


        return new ResponseEntity<>(user, HttpStatus.OK);
    }


}
