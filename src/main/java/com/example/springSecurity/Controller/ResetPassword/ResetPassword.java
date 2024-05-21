package com.example.springSecurity.Controller.ResetPassword;

import com.example.springSecurity.Model.DTO.Recover.CodeVerificationRequest;
import com.example.springSecurity.Model.DTO.Recover.CodeVerificationResponse;
import com.example.springSecurity.Model.DTO.Recover.RecoverRequest;
import com.example.springSecurity.Model.DTO.Recover.RecoverResponse;
import com.example.springSecurity.Service.Recover.PasswordResetService;
import com.example.springSecurity.Service.Recover.VerificationCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reset")
@RequiredArgsConstructor
public class ResetPassword {
    private final VerificationCodeService verificationCodeService;

    //Es redirigido aca solo si se le entrego el token de ReseteoPassword (ver config es securityconfig)
    //@PostMapping(value = "codeVerification")
    //public ResponseEntity<CodeVerificationResponse> verificationCode(@RequestBody CodeVerificationRequest code){
      //  return ResponseEntity.ok(verificationCodeService.verificationCode(code));
    //}

    @PostMapping(value = "codeVerification")
    public String welcome() {
        return "recuperando cod verificacion  desde secure endpoint";
    }
}

