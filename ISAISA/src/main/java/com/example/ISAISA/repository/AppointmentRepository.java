package com.example.ISAISA.repository;

import com.example.ISAISA.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
        Set<Appointment> findAllByDermatologist(Dermatologist dermatologist);
        Appointment findOneById(Integer id);
        Set<Appointment> findAllByPatient(Patient patient);
        List<Appointment> findByPatient(Patient patient);
        Set<Appointment> findAllByDermatologistAndBeginofappointmentAfter(Dermatologist dermatologist, LocalDateTime localDate);
        Set<Appointment> findAllByPatientNull();
        Set<Appointment> findByPatientOrderByPrice(Patient patient);
}
