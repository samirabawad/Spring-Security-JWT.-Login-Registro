package com.example.springSecurity.Service.Auth;

import com.example.springSecurity.Model.DTO.Auth.RecoverRequest;
import com.example.springSecurity.Model.DTO.ResponseDTO;
import com.example.springSecurity.Repositories.RepositoryUsers.VerificationCodeStorage;
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
        String token = jwtrecoverService.getRecoveryToken(user); // Generate recovery token if user exists

        // Store the token with the username as the key
        System.out.println("Storing Token: " + token + " for User: " + user.getUsername());
        verificationCodeStorage.storeCode(user.getUsername(), token);
        return ResponseDTO.builder()
                .mensaje("Se ha enviado un correo electrónico")
                .build();
    }
}
