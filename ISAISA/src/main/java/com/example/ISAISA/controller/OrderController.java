package com.example.ISAISA.controller;

import com.example.ISAISA.DTO.*;
import com.example.ISAISA.model.AdminPharmacy;
import com.example.ISAISA.model.Medication;
import com.example.ISAISA.model.Orderr;
import com.example.ISAISA.model.Orderr_Medication;
import com.example.ISAISA.service.AdminPharmacyService;
import com.example.ISAISA.service.MedicationService;
import com.example.ISAISA.service.OrderService;
import com.example.ISAISA.service.PharmacistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Id;
import java.util.*;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    private OrderService orderService;
    private MedicationService medicationService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setMedicationService(MedicationService medicationService) { this.medicationService = medicationService; }

    @PostMapping(value="/createOrder", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMINPHARMACY')")
    public ResponseEntity<Orderr> createOrder(@RequestBody OrderDTO orderDTO) throws Exception {

        AdminPharmacy user = (AdminPharmacy) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        orderDTO.setAdminPharmacy(user);
        orderDTO.setStatusAdmin("ceka_ponude");

        Orderr order = orderService.createOrder(orderDTO);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping(value="/ordersByPharmacy", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMINPHARMACY')")
    public ResponseEntity<Set<OrderMedicationDTO>> getOrdersByPharmacy(){
        AdminPharmacy user = (AdminPharmacy) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Set<Orderr> orderrs = orderService.getOrdersByPharmacy(user);
        Set<OrderMedicationDTO> orderMedicationDTOS = new HashSet<>();

        for (Orderr orderr : orderrs) {
            OrderMedicationDTO om = new OrderMedicationDTO(orderr.getId(), orderr.getDateDeadline(), orderr.getAdminPharmacy());
            orderMedicationDTOS.add(om);
        }

        return new ResponseEntity<>(orderMedicationDTOS, HttpStatus.OK);
    }

    @PostMapping(value="/deleteOrder", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMINPHARMACY')")
    public void deleteOrder(@RequestBody IdDto idDto) throws Exception {

        Orderr orderr = orderService.findById(idDto.getId());

        orderService.deleteOrder(orderr);
    }

    @PostMapping(value="/orderById", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMINPHARMACY')")
    public ResponseEntity<OrderDTO> getOrderById(@RequestBody IdDto idDto) {
        Orderr orderr = orderService.findById(idDto.getId());

        List<Medication> medications = new ArrayList<>();
        List<Integer> amounts = new ArrayList<>();

        for (Orderr_Medication om : orderr.getOrderr_medications()) {
            medications.add(om.getMedication());
            amounts.add(om.getAmount());
        }

        OrderDTO orderDTO = new OrderDTO(medications, amounts);

        return new ResponseEntity<>(orderDTO, HttpStatus.OK);
    }

    @PostMapping(value="/changeOrder", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMINPHARMACY')")
    public ResponseEntity<Orderr> changeOrder(@RequestBody OrderDTO orderDTO) throws Exception {

        Orderr order = orderService.findById(orderDTO.getId());

        Set<Orderr_Medication> orderr_medications = new HashSet<>();
        Iterator<Integer> i1 = orderDTO.getMed_ids().iterator();
        Iterator<Integer> i2 = orderDTO.getAmounts().iterator();

        while(i1.hasNext() && i2.hasNext()) {
            Orderr_Medication om = new Orderr_Medication();
            om.setOrderr(order);
            om.setAmount(i2.next());
            Medication medication = medicationService.findById(i1.next());
            om.setMedication(medication);
            orderr_medications.add(om);
        }

        order = orderService.changeOrder(order, orderr_medications);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PostMapping(value="/orderByStatus", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMINPHARMACY')")
    public ResponseEntity<Set<OrderMedicationDTO>> getOrdersByPharmacyAndStatus(@RequestBody StatusDTO statusDTO){
        AdminPharmacy user = (AdminPharmacy) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Set<Orderr> orderrs = orderService.getOrdersByPharmacyAndStatus(user, statusDTO.getStatus());
        Set<OrderMedicationDTO> orderMedicationDTOS = new HashSet<>();

        for (Orderr orderr : orderrs) {
            OrderMedicationDTO om = new OrderMedicationDTO(orderr.getId(), orderr.getDateDeadline(), orderr.getAdminPharmacy());
            orderMedicationDTOS.add(om);
        }

        return new ResponseEntity<>(orderMedicationDTOS, HttpStatus.OK);
    }
}
