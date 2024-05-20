package com.example.springSecurity.Service.Auth;

import com.example.springSecurity.Model.DTO.Auth.AuthResponse;
import com.example.springSecurity.Model.DTO.Auth.LoginRequest;
import com.example.springSecurity.Model.DTO.Register.RegisterRequestCliente;
import com.example.springSecurity.Model.Entitys.User.Cliente;
import com.example.springSecurity.Model.Entitys.User.Role;
import com.example.springSecurity.Repositories.IClienteRepository;
import lombok.RequiredArgsConstructor;
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
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getRut(), request.getPassword()));
        UserDetails cliente = clienteRepository.findByRut(request.getRut()).orElseThrow();
        String token = jwtService.getToken(cliente);
        //patron de diseño build.
        return AuthResponse.builder()
                .token(token)
                .build();
    }
    public AuthResponse register(RegisterRequestCliente request){
            //se utiliza patron de diseño builder, luego cambiarlo por dto.
            Cliente cliente = Cliente.builder()
                    .rut(request.getRut())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .firstname(request.getFirstname())
                    .lastname(request.getLastname())
                    .email(request.getEmail())
                    .celular(request.getCelular())
                    .role(Role.USER)
                    .build();
        clienteRepository.save(cliente);

        //Se trabaja con el patron de diseño builder para la construccion del obj AuthResponse.
        return AuthResponse.builder()
                .token(jwtService.getToken(cliente))
                .build();
    }
}
