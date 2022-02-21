package com.example.ISAISA.service;

import com.example.ISAISA.DTO.AppointmentDTO;
import com.example.ISAISA.DTO.CalendarDTO;
import com.example.ISAISA.model.*;
import com.example.ISAISA.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CounselingService {


    private CounselingRepository counselingRepository;
    private PatientRepository patientRepository;
    private ExaminationRepository examinationRepository;
    private AppointmentRepository appointmentRepository;
    private PharmacistRepository pharmacistRepository;

    @Autowired
    public void setAppointmentRepository(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Autowired
    public void setExaminationRepository(ExaminationRepository examinationRepository) {
        this.examinationRepository = examinationRepository;
    }

    @Autowired
    public void setCounselingRepository(CounselingRepository counselingRepository) {
        this.counselingRepository = counselingRepository;
    }

    @Autowired
    public void setPatientRepository(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }


    @Autowired
    public void setPharmacistRepository(PharmacistRepository pharmacistRepository) {
        this.pharmacistRepository = pharmacistRepository;
    }


    public Set<Counseling> findAllOrderByPrice(Patient patient){return  counselingRepository.findAllByPatientOrderByPrice(patient);}
    public Counseling findonebyid(Integer id){return counselingRepository.findOneById(id);}
    public void delete(Counseling counseling){counselingRepository.delete(counseling);}
    public List<Counseling> findAll() {return counselingRepository.findAll();}
    public Counseling save(Counseling appointment) {
        return counselingRepository.save(appointment);
    }

    public Counseling ifPatientHasCounseling(Integer idPatient, Pharmacist pharmacist) {

        LocalTime now = LocalTime.now();
        LocalDate today = LocalDate.now();

        List<Counseling> counselings = counselingRepository.findAll();


        for (Counseling i : counselings) {
            if (i.getPatient() != null) {
                if (i.getPatient().getId().equals(idPatient)) {
                    if (today.isEqual(i.getBeginofappointment().toLocalDate())) {
                        if (now.isAfter(i.getBeginofappointment().toLocalTime()) && now.isBefore(i.getEndofappointment().toLocalTime())) {
                            if (i.getPharmacist().getId() == pharmacist.getId()) {
                                return i;
                            }
                        }
                    }
                }
            }
        }

        return null;
    }

    public Boolean checkPharmacy(Pharmacist pharmacist) {

        LocalTime now = LocalTime.now();

        if (now.isAfter(pharmacist.getBeginofwork()) && now.isBefore(pharmacist.getEndofwork())) {
            return true;
        }
        return false;
    }

    public Integer penalPatient(Integer counselingId) {

        Counseling counseling = counselingRepository.getOne(counselingId);
        Patient patient = counseling.getPatient();
        Integer newPenalty = patient.getPenalty()+1;
        patient.setPenalty(newPenalty);
        patient = patientRepository.save(patient);
        return patient.getId();

    }

    public Boolean createCounselingPharmacist(Pharmacist pharmacist, Integer examinationId, LocalDateTime startOfCounseling, LocalDateTime endOfCounseling, Integer price){

        Examination examination = examinationRepository.findOneById(examinationId);
        Patient patient = examination.getExaminationCounseling().getPatient();
        Pharmacy pharmacy = pharmacist.getPharmacy();

        Counseling counseling = new Counseling();
        counseling.setPatient(patient);
        counseling.setPharmacist(pharmacist);
        counseling.setBeginofappointment(startOfCounseling);
        counseling.setEndofappointment(endOfCounseling);
        counseling.setPrice(price);


        Boolean ok = checkIfCounselingIsAvailable(pharmacist, patient, startOfCounseling, endOfCounseling);

        if(ok){
            counseling = counselingRepository.save(counseling);
            examination.setNewCounseling(counseling);
            examination = examinationRepository.save(examination);
            return true;
        }
        return false;

    }

    public Patient findPatient(Integer examinationId){
        Examination examination = examinationRepository.findOneById(examinationId);
        Patient patient = examination.getExaminationCounseling().getPatient();
        return patient;
    }

    public Boolean checkIfCounselingIsAvailable(Pharmacist pharmacist, Patient patient, LocalDateTime startOfCounseling, LocalDateTime endOfCounseling){

        List<Counseling> patientCounseling = counselingRepository.findAllByPatient(patient);
        Set<Appointment> patientAppointments = appointmentRepository.findAllByPatient(patient);

        LocalDateTime dateTime = LocalDateTime.now();

        if(dateTime.isAfter(startOfCounseling)){
            return false;
        }


        Boolean patientFree = true;

        for (Counseling i : patientCounseling) {
            if(i.getBeginofappointment().toLocalDate().isEqual(startOfCounseling.toLocalDate())) {
                if ((startOfCounseling.isAfter(i.getBeginofappointment()) && startOfCounseling.isBefore(i.getEndofappointment())) || (endOfCounseling.isAfter(i.getBeginofappointment()) && endOfCounseling.isBefore(i.getEndofappointment()))) {
                    patientFree = false;
                    break;
                }
            }
        }

        if(patientFree){
            for (Appointment i : patientAppointments) {
                if(i.getBeginofappointment().toLocalDate().isEqual(startOfCounseling.toLocalDate())) {
                    if ((startOfCounseling.isAfter(i.getBeginofappointment()) && startOfCounseling.isBefore(i.getEndofappointment())) || (endOfCounseling.isAfter(i.getBeginofappointment()) && endOfCounseling.isBefore(i.getEndofappointment()))) {
                        patientFree = false;
                        break;
                    }
                }
            }
        }


        List<Counseling> pharmacistCounseling = counselingRepository.findAllByPharmacist(pharmacist);
        Boolean pharmacistFree = true;


        for (Counseling i : pharmacistCounseling) {
            if(i.getBeginofappointment().toLocalDate().isEqual(startOfCounseling.toLocalDate())) {
                if ((startOfCounseling.isAfter(i.getBeginofappointment()) && startOfCounseling.isBefore(i.getEndofappointment())) || (endOfCounseling.isAfter(i.getBeginofappointment()) && endOfCounseling.isBefore(i.getEndofappointment()))) {
                    pharmacistFree = false;
                    break;
                }
            }
        }


        LocalTime pharmacistBeginOfWork = pharmacist.getBeginofwork();
        LocalTime pharmacistEndOfWork = pharmacist.getEndofwork();

        if(pharmacistFree) {
            if (startOfCounseling.toLocalTime().isBefore(pharmacistBeginOfWork) || startOfCounseling.toLocalTime().isAfter(pharmacistEndOfWork) || endOfCounseling.toLocalTime().isBefore(pharmacistBeginOfWork) || endOfCounseling.toLocalTime().isAfter(pharmacistEndOfWork)) {
                pharmacistFree = false;
            }
        }


        if(pharmacistFree && patientFree){
            return true;
        }else {
            return false;
        }
    }

    public List<CalendarDTO> getCounselingsWeek(Pharmacist user){
        List<Counseling> counselings = counselingRepository.findAllByPharmacist(user);

        LocalDate today = LocalDate.now();
        LocalDate nextWeek = today.plusDays(7);
        //.plusDays(7);

        List<Counseling> weekCounseling = new ArrayList<>();
        for(Counseling i: counselings){
            if(i.getBeginofappointment().toLocalDate().isAfter(today) && i.getBeginofappointment().toLocalDate().isBefore(nextWeek)){
                weekCounseling.add(i);
            }
        }


        List<CalendarDTO> calendarDTOS = new ArrayList<>();

        for(Counseling i:weekCounseling){
            CalendarDTO calendarDTO = new CalendarDTO(i.getPatient().getFirstName(), i.getPatient().getLastName(), i.getBeginofappointment().toLocalDate(), i.getBeginofappointment().toLocalTime(), i.getEndofappointment().toLocalTime());
            calendarDTOS.add(calendarDTO);
        }

        return calendarDTOS;


    }

    public List<CalendarDTO> getCounselingsMonth(Pharmacist user){
        List<Counseling> counselings = counselingRepository.findAllByPharmacist(user);

        LocalDate today = LocalDate.now();
        LocalDate nextWeek = today.plusDays(30);
        //.plusDays(7);

        List<Counseling> weekCounseling = new ArrayList<>();
        for(Counseling i: counselings){
            if(i.getBeginofappointment().toLocalDate().isAfter(today) && i.getBeginofappointment().toLocalDate().isBefore(nextWeek)){
                weekCounseling.add(i);
            }
        }


        List<CalendarDTO> calendarDTOS = new ArrayList<>();

        for(Counseling i:weekCounseling){
            CalendarDTO calendarDTO = new CalendarDTO(i.getPatient().getFirstName(), i.getPatient().getLastName(), i.getBeginofappointment().toLocalDate(), i.getBeginofappointment().toLocalTime(), i.getEndofappointment().toLocalTime());
            calendarDTOS.add(calendarDTO);
        }

        return calendarDTOS;


    }
    public List<CalendarDTO> getCounselingsYear(Pharmacist user){
        List<Counseling> counselings = counselingRepository.findAllByPharmacist(user);

        LocalDate today = LocalDate.now();
        LocalDate nextWeek = today.plusDays(365);
        //.plusDays(7);

        List<Counseling> weekCounseling = new ArrayList<>();
        for(Counseling i: counselings){
            if(i.getBeginofappointment().toLocalDate().isAfter(today) && i.getBeginofappointment().toLocalDate().isBefore(nextWeek)){
                weekCounseling.add(i);
            }
        }


        List<CalendarDTO> calendarDTOS = new ArrayList<>();

        for(Counseling i:weekCounseling){
            CalendarDTO calendarDTO = new CalendarDTO(i.getPatient().getFirstName(), i.getPatient().getLastName(), i.getBeginofappointment().toLocalDate(), i.getBeginofappointment().toLocalTime(), i.getEndofappointment().toLocalTime());
            calendarDTOS.add(calendarDTO);
        }

        return calendarDTOS;


    }

    public Set<Counseling> getCounselingByPharmacyAfterNow(Pharmacy pharmacy) {
        Set<Counseling> counselings = new HashSet<>();
        Set<Pharmacist> pharmacists = pharmacistRepository.findAllByPharmacy(pharmacy);

        for(Pharmacist pharmacist : pharmacists) {
            Set<Counseling> counselings1 = counselingRepository.findAllByPharmacistAndBeginofappointmentAfter(pharmacist, LocalDateTime.now());
            counselings.addAll(counselings1);
        }

        return counselings;
    }

    public Counseling findById(Integer id) {return this.counselingRepository.findOneById(id);}

    public Counseling changeCounselingPrice(Integer price, Integer id) {
        Counseling counseling = counselingRepository.findOneById(id);
        counseling.setPrice(price);
        counseling = counselingRepository.save(counseling);
        return counseling;
    }
}
