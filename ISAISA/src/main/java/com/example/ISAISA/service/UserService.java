package com.example.ISAISA.service;

import com.example.ISAISA.DTO.AdminSystemRegDto;
import com.example.ISAISA.DTO.MedicationRegDTO;
import com.example.ISAISA.model.*;
import com.example.ISAISA.repository.MedicationRepository;
import com.example.ISAISA.repository.PharmacyRepository;
import com.example.ISAISA.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    private MedicationRepository medicationRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthorityService authService;

    @Autowired
    private PharmacyRepository pharmacyRepository;

    public User findById(Integer id) throws AccessDeniedException {
        User u = userRepository.findById(id).orElseGet(null);
        return u;
    }

    public List<User> findAll() throws AccessDeniedException {
        List<User> result = userRepository.findAll();
        return result;
    }

    public User save(UserRequest userRequest) {
        User u = new User();
        u.setEmail(userRequest.getEmail());
        // pre nego sto postavimo lozinku u atribut hesiramo je
        u.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        u.setFirstName(userRequest.getFirstname());
        u.setLastName(userRequest.getLastname());
        u.setEnabled(true);

        List<Authority> auth = authService.findByname("ROLE_USER");
        // u primeru se registruju samo obicni korisnici i u skladu sa tim im se i dodeljuje samo rola USER
        u.setAuthorities(auth);

        u = this.userRepository.save(u);
        return u;
    }

    public  Medication saveMedication(MedicationRegDTO medicationRegDTO,Medication medicationAlt){
        Medication medication= new Medication();
        medication.setCode(medicationRegDTO.getCode());
        medication.setContraindication(medicationRegDTO.getContraindication());
        medication.setName(medicationRegDTO.getName());
        medication.setIngredients(medicationRegDTO.getIngredients());
        medication.setProducer(medicationRegDTO.getProducer());
        medication.setShape_med(medicationRegDTO.getShape_med());
        medication.setType_med(medicationRegDTO.getType_med());
        medication.setContraindication(medicationRegDTO.getContraindication());
        medication.setRecommended_daily_intake(medicationRegDTO.getRecommended_daily_intake());
        Set <Medication> medications= new HashSet<Medication>();
        medications.add(medicationAlt);
        medication.setMedication(medications);

        medication=this.medicationRepository.save(medication);
        return medication;
    }

    public User saveDermatologist(PatientDto userRequest) {
        Dermatologist u = new Dermatologist();
        u.setEmail(userRequest.getEmail());
        // pre nego sto postavimo lozinku u atribut hesiramo je
        u.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        u.setFirstName(userRequest.getNamee());
        u.setLastName(userRequest.getLastName());
        u.setEnabled(true);
        u.setAddress(userRequest.getAdress());
        u.setCity(userRequest.getCity());
        u.setCountry(userRequest.getCountry());
        u.setPhone(userRequest.getPhoneNumber());
        u.setFlag(0);
        u.setEnabled(true);

        List<Authority> auth = authService.findByname("ROLE_DERMATOLOGIST");

        u.setAuthorities(auth);

        u = this.userRepository.save(u);
        return u;
    }

    public User saveAdminSystem(PatientDto userRequest) {
        AdminSystem u = new AdminSystem();
        u.setEmail(userRequest.getEmail());
        // pre nego sto postavimo lozinku u atribut hesiramo je
        u.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        u.setFirstName(userRequest.getNamee());
        u.setLastName(userRequest.getLastName());
        u.setEnabled(true);
        u.setAddress(userRequest.getAdress());
        u.setCity(userRequest.getCity());
        u.setCountry(userRequest.getCountry());
        u.setPhone(userRequest.getPhoneNumber());
        u.setFlag(0);
        u.setEnabled(true);

        List<Authority> auth = authService.findByname("ROLE_ADMINSYSTEM");

        u.setAuthorities(auth);

        u = this.userRepository.save(u);
        return u;
    }

    public User changeFlag(Supplier user){
        user.setFlag(1);
        user=userRepository.save(user);
        return user;
    }

    public User changeFlagSystemAdmin(AdminSystem user){
        user.setFlag(1);
        user=userRepository.save(user);
        return user;
    }

    public User changeFlagAdminPharmacy(AdminPharmacy user){
        user.setFlag(1);
        user=userRepository.save(user);
        return user;
    }


    public User saveSupplier(PatientDto userRequest) {
        Supplier u = new Supplier();
        u.setEmail(userRequest.getEmail());
        // pre nego sto postavimo lozinku u atribut hesiramo je
        u.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        u.setFirstName(userRequest.getNamee());
        u.setLastName(userRequest.getLastName());
        u.setEnabled(true);
        u.setAddress(userRequest.getAdress());
        u.setCity(userRequest.getCity());
        u.setCountry(userRequest.getCountry());
        u.setPhone(userRequest.getPhoneNumber());
        u.setFlag(0);
        u.setEnabled(true);

        List<Authority> auth = authService.findByname("ROLE_SUPPLIER");

        u.setAuthorities(auth);

        u = this.userRepository.save(u);
        return u;
    }


    public User saveAdminPharmacy(AdminSystemRegDto userRequest) {
        AdminPharmacy a = new AdminPharmacy();
        a.setEmail(userRequest.getEmail());
        // pre nego sto postavimo lozinku u atribut hesiramo je
        a.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        a.setFirstName(userRequest.getFirstName());
        a.setLastName(userRequest.getLastName());
        a.setAddress(userRequest.getAddress());
        a.setCountry(userRequest.getCountry());
        a.setCity(userRequest.getCity());
        a.setPhone(userRequest.getPhone());
        Pharmacy pharmacy= pharmacyRepository.getOne(userRequest.getPharmacyID());
        a.setPharmacy(pharmacy);
        a.setEnabled(true);
        a.setFlag(0);

        List<Authority> auth = authService.findByname("ROLE_ADMINPHARMACY");
        // u primeru se registruju samo obicni korisnici i u skladu sa tim im se i dodeljuje samo rola USER
        a.setAuthorities(auth);

        a = this.userRepository.save(a);
        return a;
    }

    public User savePatient(PatientDto userRequest) {
        Patient p = new Patient();
        p.setEmail(userRequest.getEmail());
        // pre nego sto postavimo lozinku u atribut hesiramo je
        p.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        p.setFirstName(userRequest.getNamee());
        p.setLastName(userRequest.getLastName());
        p.setAddress(userRequest.getAdress());
        p.setCountry(userRequest.getCountry());
        p.setCity(userRequest.getCity());
        p.setPhone(userRequest.getPhoneNumber());

        p.setEnabled(false);

        List<Authority> auth = authService.findByname("ROLE_PATIENT");
        // u primeru se registruju samo obicni korisnici i u skladu sa tim im se i dodeljuje samo rola USER
        p.setAuthorities(auth);

        p = this.userRepository.save(p);
        return p;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
