package com.example.ISAISA.service;

import com.example.ISAISA.DTO.UserChangeDTO;
import com.example.ISAISA.model.AdminPharmacy;
import com.example.ISAISA.model.Supplier;
import com.example.ISAISA.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SupplierService {

    private SupplierRepository supplierRepository;

    @Autowired
    public void setSupplierRepository(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }
    public Supplier changeSupplierInfo(UserChangeDTO userDTO) {

        Supplier user = (Supplier) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setAddress(userDTO.getAddress());
        user.setPhone(userDTO.getPhone());
        user.setCity(userDTO.getCity());
        user.setCountry(userDTO.getCountry());

        user=supplierRepository.save(user);

        return user;
    }
}
