package com.example.springSecurity.Service.Recover;

import com.example.springSecurity.Model.DTO.Recover.CodeVerificationResponse;
import com.example.springSecurity.Util.VerificationCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


public class VerificationCodeService {
    private final VerificationCode verificationCode;

    public void sendPasswordResetEmail(String rut, String resetToken) {
        String cod = sendMesagge(resetToken);
        //este servicio se deberia llamar codeVerification.
        //luego enviar a servicio de comparacion de codigos.
        }

        //enviar un sms o correo con el cod y el token.
        //notificar al cliente que se ha enviado un codigo de ver. y que lo ingrese en este boton
        //boton lo envia a la url /resetPassword
        //en este url, verificar que el cod enviado por post desde el cliente sea el mismo que el generado.
        //si es el mismo, enviarlo a otro url /changePassword, donde se recibira la nueva clave.

        //retrun se ha enviado un mensaje
    }
    public String sendMesagge(String resetToken){
        String cod =verificationCode.verificationCode();
        sendCodeVerification(cod, resetToken);
        return cod;
    }
    public CodeVerificationResponse verifyCodeVerification(String code) {
        if(verificationCode.verificationCode().equals(code)) {
            return new CodeVerificationResponse();//codigo valido, se envia a url de newPassword
        }else{
            return new CodeVerificationResponse();//codigo no valido
        }
    }
    public String sendCodeVerification(String cod, String resetToken){
        return "codigo enviado";
    }

}
