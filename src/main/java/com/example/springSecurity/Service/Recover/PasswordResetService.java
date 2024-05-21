package com.example.springSecurity.Service.Recover;

import com.example.springSecurity.Model.DTO.Recover.CodeVerificationRequest;
import com.example.springSecurity.Model.DTO.Recover.CodeVerificationResponse;
import com.example.springSecurity.Model.DTO.Recover.NewPasswordRequest;
import com.example.springSecurity.Model.DTO.Recover.NewPasswordResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordResetService {
    public NewPasswordResponse resetPassword(NewPasswordRequest newpassword) {
        //funcion obtiene token
        String token = newpassword.getToken();
        System.out.println("Generated token is " + token);
        Claims claims = Jwts.parserBuilder().setSigningKey("78393920323332376147466A324D52316661636B4668475162636A764D464541").build().parseClaimsJws(token).getBody();
        System.out.println(claims.getSubject());
        String tokenType = (String) claims.get("tipo");
        System.out.println(tokenType);
        String code = (String) claims.get("code");
        System.out.println("codesent: " + code);
        Boolean resetpassword = (Boolean) claims.get("resetpassword");
        System.out.println("resetpassword:" + resetpassword);


        return new NewPasswordResponse().builder()
                .mensaje("token: "+token+"resetpassword: "+resetpassword+"")
                .build();
    }

}
