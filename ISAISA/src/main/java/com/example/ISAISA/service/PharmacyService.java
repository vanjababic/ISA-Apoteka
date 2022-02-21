package com.example.ISAISA.service;

import com.example.ISAISA.DTO.PharmacyDTO;
import com.example.ISAISA.DTO.PharmacyRegDTO;
import com.example.ISAISA.model.*;
import com.example.ISAISA.repository.PharmacyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PharmacyService {

    private PharmacyRepository pharmacyRepository;

    @Autowired
    public void setPharmacistRepository(PharmacyRepository pharmacyRepository) {
        this.pharmacyRepository = pharmacyRepository;
    }

    public Pharmacy findById (Integer id){return pharmacyRepository.findOneById(id);}

    public Pharmacy findByAddress(String address) throws AccessDeniedException {
        Pharmacy p = pharmacyRepository.findByAddress(address);
        return p;
    }

    public Set<Pharmacy> findByAddress1(String address) {return  pharmacyRepository.findAllByAddress(address);}

    public Pharmacy findbyPharmacist(Pharmacist pharmacist) {return  pharmacyRepository.findByPharmacists(pharmacist);}
    public List<Pharmacy> findbyratinggreater(Float rating){return pharmacyRepository.findByRatingGreaterThan(rating);}
    public Pharmacy save(PharmacyRegDTO pharmacyDTO) {
        Pharmacy p = new Pharmacy();
        p.setAddress(pharmacyDTO.getAddress());
        p.setName(pharmacyDTO.getName());
        p.setDescription(pharmacyDTO.getDescription());


        p = this.pharmacyRepository.save(p);
        return p;
    }

    public List<Pharmacy> findAll(){
        return pharmacyRepository.findAll();
    }

    public List<Pharmacy> findallByMedication(Medication medication) {
        List<Pharmacy> pharmacies = new ArrayList<>();
        for(PharmacyMedication pm : medication.getPharmacy_medications()) {
            pharmacies.add(pm.getPharmacy());
        }
        return pharmacies;
    }

    public Pharmacy findByName(String name) { return pharmacyRepository.findByName(name); }

    public Pharmacy findByRating(Float rating){return  pharmacyRepository.findByRating(rating);}


    public Set<Pharmacy> getPharmaciesbyname(String name){
        Set<Pharmacy> pharmacies=pharmacyRepository.findAllByName(name);
        Set<Pharmacy> pharmaciesDTOS = new HashSet<>();
        for( Pharmacy m: pharmacies){
            Pharmacy meds=new Pharmacy(m.getId(),m.getName(),m.getAddress(),m.getDescription(),m.getRating());
            pharmaciesDTOS.add(meds);
        }

        return pharmaciesDTOS;
    }

    public Pharmacy changePharmacy(Pharmacy pharmacy) {
        AdminPharmacy user = (AdminPharmacy) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Pharmacy pharmacyToChange = user.getPharmacy();

        pharmacyToChange.setName(pharmacy.getName());
        pharmacyToChange.setAddress(pharmacy.getAddress());
        pharmacyToChange.setDescription(pharmacy.getDescription());

        pharmacyToChange = pharmacyRepository.save(pharmacyToChange);

        return pharmacyToChange;
    }



}
