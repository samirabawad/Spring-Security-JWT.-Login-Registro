package com.example.springSecurity.Repositories.RepositoryUsers;

import com.example.springSecurity.Model.Entitys.User.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IEmpresaRepository extends JpaRepository<Empresa, Integer> {
    Optional<Empresa> findByRut(String rut);
}