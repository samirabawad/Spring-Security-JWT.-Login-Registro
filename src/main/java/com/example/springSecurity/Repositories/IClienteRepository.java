package com.example.springSecurity.Repositories;

import com.example.springSecurity.Model.Entitys.User.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface IClienteRepository extends JpaRepository<Cliente, Integer> {
    Optional<Cliente> findByRut(String rut);
}
