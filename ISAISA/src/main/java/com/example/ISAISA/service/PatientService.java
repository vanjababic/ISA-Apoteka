package com.example.ISAISA.service;

import com.example.ISAISA.DTO.PatientSearchDto;
import com.example.ISAISA.DTO.PharmacistDTO;
import com.example.ISAISA.DTO.UserChangeDTO;
import com.example.ISAISA.model.Appointment;
import com.example.ISAISA.model.Patient;
import com.example.ISAISA.model.Pharmacist;
import com.example.ISAISA.model.Pharmacy;
import com.example.ISAISA.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PatientService {

    private PatientRepository patientRepository;

    @Autowired
    public void setPatientRepository(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Optional<Patient> findById(Integer patientId) {
        return patientRepository.findById(patientId);
    }
    public Patient save(Patient patient){return patientRepository.save(patient);}

    public Patient changePatientInfo(UserChangeDTO userDTO) {

        Patient user = (Patient) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setAddress(userDTO.getAddress());
        user.setPhone(userDTO.getPhone());
        user.setCity(userDTO.getCity());
        user.setCountry(userDTO.getCountry());

        patientRepository.save(user);

        return user;
    }

    public Patient deletePromotion(Patient patient, Pharmacy pharmacy){
        Set<Pharmacy> pharmacies= patient.getPharmacies_promotions();
        Set<Pharmacy> pharmacies1= new HashSet<Pharmacy>();
        for(Pharmacy pharmacy1: pharmacies){
            if (pharmacy1.getId()!=pharmacy.getId()){
                pharmacies1.add(pharmacy1);
            }
        }
        patient.setPharmacies_promotions(pharmacies1);
        Patient patient1=patientRepository.save(patient);
        return patient1;
    }

    public Patient addPromotion(Patient patient,Pharmacy pharmacy){
        Set<Pharmacy> pharmacies=patient.getPharmacies_promotions();
        pharmacies.add(pharmacy);
        patient.setPharmacies_promotions(pharmacies);
        patient=patientRepository.save(patient);
        return  patient;
    }

    public List<PatientSearchDto> getAllSearchPatients() {

        List<Patient> patients = patientRepository.findAllBy();

        List<PatientSearchDto> patientDTOS = new ArrayList<>();
        for(Patient p : patients) {
            PatientSearchDto patientDTO = new PatientSearchDto(p.getId() , p.getFirstName(), p.getLastName(), p.getAddress(), p.getPhone(), p.getCity());
            patientDTOS.add(patientDTO);
        }

        return patientDTOS;
    }

    public Set<PatientSearchDto> getPatientByFirstNameAndLastName(String firstName, String lastName) {

        Set<Patient> patients = patientRepository.findAllByFirstNameIgnoreCaseAndLastNameIgnoreCase(firstName, lastName);

        Set<PatientSearchDto> patientDTOS = new HashSet<>();
        for(Patient p : patients) {
            PatientSearchDto patientDTO = new PatientSearchDto(p.getId() , p.getFirstName(), p.getLastName(), p.getAddress(), p.getPhone(), p.getCity());
            patientDTOS.add(patientDTO);
        }

        return patientDTOS;
    }
}
