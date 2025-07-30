package com.rai69.Foro_Hub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TopicoRequestDTO {
    
    @NotBlank(message = "El título es obligatorio")
    @Size(max = 200, message = "El título no puede exceder 200 caracteres")
    private String titulo;
    
    @NotBlank(message = "El mensaje es obligatorio")
    @Size(min = 10, message = "El mensaje debe tener al menos 10 caracteres")
    private String mensaje;
    
    @NotNull(message = "El usuario es obligatorio")
    private Integer usuarioId;
    
    @NotBlank(message = "El nombre del curso es obligatorio")
    private String cursoNombre;

    private String categoria; 
    
    private String status;
}
