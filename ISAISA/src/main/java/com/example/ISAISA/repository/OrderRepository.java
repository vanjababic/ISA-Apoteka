package com.example.ISAISA.repository;

import com.example.ISAISA.model.AdminPharmacy;
import com.example.ISAISA.model.Orderr;
import com.example.ISAISA.model.Orderr_Medication;
import org.hibernate.criterion.Order;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface OrderRepository extends JpaRepository<Orderr, Integer>{

    Set<Orderr> findByAdminPharmacy(AdminPharmacy adminPharmacy);

    Orderr findOneById(Integer id);

    Set<Orderr> findByStatusAdmin(String status);
}
