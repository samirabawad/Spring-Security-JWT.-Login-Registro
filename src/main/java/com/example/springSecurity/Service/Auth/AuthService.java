package com.example.springSecurity.Service.Auth;

import com.example.springSecurity.Model.DTO.Auth.AuthResponse;
import com.example.springSecurity.Model.DTO.Auth.LoginRequest;
import com.example.springSecurity.Model.DTO.Register.RegisterRequestCliente;
import com.example.springSecurity.Model.DTO.Register.RegisterRequestEmpresa;
import com.example.springSecurity.Model.Entitys.User.Cliente;
import com.example.springSecurity.Model.Entitys.User.Empresa;
import com.example.springSecurity.Model.Entitys.User.Role;
import com.example.springSecurity.Repositories.RepositoryUsers.IAdminRepository;
import com.example.springSecurity.Repositories.RepositoryUsers.IClienteRepository;
import com.example.springSecurity.Repositories.RepositoryUsers.IEmpresaRepository;
import com.example.springSecurity.Service.JWT.JwtAuthService;
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
    private final IEmpresaRepository empresaRepository;
    private final IAdminRepository adminRepository;
    private final JwtAuthService jwtAuthService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;

    //LOGIN
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getRut(), request.getPassword()));
        // Busca el usuario en todos los repositorios
        UserDetails user =  customUserDetailsService.loadUserByUsername(request.getRut());

        String token = jwtAuthService.getToken(user); //Se genera token si existe usuario.
        //patron de diseño build.
        return AuthResponse.builder()
                .token(token)
                .build();
        }

    //REGISTROS
    //Registro para Cliente.
    public AuthResponse registerCliente(RegisterRequestCliente request){
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
                .token(jwtAuthService.getToken(cliente)) //Se genera token si existe usuario.
                .build();
    }

    //Registro para Empresa.
    public AuthResponse registerEmpresa(RegisterRequestEmpresa request) {
        Empresa empresa = Empresa.builder()
                .rut(request.getRut())
                .password(passwordEncoder.encode(request.getPassword()))
                .nombre(request.getNombre())
                .giro(request.getGiro())
                .email(request.getEmail())
                .celular(request.getCelular())
                .role(Role.EMPRESA)
                .build();
        empresaRepository.save(empresa);

        //Se trabaja con el patron de diseño builder para la construccion del obj AuthResponse.
        return AuthResponse.builder()
                .token(jwtAuthService.getToken(empresa)) //Se genera token si existe usuario.
                .build();
    }
}



