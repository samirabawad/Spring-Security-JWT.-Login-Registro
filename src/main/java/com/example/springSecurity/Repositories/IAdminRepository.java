package com.example.springSecurity.Repositories;

import com.example.springSecurity.Model.Entitys.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IAdminRepository extends JpaRepository<Admin, Integer> {
    Optional<Admin> findByRut(String rut);
}
