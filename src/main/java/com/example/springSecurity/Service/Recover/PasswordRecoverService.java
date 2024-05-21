package com.example.springSecurity.Service.Recover;

import com.example.springSecurity.Model.DTO.Recover.RecoverRequest;
import com.example.springSecurity.Model.DTO.Recover.RecoverResponse;
import com.example.springSecurity.Model.DTO.Recover.VerificationCodeClass;
import com.example.springSecurity.Repositories.RepositoryUsers.IAdminRepository;
import com.example.springSecurity.Repositories.RepositoryUsers.IClienteRepository;
import com.example.springSecurity.Repositories.RepositoryUsers.IEmpresaRepository;
import com.example.springSecurity.Repositories.RepositoryUsers.VerificationCodeStorage;
import com.example.springSecurity.Service.Auth.CustomUserDetailsService;
import com.example.springSecurity.Service.JWT.JwtRecoverService;
import com.example.springSecurity.Util.VerificationCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordRecoverService {
    private final JwtRecoverService jwtrecoverService;
    private final CustomUserDetailsService customUserDetailsService;
    private final VerificationCode verificationCode;
    private final VerificationCodeStorage verificationCodeStorage;



    //RECOVERS por  RUT.
    public RecoverResponse recoverUser(RecoverRequest request) {
        UserDetails user = customUserDetailsService.loadUserByUsername(request.getRut());
        String token = jwtrecoverService.getRecoveryToken(user); // Generate recovery token if user exists

        // Store the token with the username as the key
        System.out.println("Storing Token: " + token + " for User: " + user.getUsername());
        verificationCodeStorage.storeCode(user.getUsername(), token);
        return RecoverResponse.builder()
                .token(token)
                .message("Se ha enviado un correo electrónico")
                .build();
    }


    public String sendPasswordResetEmail(String token, String code){

        return "codigo enviado"+code;
    }
}
