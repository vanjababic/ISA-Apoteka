package com.example.ISAISA.service;

import com.example.ISAISA.DTO.PharmacistDTO;
import com.example.ISAISA.DTO.UserChangeDTO;
import com.example.ISAISA.model.*;
import com.example.ISAISA.repository.AppointmentRepository;
import com.example.ISAISA.repository.DermatologistRepository;
import com.example.ISAISA.repository.Dermatologist_PharmacyyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DermatologistService {

    private DermatologistRepository dermatologistRepository;
    private Dermatologist_PharmacyyRepository dermatologist_pharmacyRepository;
    private AppointmentRepository appointmentRepository;

    @Autowired
    public void setDermatologistRepository(DermatologistRepository dermatologistRepository) {
        this.dermatologistRepository = dermatologistRepository;
    }

    @Autowired
    public void setDermatologist_pharmacyRepository(Dermatologist_PharmacyyRepository dermatologist_pharmacyRepository) {
        this.dermatologist_pharmacyRepository = dermatologist_pharmacyRepository;
    }

    @Autowired
    public void setAppointmentRepository(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public Dermatologist changeDermatologistInfo(UserChangeDTO userDTO) {

        Dermatologist user = (Dermatologist) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setAddress(userDTO.getAddress());
        user.setPhone(userDTO.getPhone());
        user.setCity(userDTO.getCity());
        user.setCountry(userDTO.getCountry());

        dermatologistRepository.save(user);

        return user;
    }

    public void changeFlag(Dermatologist dermatologist){
        dermatologist.setFlag(1);
        dermatologistRepository.save(dermatologist);
    }

    //Prikaz po apoteci
    public Set<Dermatologist> getByPharmacy(Pharmacy pharmacy) {

        Set<Dermatologist_Pharmacyy> dermatologist_pharmacies = dermatologist_pharmacyRepository.findAllByPharmacy(pharmacy);
        Set<Dermatologist> dermatologists = new HashSet<>();

        for (Dermatologist_Pharmacyy dp : dermatologist_pharmacies) {
            Dermatologist d = dp.getDermatologist();
            dermatologists.add(d);
        }

        return dermatologists;
    }

    public Dermatologist findById(Integer id) { return dermatologistRepository.findOneById(id); }

    //Pretraga
    public Set<Dermatologist> getDermatologistsByPharmacyAndFirstNameAndLastName(Pharmacy pharmacy, String firstName, String lastName) {

        Set<Dermatologist_Pharmacyy> dermatologist_pharmacyys = dermatologist_pharmacyRepository.findAllByPharmacy(pharmacy);

        Set<Dermatologist> dermatologists = new HashSet<>();

        for(Dermatologist_Pharmacyy dp : dermatologist_pharmacyys) {
            if (dp.getDermatologist().getFirstName().toLowerCase().equals(firstName.toLowerCase()) && dp.getDermatologist().getLastName().toLowerCase().equals(lastName.toLowerCase()))
                dermatologists.add(dp.getDermatologist());
        }

        return dermatologists;
    }

    //Dodavanje
    public Dermatologist findByEmail(String email) { return this.dermatologistRepository.findOneByEmail(email); }

    public Dermatologist_Pharmacyy addToPharmacy(Dermatologist dermatologist, LocalTime beginOfWork, LocalTime endOfWork, Pharmacy pharmacy) throws Exception {

        Set<Dermatologist_Pharmacyy> dermatologist_pharmacyys = dermatologist_pharmacyRepository.findAllByDermatologist(dermatologist);

        for (Dermatologist_Pharmacyy dp : dermatologist_pharmacyys) {
            if ((beginOfWork.isAfter(dp.getBeginofwork()) && beginOfWork.isBefore(dp.getEndofwork()))
                    || (endOfWork.isBefore(dp.getEndofwork()) && endOfWork.isAfter(dp.getBeginofwork()))
                    || (beginOfWork.isBefore(dp.getBeginofwork()) && endOfWork.isAfter(dp.getEndofwork()))) {
                throw new Exception("Dermatologu se poklapa radno vreme sa apotekom: " + dp.getPharmacy().getName());
            }
        }

        Dermatologist_Pharmacyy dermatologist_pharmacyy = new Dermatologist_Pharmacyy(beginOfWork, endOfWork, pharmacy, dermatologist);

        dermatologist_pharmacyy = this.dermatologist_pharmacyRepository.save(dermatologist_pharmacyy);

        return dermatologist_pharmacyy;
    }

    //Filtriranje
    public Set<Dermatologist> filterDermatologists(Float ratingOver, Float ratingUnder, Pharmacy pharmacy) {
        if (pharmacy == null) {
            return getDermatologistsByRatingBetween(ratingOver, ratingUnder);
        } else {
            return getDermatologistsByRatingBetweenAndPharmacyName(ratingOver, ratingUnder, pharmacy);
        }
    }

    public Set<Dermatologist> getDermatologistsByRatingBetweenAndPharmacyName(Float ratingOver, Float ratingUnder, Pharmacy pharmacy) {
        Set<Dermatologist> dermatologists = dermatologistRepository.findAllByRatingBetween(ratingOver, ratingUnder);

        Set<Dermatologist> chosenDermatologists = new HashSet<>();

        for (Dermatologist dermatologist : dermatologists) {
            Set<Dermatologist_Pharmacyy> dermatologist_pharmacyys = dermatologist_pharmacyRepository.findAllByDermatologist(dermatologist);
            for (Dermatologist_Pharmacyy dp : dermatologist_pharmacyys) {
                if (dp.getPharmacy().getId().equals(pharmacy.getId())) {
                    chosenDermatologists.add(dp.getDermatologist());
                }
            }
        }
        return chosenDermatologists;
    }

    public Set<Dermatologist> getDermatologistsByRatingBetween(Float ratingOver, Float ratingUnder) {

        Set<Dermatologist> dermatologists = dermatologistRepository.findAllByRatingBetween(ratingOver, ratingUnder);

        return dermatologists;
    }

    //Brisanje
    public void removeDermatologistFromPharmacy(Integer id, Pharmacy pharmacy) throws Exception {

        Dermatologist dermatologist = dermatologistRepository.findOneById(id);

        Dermatologist_Pharmacyy dermatologist_pharmacyy = dermatologist_pharmacyRepository.findByDermatologistAndPharmacy(dermatologist, pharmacy);

        Set<Appointment> appointments = appointmentRepository.findAllByDermatologist(dermatologist);
        Set<Appointment> reservedAppointments = new HashSet<>();

        for (Appointment appointment : appointments) {
            if (appointment.getPatient() != null && appointment.getBeginofappointment().isAfter(LocalDateTime.now())) {
                reservedAppointments.add(appointment);
            }
        }

        if (reservedAppointments.isEmpty()) {
            this.dermatologist_pharmacyRepository.delete(dermatologist_pharmacyy);
        } else {
            throw new Exception("Dermatolog ima zakazane termine, nije moguce ukloniti ga iz apoteke!");
        }
    }

    public Set<Dermatologist> getAll() { return this.dermatologistRepository.findAllBy(); }

    public Set<Dermatologist> getDermatologistsByFirstNameAndLastName(String firstName, String lastName) {
        return this.dermatologistRepository.findAllByFirstNameIgnoreCaseAndLastNameIgnoreCase(firstName, lastName);
    }
}
