package com.example.springSecurity.Service.Auth;

import com.example.springSecurity.Model.DTO.Auth.AuthResponse;
import com.example.springSecurity.Model.DTO.Auth.LoginRequest;
import com.example.springSecurity.Model.DTO.Auth.RegisterRequest;
import com.example.springSecurity.Model.Entitys.User.Cliente;
import com.example.springSecurity.Model.Entitys.User.Role;
import com.example.springSecurity.Repositories.IClienteRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.patterns.IToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final IClienteRepository clienteRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails cliente = clienteRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.getToken(cliente);
        //patron de diseño build.
        return AuthResponse.builder()
                .token(token)
                .build();
    }
    public AuthResponse register(RegisterRequest request){
            //se utiliza patron de diseño builder, luego cambiarlo por dto.
            Cliente cliente = Cliente.builder()
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .firstname(request.getFirstname())
                    .lastname(request.getLastname())
                    .country(request.getCountry())
                    .role(Role.USER)
                    .build();
        clienteRepository.save(cliente);

        //Se trabaja con el patron de diseño builder para la construccion del obj AuthResponse.
        return AuthResponse.builder()
                .token(jwtService.getToken(cliente))
                .build();
    }
}
