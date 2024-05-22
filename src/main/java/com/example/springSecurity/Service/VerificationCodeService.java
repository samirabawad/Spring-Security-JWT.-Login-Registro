package com.example.springSecurity.Service;

import com.example.springSecurity.Model.DTO.Auth.CodeVerificationRequest;
import com.example.springSecurity.Model.DTO.ResponseDTO;
import com.example.springSecurity.Repositories.VerificationCodeStorage;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificationCodeService {
    private final VerificationCodeStorage verificationCodeStorage;

    public ResponseDTO verificationCode(CodeVerificationRequest codeRequest) {
        // Log the input request
        System.out.println("Received codeRequest: " + codeRequest);

        // Log all keys in storage for debugging
        System.out.println("Current storage keys: " + verificationCodeStorage.getStorage().keySet());

        // Retrieve stored token using the user's identifier
        String storedToken = verificationCodeStorage.getStorage().get(codeRequest.getRut());

        // Log the retrieved stored token
        System.out.println("Stored Token for rut " + codeRequest.getRut() + ": " + storedToken);

        Claims claims = Jwts.parserBuilder().setSigningKey("78393920323332376147466A324D52316661636B4668475162636A764D464541").build().parseClaimsJws(storedToken).getBody();

        String codeStored = (String) claims.get("code");
        System.out.println("codesent: " + codeStored);

        if (codeRequest.getCode().equals(codeStored)) {
            return new ResponseDTO("Codigo de verificación correcto. Por favor ingrese nueva contraseña en el boton de abajo");
        } else {
            return new ResponseDTO("Codigo invalido");
        }
    }
}