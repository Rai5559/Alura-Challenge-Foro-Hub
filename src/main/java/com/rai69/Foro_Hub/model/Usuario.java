package com.rai69.Foro_Hub.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    
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
    private Perfil perfiles;
    
    // Relaciones inversas (opcionales)
    @OneToMany(mappedBy = "autor")
    private List<Topico> topicos;
    
    @OneToMany(mappedBy = "autor")
    private List<Respuesta> respuestas;
}
