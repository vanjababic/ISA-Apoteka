package com.example.ISAISA.service;

import com.example.ISAISA.DTO.BooleanDto;
import com.example.ISAISA.DTO.CalendarDTO;
import com.example.ISAISA.model.*;
import com.example.ISAISA.repository.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class AppointmentService {

    private AppointmentRepository appointmentRepository;
    private Dermatologist_PharmacyyRepository dermafarmaRepository;
    private PatientRepository patientRepository;
    private ExaminationRepository examinationRepository;
    private CounselingRepository counselingRepository;
    private VacationRepository vacationRepository;

    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    public Appointment findById(Integer id) {
        return appointmentRepository.findOneById(id);
    }

    public Appointment save(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    public Set<Appointment> findpatientorderbyprice(Patient patient){return appointmentRepository.findByPatientOrderByPrice(patient);}

    @Autowired
    public void setCounselingRepository(CounselingRepository counselingRepository) { this.counselingRepository = counselingRepository; }

    @Autowired
    public void setExaminationRepository(ExaminationRepository examinationRepository) { this.examinationRepository = examinationRepository; }

    @Autowired
    public void setAppointmentRepository(AppointmentRepository appointmentRepository) { this.appointmentRepository = appointmentRepository; }

    @Autowired
    public void setDermafarmaRepository(Dermatologist_PharmacyyRepository dermafarmaRepository) { this.dermafarmaRepository = dermafarmaRepository; }

    @Autowired
    public void setPatientRepository(PatientRepository patientRepository) { this.patientRepository = patientRepository; }

    @Autowired
    public void setVacationRepository(VacationRepository vacationRepository) { this.vacationRepository = vacationRepository; }

    public Appointment ifPatientHasAppointment(Integer idPatient, Dermatologist dermatologist) {

        LocalTime now = LocalTime.now();
        LocalDate today = LocalDate.now();

        List<Appointment> appointments = appointmentRepository.findAll();


        for (Appointment i : appointments) {
            if (i.getPatient() != null) {
                if (i.getPatient().getId().equals(idPatient)) {
                    if (today.isEqual(i.getBeginofappointment().toLocalDate())) {
                        if (now.isAfter(i.getBeginofappointment().toLocalTime()) && now.isBefore(i.getEndofappointment().toLocalTime())) {
                            if (i.getDermatologist().getId() == dermatologist.getId()) {
                                return i;
                            }
                        }
                    }
                }
            }
        }

        return null;
    }

    public Boolean checkPharmacy(Appointment appointment, Dermatologist dermatologist) {

        LocalTime now = LocalTime.now();

        List<Dermatologist_Pharmacyy> dermaFarma = dermafarmaRepository.findAll();
        for (Dermatologist_Pharmacyy i : dermaFarma) {
            if (i.getDermatologist().getId() == dermatologist.getId()) {
                if (i.getPharmacy().getId() == appointment.getPharmacy_appointment().getId()) {
                    if (now.isAfter(i.getBeginofwork()) && now.isBefore(i.getEndofwork())) {
                        return true;
                    }
                }
            }
        }

        return false;
    }


    public Integer penalPatient(Integer appointmentId) {

        Appointment appointment = appointmentRepository.getOne(appointmentId);
        Patient patient = appointment.getPatient();
        Integer newPenalty = patient.getPenalty()+1;
        patient.setPenalty(newPenalty);
        patient = patientRepository.save(patient);
        return patient.getId();

    }

    public Appointment saveAvailable(Appointment appointment) throws Exception {
        Dermatologist dermatologist = appointment.getDermatologist();
        Dermatologist_Pharmacyy dermatologist_pharmacy = dermafarmaRepository.findByDermatologistAndPharmacy(dermatologist, appointment.getPharmacy_appointment());
        LocalTime dermatologistBeginOfWork = dermatologist_pharmacy.getBeginofwork();
        LocalTime dermatologistEndOfWork = dermatologist_pharmacy.getEndofwork();

        //Ako pokusa da zakaze za proslo vreme
        if (appointment.getBeginofappointment().isBefore(LocalDateTime.now())) {
            throw new Exception("Nije moguce zakazati ovaj termin!");
        }

        //Ako dermatolog vec ima zakazan termin tada (unutar intervala ili da ga obuhvata)
        Set<Appointment> existingDermAppointments = appointmentRepository.findAllByDermatologist(appointment.getDermatologist());
        for (Appointment a : existingDermAppointments) {
            if ((appointment.getBeginofappointment().isAfter(a.getBeginofappointment().minusMinutes(1)) && appointment.getBeginofappointment().isBefore(a.getEndofappointment().plusMinutes(1))) ||
                    (appointment.getEndofappointment().isAfter(a.getBeginofappointment().minusMinutes(1)) && appointment.getEndofappointment().isBefore(a.getEndofappointment().plusMinutes(1)))
                    || (appointment.getBeginofappointment().isBefore(a.getBeginofappointment().minusMinutes(1)) && appointment.getEndofappointment().isAfter(a.getEndofappointment().plusMinutes(1)))) {
                throw new Exception("Postoji zakazan termin u ovo vreme!");
            }
        }


        //Ako dermatolog ne radi tada
        if (appointment.getBeginofappointment().toLocalTime().isBefore(dermatologistBeginOfWork)
                || appointment.getEndofappointment().toLocalTime().isAfter(dermatologistEndOfWork)) {
            throw new Exception("Termin nije tokom radnog vremena dermatologa!");
        }

        //Ako je dermatolog na godisnjem odmoru
        Set<Vacation> vacations = vacationRepository.findAllByDermatologistAndApprovedTrue(appointment.getDermatologist());
        for (Vacation vacation : vacations) {
            if ((appointment.getBeginofappointment().toLocalDate().isAfter(vacation.getDateBeginVacation()) && appointment.getBeginofappointment().toLocalDate().isBefore(vacation.getDateEndVacation()))
                || (appointment.getEndofappointment().toLocalDate().isAfter(vacation.getDateBeginVacation()) && appointment.getEndofappointment().toLocalDate().isBefore(vacation.getDateEndVacation()))
                || (appointment.getBeginofappointment().toLocalDate().isBefore(vacation.getDateBeginVacation()) && appointment.getEndofappointment().toLocalDate().isAfter(vacation.getDateEndVacation()))) {
                throw new Exception("Termin je u toku godisnjeg odmora dermatologa!");
            }
        }

        appointmentRepository.save(appointment);

        return appointment;
    }

    public Set<Appointment> getAvailableAppointmentsByPharmacy(Pharmacy pharmacy) {
        Set<Appointment> availableAppointments = appointmentRepository.findAllByPatientNull();
        Set<Appointment> appointmentsByPharmacy = new HashSet<>();

        for(Appointment a: availableAppointments) {
            if(a.getPharmacy_appointment() == pharmacy) {
                appointmentsByPharmacy.add(a);
            }
        }

        return appointmentsByPharmacy;
    }

    public List<Appointment> findFreeAppointmentsForDermatologist(Dermatologist dermatologist, Integer examinationId) {

        Examination examination = examinationRepository.findOneById(examinationId);
        List<Appointment> allAppointments = appointmentRepository.findAll();

        List<Appointment> freeAppointments = new ArrayList<>();
        for (Appointment i : allAppointments) {
            if (i.getPatient() == null) {
                freeAppointments.add(i);
            }
        }

        List<Appointment> freeAppointmentsDermatologist = new ArrayList<>();
        for (Appointment i : freeAppointments) {
            if (i.getDermatologist().getId() == dermatologist.getId()) {
                freeAppointmentsDermatologist.add(i);
            }
        }
        //return freeAppointmentsDermatologist;

        for (Appointment i : freeAppointmentsDermatologist) {
            if (i.getPharmacy_appointment().getId() != examination.getExaminationAppointment().getPharmacy_appointment().getId()) {
                freeAppointmentsDermatologist.remove(i);
            }
        }

        Patient patient = examination.getExaminationAppointment().getPatient();
        List<Appointment> okAppointments = new ArrayList<>();
        for (Appointment i : freeAppointmentsDermatologist) {
            if (isPatientFree(patient, i) && isDermatologistFree(dermatologist, i)) {
                okAppointments.add(i);
            }
        }

        return okAppointments;

    }

    public Boolean isPatientFree(Patient patient, Appointment appointment) {

        LocalDateTime startOfAppointment = appointment.getBeginofappointment();
        LocalDateTime endOfAppointment = appointment.getEndofappointment();

        List<Appointment> appointments = appointmentRepository.findByPatient(patient);

        /*for (Appointment i:appointments){
            if(i.getPatient()==null){
                appointments.remove(i);
            }
        }*/

        for (Appointment i : appointments) {
            if ((startOfAppointment.isAfter(i.getBeginofappointment()) && startOfAppointment.isBefore(i.getEndofappointment())) || (endOfAppointment.isAfter(i.getBeginofappointment()) && endOfAppointment.isBefore(i.getEndofappointment()))) {
                return false;

            }
        }

        return true;
    }

    public Boolean isDermatologistFree(Dermatologist dermatologist, Appointment appointment) {
        LocalDateTime startOfAppointment = appointment.getBeginofappointment();
        LocalDateTime endOfAppointment = appointment.getEndofappointment();

        Set<Appointment> appointments = appointmentRepository.findAllByDermatologist(dermatologist);
        List<Appointment> appointments1 = new ArrayList<>();

        for (Appointment i:appointments){
            if(i.getPatient()!=null){
                appointments1.add(i);
            }
        }

        for (Appointment i : appointments1) {
            if ((startOfAppointment.isAfter(i.getBeginofappointment()) && startOfAppointment.isBefore(i.getEndofappointment())) || (endOfAppointment.isAfter(i.getBeginofappointment()) && endOfAppointment.isBefore(i.getEndofappointment()))) {
                return false;

            }
        }
        return true;

    }

    public Appointment saveAppointmentDermatologist(Integer examinationId, Integer appointmentId){

        Examination examination = examinationRepository.findOneById(examinationId);
        Appointment appointment = appointmentRepository.findOneById(appointmentId);

        examination.setNewAppointment(appointment);
        examination = examinationRepository.save(examination);

        appointment.setPatient(examination.getExaminationAppointment().getPatient());
        appointment = appointmentRepository.save(appointment);

        return appointment;

    }

    public Boolean createAppointmentDermatologist(Dermatologist dermatologist, Integer examinationId, LocalDateTime startOfAppointment, LocalDateTime endOfAppointment, Integer price){

        Examination examination = examinationRepository.findOneById(examinationId);
        Patient patient = examination.getExaminationAppointment().getPatient();
        Pharmacy pharmacy = examination.getExaminationAppointment().getPharmacy_appointment();

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDermatologist(dermatologist);
        appointment.setPharmacy_appointment(pharmacy);
        appointment.setBeginofappointment(startOfAppointment);
        appointment.setEndofappointment(endOfAppointment);
        appointment.setPrice(price);


        Boolean ok = checkIfAppointmentIsAvailable(pharmacy, dermatologist, patient, startOfAppointment, endOfAppointment);

        if(ok){
            appointment = appointmentRepository.save(appointment);
            examination.setNewAppointment(appointment);
            examination = examinationRepository.save(examination);
            return true;
        }
        return false;

    }

    public Patient findPatient(Integer examinationId){
        Examination examination = examinationRepository.findOneById(examinationId);
        Patient patient = examination.getExaminationAppointment().getPatient();
        return patient;
    }

    public Boolean checkIfAppointmentIsAvailable(Pharmacy pharmacy, Dermatologist dermatologist, Patient patient, LocalDateTime startOfAppointment, LocalDateTime endOfAppointment){

        List<Appointment> patientAppointments = appointmentRepository.findByPatient(patient);
        List<Counseling> patientCounselings = counselingRepository.findAllByPatient(patient);

        LocalDateTime dateTime = LocalDateTime.now();

        if(dateTime.isAfter(startOfAppointment)){
            return false;
        }

        Boolean patientFree = true;

        for (Appointment i : patientAppointments) {
            if(i.getBeginofappointment().toLocalDate().isEqual(startOfAppointment.toLocalDate())) {
                if ((startOfAppointment.isAfter(i.getBeginofappointment()) && startOfAppointment.isBefore(i.getEndofappointment())) || (endOfAppointment.isAfter(i.getBeginofappointment()) && endOfAppointment.isBefore(i.getEndofappointment()))) {
                    patientFree = false;
                    //return false;
                    break;
                }
            }
        }

        if(patientFree){
            for (Counseling i : patientCounselings) {
                if(i.getBeginofappointment().toLocalDate().isEqual(startOfAppointment.toLocalDate())) {
                    if ((startOfAppointment.isAfter(i.getBeginofappointment()) && startOfAppointment.isBefore(i.getEndofappointment())) || (endOfAppointment.isAfter(i.getBeginofappointment()) && endOfAppointment.isBefore(i.getEndofappointment()))) {
                        patientFree = false;
                        //return false;
                        break;
                    }
                }
            }
        }

        Set<Appointment> dermatologistAppointments = appointmentRepository.findAllByDermatologist(dermatologist);
        Boolean dermatologistFree = true;
        List<Appointment> appointments1 = new ArrayList<>();

        for (Appointment i: dermatologistAppointments){
            if(i.getPatient()!=null){
                appointments1.add(i);
            }
        }

        for (Appointment i : appointments1) {
            if(i.getBeginofappointment().toLocalDate().isEqual(startOfAppointment.toLocalDate())) {
                if ((startOfAppointment.isAfter(i.getBeginofappointment()) && startOfAppointment.isBefore(i.getEndofappointment())) || (endOfAppointment.isAfter(i.getBeginofappointment()) && endOfAppointment.isBefore(i.getEndofappointment()))) {
                    dermatologistFree = false;
                    break;
                }
            }
        }

        Dermatologist_Pharmacyy dermafarma = dermafarmaRepository.findByDermatologistAndPharmacy(dermatologist, pharmacy);
        LocalTime dermatologistBeginOfWork = dermafarma.getBeginofwork();
        LocalTime dermatologistEndOfWork = dermafarma.getEndofwork();

        if(dermatologistFree) {
            if (startOfAppointment.toLocalTime().isBefore(dermatologistBeginOfWork) || startOfAppointment.toLocalTime().isAfter(dermatologistEndOfWork) || endOfAppointment.toLocalTime().isBefore(dermatologistBeginOfWork) || endOfAppointment.toLocalTime().isAfter(dermatologistEndOfWork)) {
                dermatologistFree = false;
            }
        }

        if(dermatologistFree && patientFree){
            return true;
        }
        return false;
    }

    public List<CalendarDTO> getAppointmentsWeek(Dermatologist user){
        Set<Appointment> appointments = appointmentRepository.findAllByDermatologist(user);

        //DateTime lastWeek = new DateTime().minusDays(7);

        LocalDate today = LocalDate.now();
        LocalDate nextWeek = today.plusDays(7);
        //.plusDays(7);

        List<Appointment> weekAppointments = new ArrayList<>();
        for(Appointment i: appointments){
            if(i.getBeginofappointment().toLocalDate().isAfter(today) && i.getBeginofappointment().toLocalDate().isBefore(nextWeek)){
                weekAppointments.add(i);
            }
        }

        List<CalendarDTO> calendarDTOS = new ArrayList<>();

        for(Appointment i:weekAppointments){
            if(i.getPatient()!=null){
                CalendarDTO calendarDTO = new CalendarDTO(i.getPatient().getFirstName(), i.getPatient().getLastName(), i.getBeginofappointment().toLocalDate(), i.getBeginofappointment().toLocalTime(), i.getEndofappointment().toLocalTime());
                calendarDTOS.add(calendarDTO);
            }
            else {
                CalendarDTO calendarDTO = new CalendarDTO("Termin nije zakazan", "Termin nije zakazan", i.getBeginofappointment().toLocalDate(), i.getBeginofappointment().toLocalTime(), i.getEndofappointment().toLocalTime());
                calendarDTOS.add(calendarDTO);
            }
        }

        return calendarDTOS;

    }

    public List<CalendarDTO> getAppointmentsMonth(Dermatologist user){
        Set<Appointment> appointments = appointmentRepository.findAllByDermatologist(user);

        //DateTime lastWeek = new DateTime().minusDays(7);

        LocalDate today = LocalDate.now();
        LocalDate nextWeek = today.plusDays(30);
        //.plusDays(7);

        List<Appointment> weekAppointments = new ArrayList<>();
        for(Appointment i: appointments){
            if(i.getBeginofappointment().toLocalDate().isAfter(today) && i.getBeginofappointment().toLocalDate().isBefore(nextWeek)){
                weekAppointments.add(i);
            }
        }

        List<CalendarDTO> calendarDTOS = new ArrayList<>();

        for(Appointment i:weekAppointments){
            if(i.getPatient()!=null){
                CalendarDTO calendarDTO = new CalendarDTO(i.getPatient().getFirstName(), i.getPatient().getLastName(), i.getBeginofappointment().toLocalDate(), i.getBeginofappointment().toLocalTime(), i.getEndofappointment().toLocalTime());
                calendarDTOS.add(calendarDTO);
            }
            else {
                CalendarDTO calendarDTO = new CalendarDTO("Termin nije zakazan", "Termin nije zakazan", i.getBeginofappointment().toLocalDate(), i.getBeginofappointment().toLocalTime(), i.getEndofappointment().toLocalTime());
                calendarDTOS.add(calendarDTO);
            }
        }

        return calendarDTOS;

    }

    public List<CalendarDTO> getAppointmentsYear(Dermatologist user){
        Set<Appointment> appointments = appointmentRepository.findAllByDermatologist(user);

        //DateTime lastWeek = new DateTime().minusDays(7);

        LocalDate today = LocalDate.now();
        LocalDate nextWeek = today.plusDays(365);
        //.plusDays(7);

        List<Appointment> weekAppointments = new ArrayList<>();
        for(Appointment i: appointments){
            if(i.getBeginofappointment().toLocalDate().isAfter(today) && i.getBeginofappointment().toLocalDate().isBefore(nextWeek)){
                weekAppointments.add(i);
            }
        }

        List<CalendarDTO> calendarDTOS = new ArrayList<>();

        for(Appointment i:weekAppointments){
            if(i.getPatient()!=null){
                CalendarDTO calendarDTO = new CalendarDTO(i.getPatient().getFirstName(), i.getPatient().getLastName(), i.getBeginofappointment().toLocalDate(), i.getBeginofappointment().toLocalTime(), i.getEndofappointment().toLocalTime());
                calendarDTOS.add(calendarDTO);
            }
            else {
                CalendarDTO calendarDTO = new CalendarDTO("Termin nije zakazan", "Termin nije zakazan", i.getBeginofappointment().toLocalDate(), i.getBeginofappointment().toLocalTime(), i.getEndofappointment().toLocalTime());
                calendarDTOS.add(calendarDTO);
            }
        }

        return calendarDTOS;

    }

    public Set<Appointment> getAppointmentByPharmacyAfterNow(Pharmacy pharmacy) {
        Set<Appointment> appointments = new HashSet<>();
        Set<Dermatologist> dermatologists = new HashSet<>();
        Set<Dermatologist_Pharmacyy> dermatologist_pharmacyys = dermafarmaRepository.findAllByPharmacy(pharmacy);
        for (Dermatologist_Pharmacyy dp : dermatologist_pharmacyys) {
            Set<Appointment> appointments1 = appointmentRepository.findAllByDermatologistAndBeginofappointmentAfter(dp.getDermatologist(), LocalDateTime.now());
            appointments.addAll(appointments1);
        }

        return appointments;
    }

    public Appointment changeAppointment(Integer price, Integer id) {
        Appointment appointment = appointmentRepository.findOneById(id);
        appointment.setPrice(price);
        appointment = appointmentRepository.save(appointment);
        return appointment;
    }

}

