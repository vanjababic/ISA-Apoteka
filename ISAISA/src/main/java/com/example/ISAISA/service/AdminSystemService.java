package com.example.ISAISA.service;

import com.example.ISAISA.repository.AdminSystemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminSystemService{

    private AdminSystemRepository adminSystemRepository;

    @Autowired
    public void setAdminSystemRepository(AdminSystemRepository adminSystemRepository) {
        this.adminSystemRepository = adminSystemRepository;
    }
}
