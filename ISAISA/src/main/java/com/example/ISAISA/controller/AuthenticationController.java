package com.example.ISAISA.controller;

import com.example.ISAISA.DTO.*;
import com.example.ISAISA.model.*;
import com.example.ISAISA.repository.ConfirmationTokenRepository;
import com.example.ISAISA.repository.OfferRepository;
import com.example.ISAISA.repository.OrderRepository;
import com.example.ISAISA.repository.UserRepository;
import com.example.ISAISA.model.User;
import com.example.ISAISA.model.UserRequest;
import com.example.ISAISA.model.UserTokenState;
import com.example.ISAISA.security.TokenUtils;
import com.example.ISAISA.security.auth.JwtAuthenticationRequest;
import com.example.ISAISA.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController
@RequestMapping(value="/auth", produces= MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {
    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private ComplaintService complaintService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PharmacyService pharmacyService;

    @Autowired
    private OfferService offerService;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private UserServiceDetails userDetailsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private UserService userService;

    // Prvi endpoint koji pogadja korisnik kada se loguje.
    // Tada zna samo svoje korisnicko ime i lozinku i to prosledjuje na backend.
    @PostMapping("/login")
    public ResponseEntity<UserTokenRoleDto> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest,
                                                                    HttpServletResponse response) {

        //Poziva se loadByUsername iz UserDetails i proverava da li postoji user sa ovakvim usernameom
        //Ako ne postoji, dobijamo badcredentials i vraca se 401 na klijentskoj strani
        //odbija se logovanje ako se za username vrati null
        //Ako korisnik postoji, password ce se automatski preko passwordEncodera hesirati po bcrypt jer je tako specif
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()));

        // Ubaci korisnika u trenutni security kontekst
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Kreiraj token za tog korisnika
        User user = (User) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user.getEmail());
        int expiresIn = tokenUtils.getExpiredIn();

        String rola= user.getDecriminatorValue();
        Boolean enabled= user.isEnabled();

        // Vrati token kao odgovor na uspesnu autentifikaciju
        return ResponseEntity.ok(new UserTokenRoleDto(jwt,expiresIn,rola,enabled));
    }



    // Endpoint za registraciju novog korisnika
    @PostMapping("/signup")
    public ResponseEntity<User> addUser(@RequestBody UserRequest userRequest, UriComponentsBuilder ucBuilder) throws ResourceConflictException {

        User existUser = this.userService.findByEmail(userRequest.getEmail());
        if (existUser != null) {
            throw new ResourceConflictException(userRequest.getId(), "Username already exists");
        }

        User user = this.userService.save(userRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{userId}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<User>(user, HttpStatus.CREATED);
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






    @PostMapping("/signupPatient")
    public ResponseEntity<User> addPatient(@RequestBody PatientDto patientDto, UriComponentsBuilder ucBuilder) throws ResourceConflictException, Exception {

        User existUser = this.userService.findByEmail(patientDto.getEmail());
        if (existUser != null) {
            throw new Exception("Postoji User");
        }

        User user = this.userService.savePatient(patientDto);
        ConfirmationToken confirmationToken = new ConfirmationToken(user);

        confirmationTokenRepository.save(confirmationToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("isaverifikacija@gmail.com");
        mailMessage.setText("To confirm your account, please click here : "
                +"http://localhost:8081/auth/confirm-account?token="+confirmationToken.getConfirmationToken());

        emailSenderService.sendEmail(mailMessage);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{userId}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }

    @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    public String confirmUserAccount(@RequestParam("token")String confirmationToken)
    {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if(token != null)
        {
            User user = userRepository.findByEmail(token.getUser().getEmail());
            user.setEnabled(true);
            userRepository.save(user);
            return "Cestitamo uspesno ste verifikovali nalog";
        }
        else
        {
            return "error";
        }


    }


    // U slucaju isteka vazenja JWT tokena, endpoint koji se poziva da se token osvezi
    @PostMapping(value = "/refresh")
    public ResponseEntity<UserTokenState> refreshAuthenticationToken(HttpServletRequest request) {

        String token = tokenUtils.getToken(request);
        String username = this.tokenUtils.getUsernameFromToken(token);
        User user = (User) this.userDetailsService.loadUserByUsername(username);

        if (this.tokenUtils.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
            String refreshedToken = tokenUtils.refreshToken(token);
            int expiresIn = tokenUtils.getExpiredIn();

            return ResponseEntity.ok(new UserTokenState(refreshedToken, expiresIn));
        } else {
            UserTokenState userTokenState = new UserTokenState();
            return ResponseEntity.badRequest().body(userTokenState);
        }
    }

    @PostMapping(value = "/change-password")
    @PreAuthorize("hasRole('ADMINPHARMACY')")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChanger passwordChanger) {

        userDetailsService.changePassword(passwordChanger.oldPassword, passwordChanger.newPassword);

        Map<String, String> result = new HashMap<String, String>();
        result.put("result", "success");
        return ResponseEntity.accepted().body(result);
    }

    static class PasswordChanger {
        public String oldPassword;
        public String newPassword;
    }




}