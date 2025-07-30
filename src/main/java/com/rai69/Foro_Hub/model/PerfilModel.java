package com.rai69.Foro_Hub.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Perfil")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PerfilModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false, length = 100)
    private String nombre;
    
    @OneToMany(mappedBy = "perfiles")
    private List<UsuarioModel> usuarios;
}
