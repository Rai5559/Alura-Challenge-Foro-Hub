package com.rai69.Foro_Hub.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Curso")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Curso {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false, length = 100)
    private String nombre;
    
    @Column(length = 100)
    private String categoria;
    
    // Relación inversa (opcional)
    @OneToMany(mappedBy = "curso")
    private List<Topico> topicos;
}
