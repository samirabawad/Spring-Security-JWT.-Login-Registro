package com.example.springSecurity.Config;

import com.example.springSecurity.JWT.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//Autenticacion basada en JWT.

//Aca se crean los objetos a requerir en la app
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authProvider;

    //metodo que tiene las cadenas de filtros de la app.
    //metodo restringe el acceso a las rutas.
    //retorna un http siempre que pase por los filtros.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        return http
                //dehabilita la proteccion csrf de POST
                .csrf(csrf-> csrf.disable())
                //primer filtro: rutas privadas y publicas
                .authorizeHttpRequests(authRequest ->
                        authRequest
                                .requestMatchers("/auth/**").permitAll()
                                .anyRequest().authenticated()
                )
                //se inhabilitan las sesiones, porque se trabajara con JWT para autenticar.
                .sessionManagement(sessionManager->
                        sessionManager
                        //se especifica la politica de creacion de sesiones a usar, es decir, que no se utilicen.
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authProvider)
                //Se Añade el filtro de JWT propio.
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}

