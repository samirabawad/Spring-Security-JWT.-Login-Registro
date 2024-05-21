package com.example.springSecurity.Service.Recover;

import com.example.springSecurity.Model.DTO.Recover.RecoverRequest;
import com.example.springSecurity.Model.DTO.Recover.RecoverResponse;
import com.example.springSecurity.Repositories.RepositoryUsers.IAdminRepository;
import com.example.springSecurity.Repositories.RepositoryUsers.IClienteRepository;
import com.example.springSecurity.Repositories.RepositoryUsers.IEmpresaRepository;
import com.example.springSecurity.Service.Auth.CustomUserDetailsService;
import com.example.springSecurity.Service.JWT.JwtRecoverService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordRecoverService {
    private final IClienteRepository clienteRepository;
    private final IEmpresaRepository empresaRepository;
    private final IAdminRepository adminRepository;
    private final JwtRecoverService jwtrecoverService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordResetService passwordResetService;


    //RECOVERS por  RUT.
    public RecoverResponse recoverUser(RecoverRequest request) {
        //authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getRut(), request.getPassword()));
        UserDetails user =  customUserDetailsService.loadUserByUsername(request.getRut());
        String token = jwtrecoverService.getRecoveryToken(user); //Se genera token Recovery si existe usuario.

        //SE ENVIA RUT Y TOKEN AL SERVICIO DE REESTABLECIMIENTO.
        passwordResetService.sendPasswordResetEmail(user.getUsername(), token);

        return RecoverResponse.builder()
                .token(token)
                .build();
    }
}
