package com.example.springSecurity.Model.DTO.Recover;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder //para construir los objetos
@AllArgsConstructor
@NoArgsConstructor
public class RecoverRequest {
    String rut;
}
