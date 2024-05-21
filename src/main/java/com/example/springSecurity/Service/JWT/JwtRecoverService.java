package com.example.springSecurity.Service.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtRecoverService {
    private static final String RECOVERY_SECRET_KEY = "78393920323332376147466A324D52316661636B4668475162636A764D464541";


    //CONSTRUCCION TOKEN RECOVERY
    public String getRecoveryToken(UserDetails cliente) {
        return getRecoveryToken(new HashMap<>(), cliente);
    }
    public String getRecoveryToken(Map<String, Object> extraclaims, UserDetails cliente) {
        return Jwts
                .builder()
                .setClaims(extraclaims)
                .setSubject(cliente.getUsername()) //RECORDAR QUE ESTO LO SOBREESCRIBIMOS PARA QUE OBTENGA EL RUT
                .claim("tipo", "recuperacion") // Agregar campo tipo
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) // Token válido por 30 minutos
                .signWith(getRecoveryKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    private Key getRecoveryKey() {
        byte[] keyBytes = Decoders.BASE64.decode(RECOVERY_SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public boolean isRecoveryTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isRecoveryTokenExpired(token));
    }
    public <T> T getRecoveryClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private boolean isRecoveryTokenExpired(String token) {
        return getExpirationDate(token).before(new Date());
    }
    public String getUsernameFromToken(String token) {
        return getRecoveryClaim(token, Claims::getSubject);
    }
    //obtiene todos los claims del token
    private Claims getAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getRecoveryKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    private Date getExpirationDate(String token) {
        return getRecoveryClaim(token, Claims::getExpiration);
    }


}
