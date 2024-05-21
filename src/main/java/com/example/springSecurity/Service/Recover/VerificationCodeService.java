package com.example.springSecurity.Service.Recover;

import com.example.springSecurity.Model.DTO.Recover.CodeVerificationRequest;
import com.example.springSecurity.Model.DTO.Recover.CodeVerificationResponse;
import com.example.springSecurity.Model.DTO.Recover.VerificationCodeClass;
import com.example.springSecurity.Util.VerificationCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificationCodeService {
    private final VerificationCodeStorage verificationCodeStorage;

    // Verificación del código recibido del usuario
    public CodeVerificationResponse verificationCode(CodeVerificationRequest request) {
        String generatedCode = verificationCodeStorage.retrieveCode(request.getToken());
        if (generatedCode != null && generatedCode.equals(request.getCode())) {
            return new CodeVerificationResponse("Codigos calzan"); // código válido
        } else {
            return new CodeVerificationResponse("Codigos no calzan"); // código no válido
        }
    }

}
