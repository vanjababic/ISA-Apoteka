package com.example.ISAISA.service;

import com.example.ISAISA.DTO.PharmacistDTO;
import com.example.ISAISA.model.*;
import com.example.ISAISA.repository.EmployeeRepository;
import com.example.ISAISA.repository.MedicationRepository;
import com.example.ISAISA.repository.PharmacyMedicationRepository;
import com.example.ISAISA.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MedicationService {
    private MedicationRepository medicationRepository;
    private PharmacyMedicationRepository pharmacyMedicationRepository;
    private ReservationRepository reservationRepository;

    @Autowired
    public void setMedicationRepository(MedicationRepository medicationRepository) { this.medicationRepository = medicationRepository; }

    @Autowired
    public void setPharmacyMedicationRepository(PharmacyMedicationRepository pharmacyMedicationRepository) { this.pharmacyMedicationRepository = pharmacyMedicationRepository; }

    @Autowired
    public void setReservationRepository(ReservationRepository reservationRepository) { this.reservationRepository = reservationRepository;
   }

    public List<Medication> findAll(){
        return medicationRepository.findAll();
    }


    public Set<Medication> findAllByName(String name){return medicationRepository.findAllByName(name);}
    public Medication findByName(String name){return medicationRepository.findByName(name);}
    public Medication findById(Integer id) {return medicationRepository.findOneById(id);}

    public Set<Medication> getMedicationsbyName(String name){
        Set<Medication> medications=medicationRepository.findAllByName(name);
        Set<Medication> medicationDTOS = new HashSet<>();
        for( Medication m: medications){
            Medication meds=new Medication(m.getCode(),m.getName(),m.getType_med(),m.getShape_med(),m.getIngredients(),m.getProducer(),m.getPrescription(),m.getNotes());
            medicationDTOS.add(meds);
        }

        return medicationDTOS;
    }


    public Set<Medication> getMedicationsbyNamee(String name) {
        Set<Medication> medications = medicationRepository.findAllByName(name);


        return medications;

    }
    //Prikaz
    /*public Set<Medication> getMedicationByPharmacy(Pharmacy pharmacy) {
        Set<PharmacyMedication> pharmacyMedications = pharmacy.getPharmacy_medications();
        Set<Medication> medications = new HashSet<>();
        for(PharmacyMedication pm : pharmacyMedications) {
            medications.add(pm.getMedication());
        }
        return medications;
    }*/

    public Set<Medication> getMedicationNotInPharmacy(Pharmacy pharmacy) {
        //Svi lekovi
        List<Medication> allMedicationsList = findAll();
        Set<Integer> allMedicationIds = new HashSet<>();
        for(Medication m : allMedicationsList) {
            allMedicationIds.add(m.getId());
        }

        //Lekovi koje ima
        Set<Medication> medicationsHave = getMedicationByPharmacy(pharmacy);
        Set<Integer> medicationsHaveIds = new HashSet<>();
        for(Medication m : medicationsHave) {
            medicationsHaveIds.add(m.getId());
        }

        //Lekovi koje nema
        Set<Medication> medicationsNot = new HashSet<>();
        //Set<Integer> medicationsNotIds = new HashSet<>();


        for(Integer id : allMedicationIds) {
            if (!medicationsHaveIds.contains(id)) {
                //medicationsNotIds.add(id);
                medicationsNot.add(findById(id));
            }
        }

        return medicationsNot;
    }

    //Pretraga
    public Set<Medication> getMedicationByNameAndPharmacy(Pharmacy pharmacy, String name) {
        Set<PharmacyMedication> pharmacyMedications = pharmacy.getPharmacy_medications();
        Medication medication = medicationRepository.findByNameIgnoreCase(name);
        Set<Medication> chosenMedication = new HashSet<>();

        for (PharmacyMedication pm : pharmacyMedications) {
            if (pm.getMedication().getId().equals(medication.getId())) {
                chosenMedication.add(medication);
            }
        }

        return chosenMedication;
    }

    //Dodavanje
    public PharmacyMedication addMedicationToPharmacy(PharmacyMedication pharmacyMedication) {
        return this.pharmacyMedicationRepository.save(pharmacyMedication);
    }

    //Brisanje
    public void removeMedicationFromPharmacy(Medication medication, Pharmacy pharmacy) throws Exception {

        Reservation reservation = reservationRepository.findOneByMedicationAndPharmacy(medication, pharmacy);
        if (reservation != null) {
            if(!reservation.getMedicationtaken() && reservation.getDateofreservation().isAfter(LocalDateTime.now())) {
                throw new Exception("Nije moguce obrisati lek jer je rezervisan i nije jos preuzet!");
            }
        }

        Set<PharmacyMedication> pharmacyMedications = pharmacyMedicationRepository.findByPharmacyAndMedicationOrderByBeginPriceValidityDesc(pharmacy, medication);
        List<PharmacyMedication> pharmacyMedications1 = new ArrayList<>(pharmacyMedications);
        if (pharmacyMedications1.isEmpty()) {
            throw new Exception("Doslo je do greske!");
        }
        PharmacyMedication pharmacyMedication = pharmacyMedications1.get(0);

        pharmacyMedicationRepository.delete(pharmacyMedication);
    }

    //Prikaz
    public Set<Medication> getMedicationByPharmacy(Pharmacy pharmacy) {
        List<Medication> medications = medicationRepository.findAll();
        Set<PharmacyMedication> pharmacyMedications = new HashSet<>();
        for (Medication medication : medications) {
            Set<PharmacyMedication> pharmacyMedications1 = pharmacyMedicationRepository.findByPharmacyAndMedicationOrderByBeginPriceValidityDesc(pharmacy, medication);
            List<PharmacyMedication> pharmacyMedications2 = new ArrayList<>(pharmacyMedications1);
            if(!pharmacyMedications2.isEmpty())
                pharmacyMedications.add(pharmacyMedications2.get(0));
        }

        Set<Medication> chosenMedication = new HashSet<>();

        for (PharmacyMedication pm : pharmacyMedications) {
            chosenMedication.add(pm.getMedication());
        }

        return chosenMedication;
    }

    public PharmacyMedication changeMedication(Integer price, Integer id, Pharmacy pharmacy, LocalDate end) {
        Medication medication = medicationRepository.findOneById(id);
        Set<PharmacyMedication> pharmacyMedications1 = pharmacyMedicationRepository.findByPharmacyAndMedicationOrderByBeginPriceValidityDesc(pharmacy, medication);
        List<PharmacyMedication> pharmacyMedications2 = new ArrayList<>(pharmacyMedications1);

        PharmacyMedication pharmacyMedicationBefore = pharmacyMedications2.get(0);
        PharmacyMedication pharmacyMedicationNow;
        if (pharmacyMedicationBefore.getPrice() == null) {
            pharmacyMedicationNow = pharmacyMedicationBefore;
            pharmacyMedicationNow.setPrice(price);
            pharmacyMedicationNow.setBeginPriceValidity(LocalDate.now());
            pharmacyMedicationNow.setEndPriceValidity(end);

        } else {
            //Postavi kraj perioda vazenja cene poslednjeg pojavljivanja u pharmacyMedication na danas
            pharmacyMedicationBefore.setEndPriceValidity(LocalDate.now());
            pharmacyMedicationBefore = pharmacyMedicationRepository.save(pharmacyMedicationBefore);
            //Postavi pocetak perioda vazenja cene novog pojavljivanja u pharmacyMedication na danas
             pharmacyMedicationNow = new PharmacyMedication(pharmacyMedicationBefore.getPharmacy(), pharmacyMedicationBefore.getMedication(),
                    pharmacyMedicationBefore.getQuantity(), price, LocalDate.now().plusDays(1), end);
        }
        pharmacyMedicationNow = pharmacyMedicationRepository.save(pharmacyMedicationNow);

        return pharmacyMedicationNow;
    }

    public PharmacyMedication changeMedicationQuantity(Integer quantity, Integer id, Pharmacy pharmacy) {
        Medication medication = medicationRepository.findOneById(id);
        Set<PharmacyMedication> pharmacyMedications1 = pharmacyMedicationRepository.findByPharmacyAndMedicationOrderByBeginPriceValidityDesc(pharmacy, medication);
        List<PharmacyMedication> pharmacyMedications2 = new ArrayList<>(pharmacyMedications1);

        PharmacyMedication pharmacyMedicationBefore = pharmacyMedications2.get(0);

        pharmacyMedicationBefore.setQuantity(quantity);

        pharmacyMedicationBefore = pharmacyMedicationRepository.save(pharmacyMedicationBefore);

        return pharmacyMedicationBefore;

    }
}
