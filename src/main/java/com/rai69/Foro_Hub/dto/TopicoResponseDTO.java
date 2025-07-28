package com.rai69.Foro_Hub.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicoResponseDTO {
    
    private Integer id;
    private String titulo;
    private String mensaje;
    private LocalDateTime fechaCreacion;
    private String status;
    private AutorDTO autor;
    private CursoDTO curso;
    private Integer totalRespuestas;
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AutorDTO {
        private Integer id;
        private String nombre;
    }
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CursoDTO {
        private Integer id;
        private String nombre;
        private String categoria;
    }
}
