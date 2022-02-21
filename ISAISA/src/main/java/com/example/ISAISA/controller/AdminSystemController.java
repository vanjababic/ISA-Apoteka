package com.example.ISAISA.controller;

import com.example.ISAISA.DTO.*;
import com.example.ISAISA.model.*;
import com.example.ISAISA.repository.*;
import com.example.ISAISA.security.TokenUtils;
import com.example.ISAISA.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/systemadmins")
public class AdminSystemController {

    @Autowired
    private AdminSystemService adminSystemService;

    @Autowired
    private ComplaintsRepository complaintsRepository;

    @Autowired
    private ComplaintService complaintService;

    @Autowired
    private MedicationRepository medicationRepository;

    @Autowired
    private PharmacyService pharmacyService;

    @Autowired
    private UserServiceDetails userDetailsService;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private UserService userService;

    @Autowired
    public void setAdminSystemService(AdminSystemService adminSystemService) {
        this.adminSystemService = adminSystemService;
    }

    @PostMapping(value="/signupAdminPharmacy", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMINSYSTEM')")
    public ResponseEntity<User> addAdminPharmacy(@RequestBody AdminSystemRegDto adminSystemRegDto, UriComponentsBuilder ucBuilder) throws ResourceConflictException, Exception {

        User existUser = this.userService.findByEmail(adminSystemRegDto.getEmail());
        if (existUser != null) {
            throw new Exception("Postoji User");
        }

        User user = this.userService.saveAdminPharmacy(adminSystemRegDto);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{userId}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }

    @PostMapping(value="/signupAdmin",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMINSYSTEM')")
    public ResponseEntity<User> addAdmin(@RequestBody PatientDto patientDto, UriComponentsBuilder ucBuilder) throws ResourceConflictException, Exception {

        User existUser = this.userService.findByEmail(patientDto.getEmail());
        if (existUser != null) {
            throw new Exception("Postoji User");
        }

        User user = this.userService.saveAdminSystem(patientDto);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{userId}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }

    @PostMapping(value="/registerMedication",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMINSYSTEM')")
    public ResponseEntity<Medication> registerMedication(@RequestBody MedicationRegDTO medicationRegDTO, UriComponentsBuilder ucBuilder) throws ResourceConflictException, Exception {

        Medication existmedication = this.medicationRepository.findByCode(medicationRegDTO.getCode());
        if (existmedication != null) {
            throw new Exception("Lek je vec registrovan");
        }

        Medication medication1= this.medicationRepository.findByName(medicationRegDTO.getAlternative());

        Medication medication=this.userService.saveMedication(medicationRegDTO,medication1);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{userId}").buildAndExpand(medication.getId()).toUri());
        return new ResponseEntity<Medication>(medication, HttpStatus.CREATED);
    }

    @GetMapping(value="/allcomplaints",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMINSYSTEM')")
    public ResponseEntity<List<ComplainttDTO>> getComplaints() {
        List<Complaint> complaintList = complaintsRepository.findAllByIshospital(false);

        List<ComplainttDTO> complainttDTOS= new ArrayList<ComplainttDTO>();

        for (Complaint complaint: complaintList){
            Patient patient=complaint.getPatient();
            String emailpatient= patient.getEmail();
            User user=complaint.getUser();
            String emailuser= user.getEmail();

            ComplainttDTO complainttDTO= new ComplainttDTO(complaint.getId(),complaint.isAnswered(),complaint.getQuestion(),complaint.getReply(),emailuser,emailpatient,complaint.isIshospital());
            complainttDTOS.add(complainttDTO);
        }



        return new ResponseEntity<>(complainttDTOS, HttpStatus.OK);
    }


    @GetMapping(value="/allcomplaintspharmacy",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMINSYSTEM')")
    public ResponseEntity<List<ComplainttDTO>> getComplaintsPharmacy() {
        List<Complaint> complaintList = complaintsRepository.findAllByIshospital(true);

        List<ComplainttDTO> complainttDTOS= new ArrayList<ComplainttDTO>();


        for (Complaint complaint: complaintList){
            Patient patient=complaint.getPatient();
            String emailpatient= patient.getEmail();
            Pharmacy pharmacy= complaint.getPharmacy();
            String nazivApoteke=pharmacy.getName();


            ComplainttDTO complainttDTO= new ComplainttDTO(complaint.getId(),complaint.isAnswered(),complaint.getQuestion(),complaint.getReply(),complaint.isIshospital(),emailpatient,nazivApoteke);
            complainttDTOS.add(complainttDTO);
        }



        return new ResponseEntity<>(complainttDTOS, HttpStatus.OK);
    }

    @PostMapping("/reply")
    public ResponseEntity<Complaint> addReply(@RequestBody ReplyDTO replyDTO, UriComponentsBuilder ucBuilder) throws ResourceConflictException, Exception {

        Complaint complaint = this.complaintService.saveReply(replyDTO);
        Patient patient= complaint.getPatient();
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(patient.getEmail());
        mailMessage.setSubject("Odgovor na zalbu");
        mailMessage.setFrom("isaverifikacija@gmail.com");
        mailMessage.setText(complaint.getReply());

        emailSenderService.sendEmail(mailMessage);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{userId}").buildAndExpand(complaint.getId()).toUri());
        return new ResponseEntity<Complaint>(complaint, HttpStatus.CREATED);
    }


    @PostMapping(value="/signupPharmacy", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMINSYSTEM')")
    public ResponseEntity<Pharmacy> addPharmacy(@RequestBody PharmacyRegDTO pharmacyDto, UriComponentsBuilder ucBuilder) throws ResourceConflictException, Exception {

        Pharmacy existPharmacy = this.pharmacyService.findByAddress(pharmacyDto.getAddress());
        if (existPharmacy != null) {
            throw new Exception("Postoji User");
        }

        Pharmacy pharmacy = this.pharmacyService.save(pharmacyDto);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{userId}").buildAndExpand(pharmacy.getId()).toUri());
        return new ResponseEntity<Pharmacy>(pharmacy, HttpStatus.CREATED);
    }


    @RequestMapping(value="/admin", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMINSYSTEM')")
    public ResponseEntity<AdminSystem> getAdminSystem() {
        AdminSystem user = (AdminSystem) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @PostMapping(value = "/change-password-firsttime", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMINSYSTEM')")
    public ResponseEntity<?> changePasswordFirstTime(@RequestBody AuthenticationController.PasswordChanger passwordChanger) {



        AdminSystem user = (AdminSystem) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user1=userService.changeFlagSystemAdmin(user);
        userDetailsService.changePassword(passwordChanger.oldPassword, passwordChanger.newPassword);

        Map<String, String> result = new HashMap<String, String>();
        result.put("result", "success");

        return ResponseEntity.accepted().body(result);
    }
    @PostMapping(value="/signupSupplier",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMINSYSTEM')")
    public ResponseEntity<User> addSupplier(@RequestBody PatientDto patientDto, UriComponentsBuilder ucBuilder) throws ResourceConflictException, Exception {

        User existUser = this.userService.findByEmail(patientDto.getEmail());
        if (existUser != null) {
            throw new Exception("Postoji User");
        }

        User user = this.userService.saveSupplier(patientDto);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{userId}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }

    @PostMapping(value="/signupDermatologist",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMINSYSTEM')")
    public ResponseEntity<User> addAdminPharmacy(@RequestBody PatientDto patientDto, UriComponentsBuilder ucBuilder) throws ResourceConflictException, Exception {

        User existUser = this.userService.findByEmail(patientDto.getEmail());
        if (existUser != null) {
            throw new Exception("Postoji User");
        }

        User user = this.userService.saveDermatologist(patientDto);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{userId}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }
}
