package com.example.ISAISA.repository;

import com.example.ISAISA.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    Optional<User> findById(Integer id);

}
