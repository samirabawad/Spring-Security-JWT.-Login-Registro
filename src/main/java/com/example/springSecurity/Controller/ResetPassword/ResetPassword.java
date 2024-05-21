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

    @PostMapping(value = "codeVerificationExample")
    public String welcome() {

        return "recuperando cod verificacion  desde secure endpoint";
    }

    @PostMapping(value="codeVerification")
    public ResponseEntity<CodeVerificationResponse> codeVerification(
                @RequestBody CodeVerificationRequest codeRequest,
                @RequestHeader("Authorization") String authHeader) {
            System.out.println("Request received with token: " + authHeader); // Log para depuración
            String token = authHeader.substring(7); // Remueve "Bearer "
            codeRequest.setToken(token);

            CodeVerificationResponse response = verificationCodeService.verificationCode(codeRequest);
            return ResponseEntity.ok(response);
        }
    }

