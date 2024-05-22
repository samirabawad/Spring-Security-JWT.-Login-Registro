package com.example.springSecurity.Service;

import com.example.springSecurity.Model.DTO.Auth.RecoverRequest;
import com.example.springSecurity.Model.DTO.ResponseDTO;
import com.example.springSecurity.Repositories.VerificationCodeStorage;
import com.example.springSecurity.Util.VerificationCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordRecoverService {
    private final JwtRecoverService jwtrecoverService;
    private final CustomUserDetailsService customUserDetailsService;
    private final VerificationCode verificationCode;
    private final VerificationCodeStorage verificationCodeStorage;

    //RECOVERS por  RUT.
    public ResponseDTO recoverUser(RecoverRequest request) {
        UserDetails user = customUserDetailsService.loadUserByUsername(request.getRut());
        //hacer un if-else sobre si el usuario no existe, retornar una respuesta de que:
        //"usuario no existe."
        String token = jwtrecoverService.getRecoveryToken(user); // Generate recovery token if user exists

        // Store the token with the username as the key
        System.out.println("Storing Token: " + token + " for User: " + user.getUsername());
        //se guarda token y usuario en un localStorage. Seria bueno guardar solo el user con codigo de verificacion, seria mas logico.
        verificationCodeStorage.storeCode(user.getUsername(), token);
        return ResponseDTO.builder()
                .mensaje("Se ha enviado un correo electrónico")
                .build();
    }
}
