package com.example.springSecurity.Service.Recover;

import com.example.springSecurity.Model.DTO.Recover.CodeVerificationRequest;
import com.example.springSecurity.Model.DTO.Recover.CodeVerificationResponse;
import com.example.springSecurity.Model.DTO.Recover.RecoverResponse;
import com.example.springSecurity.Model.DTO.Recover.VerificationCodeClass;
import com.example.springSecurity.Repositories.RepositoryUsers.VerificationCodeStorage;
import com.example.springSecurity.Util.VerificationCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class VerificationCodeService {
    private final VerificationCodeStorage verificationCodeStorage;

    public CodeVerificationResponse verificationCode(CodeVerificationRequest codeRequest) {
        // Log the input request
        System.out.println("Received codeRequest: " + codeRequest);

        // Log all keys in storage for debugging
        System.out.println("Current storage keys: " + verificationCodeStorage.getStorage().keySet());

        // Retrieve stored token using the user's identifier
        String storedToken = verificationCodeStorage.getStorage().get(codeRequest.getRut());

        // Log the retrieved stored token
        System.out.println("Stored Token for rut " + codeRequest.getRut() + ": " + storedToken);

        Claims claims = Jwts.parserBuilder().setSigningKey("78393920323332376147466A324D52316661636B4668475162636A764D464541").build().parseClaimsJws(storedToken).getBody();

        String codeStored = (String) claims.get("code");
        System.out.println("codesent: " + codeStored);

        if (codeRequest.getCode().equals(codeStored)) {
            return new CodeVerificationResponse("Code verified successfully", storedToken);
        } else {
            return new CodeVerificationResponse("Invalid code", null);
        }
    }
}






       /* private final RecoverResponse recoverResponse;

        if(codeRequest.equals(recoverResponse.getToken())){
            return CodeVerificationResponse.builder()
                    .mensaje("Códigos calzan")
                    .newToken(newToken) // se le asigna el token al usuario para entrar al private
                    .build(); // código válido
        }else{
            return CodeVerificationResponse.builder()
                    .mensaje("Códigos no calzan")
                    .newToken(newToken) // token nulo al usuario para que no pueda entrar al priavte.
                    .build(); // código válido
        }


        String token = codeRequest.getToken();
        System.out.println("Generated token is " + token);
        Claims claims = Jwts.parserBuilder().setSigningKey("78393920323332376147466A324D52316661636B4668475162636A764D464541").build().parseClaimsJws(token).getBody();
        System.out.println(claims.getSubject());
        String tokenType = (String) claims.get("tipo");
        System.out.println(tokenType);
        String code = (String) claims.get("code");
        System.out.println("codesent: " + code);
        Boolean resetpassword = (Boolean) claims.get("resetpassword");
        System.out.println("resetpassword:" + resetpassword);


        if (code != null && code.equals(codeRequest.getCode())) {

            String newToken = JwtTokenUtil.generateToken(claims.getSubject(), true);

            // Devuelve el nuevo token en la respuesta
            return CodeVerificationResponse.builder()
                    .mensaje("Códigos calzan")
                    .newToken(newToken) // Agrega el nuevo token a la respuesta
                    .build(); // código válido
        } else {
            return CodeVerificationResponse.builder()
                    .mensaje("Códigos no calzan")
                    .build(); // código no válido
        }
    }

    public class JwtTokenUtil {
        private static final String SECRET_KEY = "78393920323332376147466A324D52316661636B4668475162636A764D464541";
        private static final long EXPIRATION_TIME = 864_000_000; // 10 días en milisegundos

        public static String generateToken(String subject, boolean resetpassword) {
            return Jwts.builder()
                    .setSubject(subject)
                    .claim("resetpassword", resetpassword)
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                    .compact();
        }
    }
}*/
