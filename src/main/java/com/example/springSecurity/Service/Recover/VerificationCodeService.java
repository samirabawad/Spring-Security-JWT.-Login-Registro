package com.example.springSecurity.Service.Recover;

import com.example.springSecurity.Model.DTO.Recover.CodeVerificationRequest;
import com.example.springSecurity.Model.DTO.Recover.CodeVerificationResponse;
import com.example.springSecurity.Model.DTO.Recover.VerificationCodeClass;
import com.example.springSecurity.Repositories.RepositoryUsers.VerificationCodeStorage;
import com.example.springSecurity.Util.VerificationCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificationCodeService {
    private final VerificationCodeStorage verificationCodeStorage;

    // Verificación del código recibido del usuario
    public CodeVerificationResponse verificationCode(CodeVerificationRequest codeRequest) {
        //no me llega el codigo generado, pero si entra al endpoint
        System.out.println("Generated code is desde Codeverification");

        //funcion obtiene token
        String token = codeRequest.getToken();
        System.out.println("Generated token is " +token);
        Claims claims = Jwts.parserBuilder().setSigningKey("78393920323332376147466A324D52316661636B4668475162636A764D464541").build().parseClaimsJws(token).getBody();
        System.out.println(claims.getSubject());
        String tokenType = (String) claims.get("tipo");
        System.out.println(tokenType);
        String code = (String) claims.get("code");
        System.out.println("codesent: "+code);


        if (code != null && code.equals(codeRequest.getCode())) {
            return CodeVerificationResponse.builder()
                    .mensaje("Códigos calzan")
                    .build(); // código válido
        } else {
            return CodeVerificationResponse.builder()
                    .mensaje("Códigos no calzan")
                    .build(); // código no válido
        }
    }
}
