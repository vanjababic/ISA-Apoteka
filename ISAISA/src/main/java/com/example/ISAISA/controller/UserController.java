package com.example.ISAISA.controller;


import com.example.ISAISA.model.Patient;
import com.example.ISAISA.model.PatientDto;
import com.example.ISAISA.model.User;
import com.example.ISAISA.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value="/user",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<User> getKorisnik() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


}
