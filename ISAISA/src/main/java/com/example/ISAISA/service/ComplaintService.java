package com.example.ISAISA.service;


import com.example.ISAISA.DTO.AdminSystemRegDto;
import com.example.ISAISA.DTO.ReplyDTO;
import com.example.ISAISA.model.*;
import com.example.ISAISA.repository.ComplaintsRepository;
import com.example.ISAISA.repository.PharmacistRepository;
import com.example.ISAISA.repository.PharmacyRepository;
import com.example.ISAISA.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComplaintService {

    @Autowired
    private PharmacistRepository userRepository;

    @Autowired
    public void setUserRepository(PharmacistRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    private ComplaintsRepository complaintsRepository;

    @Autowired
    private PharmacyRepository pharmacyRepository;

    @Autowired
    public void setComplaintsRepository(ComplaintsRepository complaintsRepository) {
        this.complaintsRepository = complaintsRepository;
    }

    public List<Complaint> findAll(){
        return complaintsRepository.findAll();
    }

    public Complaint saveReply(ReplyDTO replyDTO) {

        Complaint complaint= complaintsRepository.getOne(replyDTO.getComplaintID());
        complaint.setReply(replyDTO.getReply());
        complaint.setAnswered(true);
        complaint=this.complaintsRepository.save(complaint);
        return complaint;

    }

    public Complaint saveComplaintPharmacist(ReplyDTO replyDTO,Patient user) {


        Complaint complaint= new Complaint();
        complaint.setQuestion(replyDTO.getReply());
        complaint.setPatient(user);
        complaint.setIshospital(false);
        Pharmacist pharmacist=userRepository.getOne(replyDTO.getComplaintID());
        complaint.setUser(pharmacist);
        complaint=this.complaintsRepository.save(complaint);
        return complaint;

    }

    public Complaint saveComplaintDermatologist(ReplyDTO replyDTO,Patient user) {


        Complaint complaint= new Complaint();
        complaint.setQuestion(replyDTO.getReply());
        complaint.setPatient(user);
        complaint.setIshospital(false);
        Pharmacist pharmacist=userRepository.getOne(replyDTO.getComplaintID());
        complaint.setUser(pharmacist);
        complaint=this.complaintsRepository.save(complaint);
        return complaint;

    }

    public Complaint saveComplaintPharmacy(ReplyDTO replyDTO,Patient user) {


        Complaint complaint= new Complaint();
        complaint.setQuestion(replyDTO.getReply());
        complaint.setPatient(user);
        complaint.setIshospital(true);
        Pharmacy pharmacy= pharmacyRepository.getOne(replyDTO.getComplaintID());
        complaint.setPharmacy(pharmacy);
        complaint=this.complaintsRepository.save(complaint);
        return complaint;

    }
}
