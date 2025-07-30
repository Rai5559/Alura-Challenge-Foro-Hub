package com.rai69.Foro_Hub.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "Usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioModel implements UserDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false, length = 100)
    private String nombre;
    
    @Column(name = "correoElectronico", nullable = false, unique = true, length = 150)
    private String correoElectronico;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String contrasena;
    
    @ManyToOne
    @JoinColumn(name = "perfiles")
    private PerfilModel perfiles;
    
    @OneToMany(mappedBy = "autor")
    private List<TopicoModel> topicos;
    
    @OneToMany(mappedBy = "autor")
    private List<RespuestaModel> respuestas;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + perfiles.getNombre().toUpperCase()));
    }

    @Override
    public String getPassword() {
        return contrasena;
    }

    @Override
    public String getUsername() {
        return correoElectronico;
    }

    @Override
    public boolean isAccountNonExpired() {
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
