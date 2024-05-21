package com.example.springSecurity.Controller.Auth;

import com.example.springSecurity.Model.DTO.Auth.AuthResponse;
import com.example.springSecurity.Model.DTO.Auth.LoginRequest;
import com.example.springSecurity.Model.DTO.Recover.*;
import com.example.springSecurity.Model.DTO.Register.RegisterRequestCliente;
import com.example.springSecurity.Model.DTO.Register.RegisterRequestEmpresa;
import com.example.springSecurity.Service.Auth.AuthService;
import com.example.springSecurity.Service.Recover.PasswordRecoverService;
import com.example.springSecurity.Service.Recover.PasswordResetService;
import com.example.springSecurity.Service.Recover.VerificationCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final PasswordRecoverService passwordRecoverService;
    private final PasswordResetService passwordResetService;
    private final VerificationCodeService verificationCodeService;

    @PostMapping(value ="login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping(value = "register/cliente")
    public ResponseEntity<AuthResponse> registerCliente(@RequestBody RegisterRequestCliente request) {
        return ResponseEntity.ok(authService.registerCliente(request));
    }
    @PostMapping(value = "register/empresa")
    public ResponseEntity<AuthResponse> registerEmpresa(@RequestBody RegisterRequestEmpresa request) {
        return ResponseEntity.ok(authService.registerEmpresa(request));
    }

    //Envia respuesta de se ha enviado un correo electronico, se debe redirigir en el front al endpoint /verificationCode
    @PostMapping(value = "recover")
    public ResponseEntity<RecoverResponse> recoverUser(@RequestBody RecoverRequest request) {
        return ResponseEntity.ok(passwordRecoverService.recoverUser(request));
    }
    @PostMapping(value="codeVerification")
    public ResponseEntity<CodeVerificationResponse> codeVerification(
            @RequestBody CodeVerificationRequest codeRequest) {
        CodeVerificationResponse response = verificationCodeService.verificationCode(codeRequest);
        return ResponseEntity.ok(response);
    }
}

