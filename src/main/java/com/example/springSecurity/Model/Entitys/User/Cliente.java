package com.example.springSecurity.Model.Entitys.User;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Builder //para construir los objetos
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cliente", uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})})
//implementa userDetails para trabajar con la autentificación
public class Cliente implements UserDetails {
    @Id
    @GeneratedValue
    Integer id;
    @Column(nullable = false)
    String username;
    String password;
    @Column(nullable = false)
    String firstname;
    @Column(nullable = false)
    String lastname;
    String country;
    @Enumerated(EnumType.STRING)
    Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //retorna una lista que contiene un unico obj que representa la autoridad otorgada al usuario atenticado.
        //por esto se especifica el campo role
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        //el token JWT indicará cuando expire
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

