package com.example.springSecurity.Model.DTO.Register;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder //para construir los objetos
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestCliente {
    String rut;
    String password;
    String firstname;
    String lastname;
    String country;
}
