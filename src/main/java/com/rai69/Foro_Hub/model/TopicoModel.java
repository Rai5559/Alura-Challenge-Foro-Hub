package com.rai69.Foro_Hub.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Topico")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopicoModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false, length = 200)
    private String titulo;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String mensaje;
    
    @Column(name = "fechaCreacion")
    private LocalDateTime fechaCreacion;
    
    @Column(length = 50)
    private String status;
    
    @ManyToOne
    @JoinColumn(name = "autor")
    private UsuarioModel autor;
    
    @ManyToOne
    @JoinColumn(name = "curso")
    private CursoModel curso;
    
    @OneToMany(mappedBy = "topico", cascade = CascadeType.ALL)
    private List<RespuestaModel> respuestas;
    
    @PrePersist
    protected void onCreate() {
        if (fechaCreacion == null) {
            fechaCreacion = LocalDateTime.now();
        }
    }
}
