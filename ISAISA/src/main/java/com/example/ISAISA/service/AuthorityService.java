package com.example.ISAISA.service;

import com.example.ISAISA.model.Authority;

import java.util.List;

public interface AuthorityService {
    List<Authority> findById(Integer id);
    List<Authority> findByname(String name);
}
