package com.rai69.Foro_Hub.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Respuesta")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Respuesta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String mensaje;
    
    @ManyToOne
    @JoinColumn(name = "topico")
    private Topico topico;
    
    @Column(name = "fechaCreacion")
    private LocalDateTime fechaCreacion;
    
    @ManyToOne
    @JoinColumn(name = "autor")
    private Usuario autor;
    
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean solucion = false;
    
    @PrePersist
    protected void onCreate() {
        if (fechaCreacion == null) {
            fechaCreacion = LocalDateTime.now();
        }
    }
}
