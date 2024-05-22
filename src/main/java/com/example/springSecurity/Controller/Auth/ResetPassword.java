package com.example.springSecurity.Controller.Auth;

import com.example.springSecurity.Service.Auth.VerificationCodeService;
import lombok.RequiredArgsConstructor;
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


