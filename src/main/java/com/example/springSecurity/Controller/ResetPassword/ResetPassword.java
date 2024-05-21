package com.example.springSecurity.Controller.ResetPassword;

import com.example.springSecurity.Model.DTO.Recover.RecoverRequest;
import com.example.springSecurity.Model.DTO.Recover.RecoverResponse;
import com.example.springSecurity.Service.Recover.PasswordResetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reset")
@RequiredArgsConstructor
public class ResetPassword {
    private final PasswordResetService passwordResetService;

    //@PostMapping(value = "codeVerification")
    //public ResponseEntity<CodeVerificationResponse> codeVerification(@RequestParam("code") CodeVerificationRequest code) {
        //return ResponseEntity.ok(Veri.)
    //}

}
