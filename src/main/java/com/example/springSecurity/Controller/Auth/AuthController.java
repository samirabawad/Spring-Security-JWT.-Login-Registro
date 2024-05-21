package com.example.springSecurity.Controller.Auth;

import com.example.springSecurity.Model.DTO.Auth.AuthResponse;
import com.example.springSecurity.Model.DTO.Auth.LoginRequest;
import com.example.springSecurity.Model.DTO.Recover.RecoverRequest;
import com.example.springSecurity.Model.DTO.Recover.RecoverResponse;
import com.example.springSecurity.Model.DTO.Recover.ResetResponse;
import com.example.springSecurity.Model.DTO.Register.RegisterRequestCliente;
import com.example.springSecurity.Model.DTO.Register.RegisterRequestEmpresa;
import com.example.springSecurity.Service.Auth.AuthService;
import com.example.springSecurity.Service.Recover.PasswordRecoverService;
import com.example.springSecurity.Service.Recover.PasswordResetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final PasswordRecoverService passwordRecoverService;
    private final PasswordResetService passwordResetService;

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

    //envia de respuesta el token de recuperacion
    @PostMapping(value = "recover")
    public ResponseEntity<RecoverResponse> recoverUser(@RequestBody RecoverRequest request) {
        return ResponseEntity.ok(passwordRecoverService.recoverUser(request));
    }
    //envia de respuesta el token de recuperacion
    //@PostMapping(value = "reset/password")
    //public ResponseEntity<ResetResponse> resetPassword() {
      //  return ResponseEntity.ok(passwordResetService.resetPassword());
    //}
}

