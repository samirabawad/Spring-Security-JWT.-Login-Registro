package com.example.springSecurity.Controller.ResetPassword;

import com.example.springSecurity.Model.DTO.Recover.*;
import com.example.springSecurity.Service.Recover.PasswordResetService;
import com.example.springSecurity.Service.Recover.VerificationCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reset")
@RequiredArgsConstructor
public class ResetPassword {
    private final VerificationCodeService verificationCodeService;
    private final PasswordResetService passwordResetService;

    @PostMapping(value = "codeVerificationExample")
    public String welcome() {

        return "recuperando cod verificacion  desde secure endpoint";
    }


    //@PostMapping(value = "resetPassword")
    //public String resetPassword(
      //      @RequestBody String newpassword) {
        //return "contrasena modificada como: " + newpassword;
    //}
}
        //Boolean resetpasswordvar = verificationCodeService.isResetPassword(token);
        // Verificar si resetPassword es true en el token
        //if (!resetpasswordvar) {
          //  return "resetPassword fuera en el if: "+resetpasswordvar;
            // Si resetPassword es false, no permitir el acceso a resetPassword
            //return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // O cualquier otro código de estado que desees
        //}
        //return "resetPassword fuera del if: +"+resetpasswordvar;
        //NewPasswordResponse response = PasswordResetService.resetpassword(newpassword);
        // return ResponseEntity.ok(response);


