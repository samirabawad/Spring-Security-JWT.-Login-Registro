package com.example.springSecurity.Model.DTO.Recover;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder //para construir los objetos
@AllArgsConstructor
@NoArgsConstructor
public class NewPasswordRequest {
    private String token;
    private String password;
    private String confirmPassword;
}