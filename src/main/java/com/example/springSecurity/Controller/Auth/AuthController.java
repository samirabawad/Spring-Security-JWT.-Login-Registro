package com.example.springSecurity.Controller.Auth;

import com.example.springSecurity.Model.DTO.Auth.*;
import com.example.springSecurity.Model.DTO.ResponseDTO;
import com.example.springSecurity.Service.Auth.AuthService;
import com.example.springSecurity.Service.Auth.PasswordRecoverService;
import com.example.springSecurity.Service.Auth.VerificationCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final PasswordRecoverService passwordRecoverService;
    private final VerificationCodeService verificationCodeService;

    @PostMapping(value ="login")
    public ResponseEntity<ResponseDTO> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping(value = "register/cliente")
    public ResponseEntity<ResponseDTO> registerCliente(@RequestBody RegisterRequestCliente request) {
        return ResponseEntity.ok(authService.registerCliente(request));
    }
    @PostMapping(value = "register/empresa")
    public ResponseEntity<ResponseDTO> registerEmpresa(@RequestBody RegisterRequestEmpresa request) {
        return ResponseEntity.ok(authService.registerEmpresa(request));
    }

    //Envia respuesta de se ha enviado un correo electronico, se debe redirigir en el front al endpoint /verificationCode
    @PostMapping(value = "recover")
    public ResponseEntity<ResponseDTO> recoverUser(@RequestBody RecoverRequest request) {
        return ResponseEntity.ok(passwordRecoverService.recoverUser(request));
    }

    @PostMapping(value="codeVerification")
    public ResponseEntity<ResponseDTO> codeVerification(
            @RequestBody CodeVerificationRequest codeRequest) {
        ResponseDTO response = verificationCodeService.verificationCode(codeRequest);
        return ResponseEntity.ok(response);
    }
}

