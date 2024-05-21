package com.example.springSecurity.JWT;

import com.example.springSecurity.Service.JWT.JwtAuthService;
import com.example.springSecurity.Service.JWT.JwtRecoverService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//crea filtros personalizados.
//OncePerRequest garantiza que se ejecute solo una vez el filtro por cada solicitud HTTP
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtAuthService jwtAuthService;
    private final UserDetailsService userDetailsService;
    private final JwtRecoverService jwtRecoverService;

    //SE VALIDA SI EL TOKEN ES VALIDO AUN.
    private boolean isTokenValid(String token, UserDetails userDetails, String requestURI) {
        if (requestURI.contains("/auth/recover/user")) {
            return jwtRecoverService.isRecoveryTokenValid(token, userDetails);
        } else {
            return jwtAuthService.isTokenValid(token, userDetails);
        }
    }

    //DEVUELVE EL TOKEN
    private String getTokenFromRequest(HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    //FUNCION OBTIENE EL TOKEN DEL REQUEST, UTILIZANDO FUNCIONES DE ARRIBA.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String token = getTokenFromRequest(request);
        final String rut;

        if(token == null) {
            filterChain.doFilter(request, response);
            return;
        }
        //1. INTENTA OBTENER RUT DEL TOKEN.
        rut = jwtAuthService.getUsernameFromToken(token);

        //2. SI NO LO OBTIENE, LO INTENTA OBTENER DE LA BASE DE DATOS.
        if(rut != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(rut);

            //SE OBTIENE EL TOKEN GENERADO (ACCESO O RECUPERACION) Y SE VALIDA.
            if(isTokenValid(token, userDetails, request.getRequestURI())){
                UsernamePasswordAuthenticationToken authToken= new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
