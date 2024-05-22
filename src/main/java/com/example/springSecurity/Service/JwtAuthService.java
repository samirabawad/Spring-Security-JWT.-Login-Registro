package com.example.springSecurity.Service;

import io.jsonwebtoken.Claims;
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

import io.jsonwebtoken.Jwts;

@Service
public class JwtAuthService {

    private static final String SECRET_KEY = "586E3272357538782F413F4428472B4B6250655368566B597033733676397924";

    //CONSTRUCCION TOKEN ACCESO
    public String getToken(UserDetails cliente) {
        return getToken(new HashMap<>(), cliente);
    }
    public String getToken(Map<String, Object> extraclaims, UserDetails cliente) {
        return Jwts
                .builder()
                .setClaims(extraclaims)
                .setSubject(cliente.getUsername())//RECORDAR QUE ESTO LO SOBREESCRIBIMOS PARA QUE OBTENGA EL RUT
                .claim("tipo", "acceso") // Agregar campo tipo
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        //crea una instancia de la secret key
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    //metodo generico para generar claims
    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }
    //ve si el token ha expirado
    private boolean isTokenExpired(String token) {
        return getExpirationDate(token).before(new Date());
    }

    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    //obtiene todos los claims del token
    private Claims getAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

    }
    private Date getExpirationDate(String token) {
        return getClaim(token, Claims::getExpiration);
    }
}
