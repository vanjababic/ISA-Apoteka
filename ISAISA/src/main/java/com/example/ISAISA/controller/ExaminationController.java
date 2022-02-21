package com.example.ISAISA.controller;

import com.example.ISAISA.DTO.*;
import com.example.ISAISA.model.*;
import com.example.ISAISA.service.EmailSenderService;
import com.example.ISAISA.service.ExaminationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/examinations")
public class ExaminationController {

    private ExaminationService examinationService;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    public void setExaminationService(ExaminationService examinationService) {
        this.examinationService = examinationService;
    }

    @GetMapping(value="/getAppointmentId",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('DERMATOLOGIST')")
    public ResponseEntity<IdDto> getExaminationId() {
        Dermatologist user = (Dermatologist) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Appointment appointment = examinationService.findAppointment(user);

        Integer examinationId = examinationService.Save(appointment, user);

        //Integer idExamination = examinationService.createNewExamination(appointment, user);

        IdDto ex = new IdDto(examinationId);
        return new ResponseEntity<>(ex, HttpStatus.OK);
    }


    @PostMapping(value="/writeReport", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('DERMATOLOGIST')")
    public ResponseEntity<IdDto> writeReport(@RequestBody ReportDto reportDto) throws Exception {

        examinationService.writeReport(reportDto.getId(), reportDto.getReport());

        IdDto ex = new IdDto(reportDto.getId());
        return new ResponseEntity<>(ex, HttpStatus.OK);
    }

    @PostMapping(value="/getMedicationsForPrescription",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('DERMATOLOGIST')")
    public ResponseEntity<List<MedicationsForPrescriptionDto>> getMedicationsForPrescription(@RequestBody IdDto idDto) {

        List<String> medicationsForPrescription = examinationService.getMedicationsForPrescription(idDto.getId());

        List<MedicationsForPrescriptionDto> listMedications = new ArrayList<>();

        for(String i : medicationsForPrescription){
            MedicationsForPrescriptionDto medicationsForPrescriptionDto = new MedicationsForPrescriptionDto(i);
            listMedications.add(medicationsForPrescriptionDto);
        }

        return new ResponseEntity<>(listMedications, HttpStatus.OK);
    }


    @PostMapping(value="/checkIfMedicationIsAvailable",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('DERMATOLOGIST')")
    public ResponseEntity<BooleanDto> checkIfMedicationIsAvailable(@RequestBody MedicationExaminationDto medicationExaminationDto) {

        Boolean isMedicationAvailable = examinationService.isMedicationAvailable(medicationExaminationDto.getName(), medicationExaminationDto.getId());

        if(!isMedicationAvailable){
            AdminPharmacy adminPharmacy = examinationService.findAdminPharmacy(medicationExaminationDto.getId());
                SimpleMailMessage mailMessage = new SimpleMailMessage();
                mailMessage.setTo(adminPharmacy.getEmail());
                mailMessage.setSubject("Lek nedostupan");
                mailMessage.setFrom("isaverifikacija@gmail.com");
                mailMessage.setText("Nedostupan lek u apoteci " + medicationExaminationDto.getName());

                emailSenderService.sendEmail(mailMessage);
        }

        BooleanDto booleanDto = new BooleanDto(isMedicationAvailable);
        return new ResponseEntity<>(booleanDto, HttpStatus.OK);

    }

    @PostMapping(value="/getAlternativeMedications",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('DERMATOLOGIST')")
    public ResponseEntity<List<MedicationsForPrescriptionDto>> getAlternativeMedications(@RequestBody MedicationExaminationDto medicationExaminationDto) {

        List<String> alternativeMedications = examinationService.getAlternativeMedications(medicationExaminationDto.getId(), medicationExaminationDto.getName());

        List<MedicationsForPrescriptionDto> listMedications = new ArrayList<>();

        for(String i : alternativeMedications){
            MedicationsForPrescriptionDto medicationsForPrescriptionDto = new MedicationsForPrescriptionDto(i);
            listMedications.add(medicationsForPrescriptionDto);
        }

        return new ResponseEntity<>(listMedications, HttpStatus.OK);
    }


    @PostMapping(value="/savePrescription", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('DERMATOLOGIST')")
    public ResponseEntity<BooleanDto> savePrescription(@RequestBody SavePrescriptionDto savePrescriptionDto) throws Exception {

        Boolean isDone = examinationService.savePrescription(savePrescriptionDto.getId(), savePrescriptionDto.getName(), savePrescriptionDto.getDuration());

        BooleanDto booleanDto = new BooleanDto(isDone);
        return new ResponseEntity<>(booleanDto, HttpStatus.OK);
    }

    @GetMapping(value="/getExaminatedPatientsDermatologist",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('DERMATOLOGIST')")
    public ResponseEntity<List<ExaminPatientDto>> getExaminatedPatientsDermatologist() {
        Dermatologist user = (Dermatologist) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Appointment> appointments = examinationService.getExaminatedPatients(user);

        List<ExaminPatientDto> examinPatientDtos = new ArrayList<>();
        for(Appointment i: appointments){
            ExaminPatientDto examinPatientDto = new ExaminPatientDto(i.getPatient().getFirstName(), i.getPatient().getLastName(), i.getBeginofappointment().toLocalDate());
            examinPatientDtos.add(examinPatientDto);
        }

        return new ResponseEntity<>(examinPatientDtos, HttpStatus.OK);
    }


    @GetMapping(value="/getExaminatedPatientsDermatologistSortName",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('DERMATOLOGIST')")
    public ResponseEntity<List<ExaminPatientDto>> getExaminatedPatientsDermatologistSortName() {
        Dermatologist user = (Dermatologist) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<ExaminPatientDto> examinPatientDtos = examinationService.getExaminatedPatientsSortName(user);

        return new ResponseEntity<>(examinPatientDtos, HttpStatus.OK);
    }

    @GetMapping(value="/getExaminatedPatientsDermatologistSortLastName",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('DERMATOLOGIST')")
    public ResponseEntity<List<ExaminPatientDto>> getExaminatedPatientsDermatologistSortLastName() {
        Dermatologist user = (Dermatologist) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<ExaminPatientDto> examinPatientDtos = examinationService.getExaminatedPatientsSortLastName(user);

        return new ResponseEntity<>(examinPatientDtos, HttpStatus.OK);
    }

    @GetMapping(value="/getExaminatedPatientsDermatologistSortDate",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('DERMATOLOGIST')")
    public ResponseEntity<List<ExaminPatientDto>> getExaminatedPatientsDermatologistSortDate() {
        Dermatologist user = (Dermatologist) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<ExaminPatientDto> examinPatientDtos = examinationService.getExaminatedPatientsSortDate(user);

        return new ResponseEntity<>(examinPatientDtos, HttpStatus.OK);
    }


//***********************

    @GetMapping(value="/getCounselingId",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PHARMACIST')")
    public ResponseEntity<IdDto> getCounselingId() {

        Pharmacist user = (Pharmacist) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Counseling counseling = examinationService.findCounseling(user);

        Integer examinationId = examinationService.SaveCounseling(counseling, user);

        //Integer idExamination = examinationService.createNewExamination(appointment, user);

        IdDto ex = new IdDto(examinationId);
        return new ResponseEntity<>(ex, HttpStatus.OK);
    }

    @PostMapping(value="/writeReportPharmacist", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PHARMACIST')")
    public ResponseEntity<IdDto> writeReportPharmacist(@RequestBody ReportDto reportDto) throws Exception {

        examinationService.writeReport(reportDto.getId(), reportDto.getReport());

        IdDto ex = new IdDto(reportDto.getId());
        return new ResponseEntity<>(ex, HttpStatus.OK);
    }

    @PostMapping(value="/getMedicationsForPrescriptionPharmacist",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PHARMACIST')")
    public ResponseEntity<List<MedicationsForPrescriptionDto>> getMedicationsForPrescriptionPharmacist(@RequestBody IdDto idDto) {

        List<String> medicationsForPrescription = examinationService.getMedicationsForPrescription(idDto.getId());

        List<MedicationsForPrescriptionDto> listMedications = new ArrayList<>();

        for(String i : medicationsForPrescription){
            MedicationsForPrescriptionDto medicationsForPrescriptionDto = new MedicationsForPrescriptionDto(i);
            listMedications.add(medicationsForPrescriptionDto);
        }

        return new ResponseEntity<>(listMedications, HttpStatus.OK);
    }

    @PostMapping(value="/checkIfMedicationIsAvailablePharma",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PHARMACIST')")
    public ResponseEntity<BooleanDto> checkIfMedicationIsAvailablePharma(@RequestBody MedicationExaminationDto medicationExaminationDto) {

        Pharmacist user = (Pharmacist) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Boolean isMedicationAvailable = examinationService.isMedicationAvailable(medicationExaminationDto.getName(), medicationExaminationDto.getId());

        if(!isMedicationAvailable){
            Set<AdminPharmacy> adminPharmacy = examinationService.findAdminPharmacyPharmacist(medicationExaminationDto.getId(), user);
            for(AdminPharmacy i: adminPharmacy) {
                SimpleMailMessage mailMessage = new SimpleMailMessage();
                mailMessage.setTo(i.getEmail());
                mailMessage.setSubject("Lek nedostupan");
                mailMessage.setFrom("isaverifikacija@gmail.com");
                mailMessage.setText("Nedostupan lek u apoteci " + medicationExaminationDto.getName());

                emailSenderService.sendEmail(mailMessage);
            }

        }


        BooleanDto booleanDto = new BooleanDto(isMedicationAvailable);
        return new ResponseEntity<>(booleanDto, HttpStatus.OK);

    }

    @PostMapping(value="/savePrescriptionPharmacist", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PHARMACIST')")
    public ResponseEntity<BooleanDto> savePrescriptionPharmacist(@RequestBody SavePrescriptionDto savePrescriptionDto) throws Exception {

        Boolean isDone = examinationService.savePrescription(savePrescriptionDto.getId(), savePrescriptionDto.getName(), savePrescriptionDto.getDuration());

        BooleanDto booleanDto = new BooleanDto(isDone);
        return new ResponseEntity<>(booleanDto, HttpStatus.OK);
    }


    @PostMapping(value="/getAlternativeMedicationsPharmacist",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PHARMACIST')")
    public ResponseEntity<List<MedicationsForPrescriptionDto>> getAlternativeMedicationsPharmacist(@RequestBody MedicationExaminationDto medicationExaminationDto) {

        List<String> alternativeMedications = examinationService.getAlternativeMedications(medicationExaminationDto.getId(), medicationExaminationDto.getName());

        List<MedicationsForPrescriptionDto> listMedications = new ArrayList<>();

        for(String i : alternativeMedications){
            MedicationsForPrescriptionDto medicationsForPrescriptionDto = new MedicationsForPrescriptionDto(i);
            listMedications.add(medicationsForPrescriptionDto);
        }

        return new ResponseEntity<>(listMedications, HttpStatus.OK);
    }


    @GetMapping(value="/getExaminatedPatientsPharmacist",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PHARMACIST')")
    public ResponseEntity<List<ExaminPatientDto>> getExaminatedPatientsPharmacist() {
        Pharmacist user = (Pharmacist) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Counseling> counselings = examinationService.getExaminatedPatientsPharmacist(user);

        List<ExaminPatientDto> examinPatientDtos = new ArrayList<>();
        for(Counseling i: counselings){
            ExaminPatientDto examinPatientDto = new ExaminPatientDto(i.getPatient().getFirstName(), i.getPatient().getLastName(), i.getBeginofappointment().toLocalDate());
            examinPatientDtos.add(examinPatientDto);
        }

        return new ResponseEntity<>(examinPatientDtos, HttpStatus.OK);
    }

    @GetMapping(value="/getExaminatedPatientsPharmacistSortName",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PHARMACIST')")
    public ResponseEntity<List<ExaminPatientDto>> getExaminatedPatientsPharmacistSortName() {
        Pharmacist user = (Pharmacist) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<ExaminPatientDto> examinPatientDtos = examinationService.examinatedPatientsPharmacistSortName(user);

        return new ResponseEntity<>(examinPatientDtos, HttpStatus.OK);
    }

    @GetMapping(value="/getExaminatedPatientsPharmacistSortLastName",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PHARMACIST')")
    public ResponseEntity<List<ExaminPatientDto>> getExaminatedPatientsPharmacistSortLastName() {
        Pharmacist user = (Pharmacist) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<ExaminPatientDto> examinPatientDtos = examinationService.getExaminatedPatientsPharmacistSortLastName(user);

        return new ResponseEntity<>(examinPatientDtos, HttpStatus.OK);
    }

    @GetMapping(value="/getExaminatedPatientsPharmacistSortDate",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PHARMACIST')")
    public ResponseEntity<List<ExaminPatientDto>> getExaminatedPatientsPharmacistSortDate() {
        Pharmacist user = (Pharmacist) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<ExaminPatientDto> examinPatientDtos = examinationService.getExaminatedPatientsPharmacistSortDate(user);

        return new ResponseEntity<>(examinPatientDtos, HttpStatus.OK);
    }

}
