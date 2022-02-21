package com.example.ISAISA.service;

import com.example.ISAISA.DTO.BooleanDto;
import com.example.ISAISA.DTO.ExaminPatientDto;
import com.example.ISAISA.model.*;
import com.example.ISAISA.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
public class ExaminationService {

    private ExaminationRepository examinationRepository;
    private AppointmentRepository appointmentRepository;
    private MedicationRepository medicationRepository;
    private CounselingRepository counselingRepository;
    private AdminPharmacyRepository adminPharmacyRepository;
    private PharmacyMedicationRepository pharmacyMedicationRepository;

    @Autowired
    public void setPharmacyMedicationRepository(PharmacyMedicationRepository pharmacyMedicationRepository) {
        this.pharmacyMedicationRepository = pharmacyMedicationRepository;
    }

    @Autowired
    public void setAdminPharmacyRepository(AdminPharmacyRepository adminPharmacyRepository) {
        this.adminPharmacyRepository = adminPharmacyRepository;
    }

    @Autowired
    public void setExaminationRepository(ExaminationRepository examinationRepository) {
        this.examinationRepository = examinationRepository;
    }

    @Autowired
    public void setAppointmentRepository(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Autowired
    public void setMedicationRepository(MedicationRepository medicationRepository) {
        this.medicationRepository = medicationRepository;
    }

    @Autowired
    public void setCounselingRepository(CounselingRepository counselingRepository) {
        this.counselingRepository = counselingRepository;
    }

    public Appointment findAppointment(Dermatologist dermatologist){

        LocalTime now = LocalTime.now();
        LocalDate today = LocalDate.now();

        List<Appointment> appointments = appointmentRepository.findAll();

        for (Appointment i : appointments){
            if(today.isEqual(i.getBeginofappointment().toLocalDate())){
                if(now.isAfter(i.getBeginofappointment().toLocalTime()) && now.isBefore(i.getEndofappointment().toLocalTime())){
                    if(i.getDermatologist().getId() == dermatologist.getId()) {
                        return i;
                    }
                }
            }
        }

        return null;
    }

    public Integer Save(Appointment appointment, Dermatologist user) {
        Examination examination = new Examination();
        examination.setExaminationAppointment(appointment);
        examination = examinationRepository.save(examination);
        return examination.getId();
    }

    /*public Integer createNewExamination(Appointment appointment, Dermatologist dermatologist){

        examinationRepository.

        return null;
    }*/

    public void writeReport(Integer examinationId, String report) {

        Examination examination = examinationRepository.findOneById(examinationId);

        examination.setReport(report);
        examination = examinationRepository.save(examination);

    }
    public List<String> getMedicationsForPrescription(Integer examinationId){

        Examination examination = examinationRepository.findOneById(examinationId);

        Patient patient; //= new Patient();
        if(examination.getExaminationAppointment() != null) {
            patient = examination.getExaminationAppointment().getPatient();
        }
        else {
             patient = examination.getExaminationCounseling().getPatient();
        }

        Set<Medication> allergy = patient.getMedication();

        List<Medication> medications = medicationRepository.findAll();

        List<String> imenaSvihLekova = new ArrayList<>();
        for(Medication j : medications){
            imenaSvihLekova.add(j.getName());
        }

        List<String> imenaAlergena = new ArrayList<>();
        for(Medication j : allergy){
            imenaAlergena.add(j.getName());
        }

        for(int i = 0; i < imenaSvihLekova.size(); i++){
            String ime = imenaSvihLekova.get(i);
            for(int j = 0; j< imenaAlergena.size(); j++){
                String ime2 = imenaAlergena.get(j);
                if(ime.equals(ime2)){
                    imenaSvihLekova.remove(i);
                }
            }
        }


        /*for(Medication i : allergy){
            for(Medication j : medications){
                if(i.getId() == j.getId()){
                    medications.remove(j);
                }
            }
        }*/

        return imenaSvihLekova;


    }


    public Boolean isMedicationAvailable(String medicationName, Integer examinationId){

        Medication medication = medicationRepository.findByName(medicationName);
        Examination examination = examinationRepository.findOneById(examinationId);

        //Integer pharmacyId = examination.getExaminationAppointment().getPharmacy_appointment().getId();

        //Set<Medication> medications = pharmacy.getMedication();
        //Set<Pharmacy> pharmaciesSet = medication.getPharmacies();
        Set<Medication> medications = new HashSet<>();
        Pharmacy pharmacy;
        if(examination.getExaminationAppointment() != null) {
             pharmacy = examination.getExaminationAppointment().getPharmacy_appointment();
             for (PharmacyMedication pm : pharmacy.getPharmacy_medications()) {
                 if(pm.getQuantity()>0) {
                     medications.add(pm.getMedication());
                 }
             }
        }
        else {
             pharmacy = examination.getExaminationCounseling().getPharmacist().getPharmacy();
             for (PharmacyMedication pm : pharmacy.getPharmacy_medications()) {
                 if(pm.getQuantity()>0) {
                     medications.add(pm.getMedication());
                 }
             }
        }
        List<Integer> lekoviId = new ArrayList<>();
        for(Medication j : medications){
            lekoviId.add(j.getId());
        }

        Integer lekId = medication.getId();

        for(int i = 0; i<lekoviId.size(); i++){
            if(lekoviId.get(i).equals(lekId)){
                return true;
            }
        }

        return false;

        /*
        for(Pharmacy i: pharmaciesSet){
            Integer getId = i.getId();
            if(getId.equals(pharmacyId)){
                return true;
            }
        }*/

        /*List<Integer> apotekeId = new ArrayList<>();
        for(Pharmacy j : pharmaciesSet){
            apotekeId.add(j.getId());
        }


        for(int i = 0; i<apotekeId.size(); i++){
            Integer p = apotekeId.get(i);
            if(p.equals(pharmacyId)){
                return true;
            }
        }*/

        /*List<Integer> lekoviId = new ArrayList<>();
        for(Medication j : medications){
            lekoviId.add(j.getId());
        }

        Integer lekId = medication.getId();

        for(int i = 0; i<lekoviId.size(); i++){
            if(lekoviId.get(i).equals(lekId)){
                return true;
            }
        }*/



        /*if(medications.contains(medication)){
            return true;
        }

        /*for(Medication i: pharmacy.getMedication()){
            if (i.getId() == medication.getId()){
                return true;
            }
        }*/


    }


    public List<String> getAlternativeMedications(Integer examinationId,String medicationName){

        Medication medication = medicationRepository.findByName(medicationName);
        List<String> notAlergic = getMedicationsForPrescription(examinationId);

        Set<Medication> alternative = medication.getMedication();

        List<String> imenaAlternativa = new ArrayList<>();
        for(Medication i : alternative){
            if (isMedicationAvailable(i.getName(), examinationId)) {
                imenaAlternativa.add(i.getName());
            }
        }

        List<String> alternativeNotAlergic = new ArrayList<>();

        for(int i = 0; i < imenaAlternativa.size(); i++){
            String ime = imenaAlternativa.get(i);
            for(int j = 0; j< notAlergic.size(); j++){
                String ime2 = notAlergic.get(j);
                if(ime.equals(ime2)){
                    alternativeNotAlergic.add(ime);
                }
            }
        }

        return alternativeNotAlergic;
    }


    public Boolean savePrescription(Integer examinationId, String medicationName, Integer duration) {

        Examination examination = examinationRepository.findOneById(examinationId);
        Medication medication = medicationRepository.findByName(medicationName);

        Pharmacy pharmacy;
        if(examination.getExaminationAppointment() != null) {
            pharmacy = examination.getExaminationAppointment().getPharmacy_appointment();
        }else {
            pharmacy = examination.getExaminationCounseling().getPharmacist().getPharmacy();
        }

        LocalDate today = LocalDate.now();
        Set<PharmacyMedication> pharmacyMedications = pharmacyMedicationRepository.findAllByPharmacyAndMedication(pharmacy, medication);
        for(PharmacyMedication i: pharmacyMedications){
            if(today.isAfter(i.getBeginPriceValidity()) && today.isBefore(i.getEndPriceValidity())){
                i.setQuantity(i.getQuantity()-1);
                pharmacyMedicationRepository.save(i);
                break;
            }
        }

        examination.setPrescriptedMedication(medication);
        examination.setTherapyDuration(duration);
        examination = examinationRepository.save(examination);



        return true;
    }

    public List<Appointment> getExaminatedPatients(Dermatologist dermatologist){

        List<Examination> examinations = examinationRepository.findAll();
        Integer dermatologistId = dermatologist.getId();

        List<Examination> examinationsDermatologist = new ArrayList<>();
        for(Examination i: examinations){
            if(i.getExaminationAppointment()  != null) {
                if (i.getExaminationAppointment().getDermatologist().getId() == dermatologistId) {
                    examinationsDermatologist.add(i);
                }
            }
        }

        List<Appointment> termini = new ArrayList<>();
        for (Examination i: examinationsDermatologist){
            termini.add(i.getExaminationAppointment());
        }

        return termini;
    }

    public List<ExaminPatientDto> getExaminatedPatientsSortName(Dermatologist dermatologist){

        List<Examination> examinations = examinationRepository.findAll();
        Integer dermatologistId = dermatologist.getId();

        List<Examination> examinationsDermatologist = new ArrayList<>();
        for(Examination i: examinations){
            if(i.getExaminationAppointment()  != null) {
                if (i.getExaminationAppointment().getDermatologist().getId() == dermatologistId) {
                    examinationsDermatologist.add(i);
                }
            }
        }

        List<Appointment> termini = new ArrayList<>();
        for (Examination i: examinationsDermatologist){
            termini.add(i.getExaminationAppointment());
        }

        List<ExaminPatientDto> examinPatientDtos = new ArrayList<>();
        for(Appointment i: termini){
            ExaminPatientDto examinPatientDto = new ExaminPatientDto(i.getPatient().getFirstName(), i.getPatient().getLastName(), i.getBeginofappointment().toLocalDate());
            examinPatientDtos.add(examinPatientDto);
        }

        Collections.sort(examinPatientDtos, Comparator.comparing(ExaminPatientDto::getName));

        return examinPatientDtos;
    }


    public List<ExaminPatientDto> getExaminatedPatientsSortLastName(Dermatologist dermatologist){

        List<Examination> examinations = examinationRepository.findAll();
        Integer dermatologistId = dermatologist.getId();

        List<Examination> examinationsDermatologist = new ArrayList<>();
        for(Examination i: examinations){
            if(i.getExaminationAppointment()  != null) {
                if (i.getExaminationAppointment().getDermatologist().getId() == dermatologistId) {
                    examinationsDermatologist.add(i);
                }
            }
        }

        List<Appointment> termini = new ArrayList<>();
        for (Examination i: examinationsDermatologist){
            termini.add(i.getExaminationAppointment());
        }

        List<ExaminPatientDto> examinPatientDtos = new ArrayList<>();
        for(Appointment i: termini){
            ExaminPatientDto examinPatientDto = new ExaminPatientDto(i.getPatient().getFirstName(), i.getPatient().getLastName(), i.getBeginofappointment().toLocalDate());
            examinPatientDtos.add(examinPatientDto);
        }

        Collections.sort(examinPatientDtos, Comparator.comparing(ExaminPatientDto::getLastName));

        return examinPatientDtos;
    }

    public List<ExaminPatientDto> getExaminatedPatientsSortDate(Dermatologist dermatologist){

        List<Examination> examinations = examinationRepository.findAll();
        Integer dermatologistId = dermatologist.getId();

        List<Examination> examinationsDermatologist = new ArrayList<>();
        for(Examination i: examinations){
            if(i.getExaminationAppointment()  != null) {
                if (i.getExaminationAppointment().getDermatologist().getId() == dermatologistId) {
                    examinationsDermatologist.add(i);
                }
            }
        }

        List<Appointment> termini = new ArrayList<>();
        for (Examination i: examinationsDermatologist){
            termini.add(i.getExaminationAppointment());
        }

        List<ExaminPatientDto> examinPatientDtos = new ArrayList<>();
        for(Appointment i: termini){
            ExaminPatientDto examinPatientDto = new ExaminPatientDto(i.getPatient().getFirstName(), i.getPatient().getLastName(), i.getBeginofappointment().toLocalDate());
            examinPatientDtos.add(examinPatientDto);
        }

        Collections.sort(examinPatientDtos, Comparator.comparing(ExaminPatientDto::getDate));

        return examinPatientDtos;
    }

    //*************

    public Counseling findCounseling(Pharmacist pharmacist){

        LocalTime now = LocalTime.now();
        LocalDate today = LocalDate.now();

        List<Counseling> counselings = counselingRepository.findAll();

        for (Counseling i : counselings){
            if(today.isEqual(i.getBeginofappointment().toLocalDate())){
                if(now.isAfter(i.getBeginofappointment().toLocalTime()) && now.isBefore(i.getEndofappointment().toLocalTime())){
                    if(i.getPharmacist().getId() == pharmacist.getId()) {
                        return i;
                    }
                }
            }
        }

        return null;
    }

    public Integer SaveCounseling(Counseling counseling, Pharmacist user) {
        Examination examination = new Examination();
        examination.setExaminationCounseling(counseling);
        examination = examinationRepository.save(examination);
        return examination.getId();
    }


    public List<Counseling> getExaminatedPatientsPharmacist(Pharmacist pharmacist){

        List<Examination> examinations = examinationRepository.findAll();
        Integer pharmacistId = pharmacist.getId();

        List<Examination> examinationsPharmacist = new ArrayList<>();
        for(Examination i: examinations){
            if(i.getExaminationCounseling() != null) {
                if (i.getExaminationCounseling().getPharmacist().getId() == pharmacistId) {
                    examinationsPharmacist.add(i);
                }
            }
        }

        List<Counseling> termini = new ArrayList<>();
        for (Examination i: examinationsPharmacist){
            termini.add(i.getExaminationCounseling());
        }

        return termini;
    }

    public List<ExaminPatientDto> examinatedPatientsPharmacistSortName(Pharmacist pharmacist) {

        List<Examination> examinations = examinationRepository.findAll();
        Integer pharmacistId = pharmacist.getId();

        List<Examination> examinationsPharmacist = new ArrayList<>();
        for(Examination i: examinations){
            if(i.getExaminationCounseling() != null) {
                if (i.getExaminationCounseling().getPharmacist().getId() == pharmacistId) {
                    examinationsPharmacist.add(i);
                }
            }
        }

        List<Counseling> termini = new ArrayList<>();
        for (Examination i: examinationsPharmacist){
            termini.add(i.getExaminationCounseling());
        }

        List<ExaminPatientDto> examinPatientDtos = new ArrayList<>();
        for(Counseling i: termini){
            ExaminPatientDto examinPatientDto = new ExaminPatientDto(i.getPatient().getFirstName(), i.getPatient().getLastName(), i.getBeginofappointment().toLocalDate());
            examinPatientDtos.add(examinPatientDto);
        }

        Collections.sort(examinPatientDtos, Comparator.comparing(ExaminPatientDto::getName));

        return examinPatientDtos;
    }


    public List<ExaminPatientDto> getExaminatedPatientsPharmacistSortLastName(Pharmacist pharmacist) {

        List<Examination> examinations = examinationRepository.findAll();
        Integer pharmacistId = pharmacist.getId();

        List<Examination> examinationsPharmacist = new ArrayList<>();
        for(Examination i: examinations){
            if(i.getExaminationCounseling() != null) {
                if (i.getExaminationCounseling().getPharmacist().getId() == pharmacistId) {
                    examinationsPharmacist.add(i);
                }
            }
        }

        List<Counseling> termini = new ArrayList<>();
        for (Examination i: examinationsPharmacist){
            termini.add(i.getExaminationCounseling());
        }

        List<ExaminPatientDto> examinPatientDtos = new ArrayList<>();
        for(Counseling i: termini){
            ExaminPatientDto examinPatientDto = new ExaminPatientDto(i.getPatient().getFirstName(), i.getPatient().getLastName(), i.getBeginofappointment().toLocalDate());
            examinPatientDtos.add(examinPatientDto);
        }

        Collections.sort(examinPatientDtos, Comparator.comparing(ExaminPatientDto::getLastName));

        return examinPatientDtos;
    }

    public List<ExaminPatientDto> getExaminatedPatientsPharmacistSortDate(Pharmacist pharmacist) {

        List<Examination> examinations = examinationRepository.findAll();
        Integer pharmacistId = pharmacist.getId();

        List<Examination> examinationsPharmacist = new ArrayList<>();
        for(Examination i: examinations){
            if(i.getExaminationCounseling() != null) {
                if (i.getExaminationCounseling().getPharmacist().getId() == pharmacistId) {
                    examinationsPharmacist.add(i);
                }
            }
        }

        List<Counseling> termini = new ArrayList<>();
        for (Examination i: examinationsPharmacist){
            termini.add(i.getExaminationCounseling());
        }

        List<ExaminPatientDto> examinPatientDtos = new ArrayList<>();
        for(Counseling i: termini){
            ExaminPatientDto examinPatientDto = new ExaminPatientDto(i.getPatient().getFirstName(), i.getPatient().getLastName(), i.getBeginofappointment().toLocalDate());
            examinPatientDtos.add(examinPatientDto);
        }

        Collections.sort(examinPatientDtos, Comparator.comparing(ExaminPatientDto::getDate));

        return examinPatientDtos;
    }


    public AdminPharmacy findAdminPharmacy(Integer examinationId){
        Examination examination = examinationRepository.findOneById(examinationId);
        Pharmacy pharmacy = examination.getExaminationAppointment().getPharmacy_appointment();
        Set<AdminPharmacy> adminPharmacies = adminPharmacyRepository.findAllByPharmacy(pharmacy);
        AdminPharmacy admin = adminPharmacies.iterator().next();
        return admin;

    }

    public Set<AdminPharmacy> findAdminPharmacyPharmacist(Integer examinationId, Pharmacist user){
        Examination examination = examinationRepository.findOneById(examinationId);
        Pharmacy pharmacy = user.getPharmacy();
        Set<AdminPharmacy> adminPharmacies = adminPharmacyRepository.findAllByPharmacy(pharmacy);

        //AdminPharmacy admin = adminPharmacies.iterator().next();

        return adminPharmacies;

    }

    }
