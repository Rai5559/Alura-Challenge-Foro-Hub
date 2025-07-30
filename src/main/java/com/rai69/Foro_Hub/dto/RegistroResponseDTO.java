package com.rai69.Foro_Hub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegistroResponseDTO {
    private Integer id;
    private String nombre;
    private String correoElectronico;
    private String perfil;
    private String mensaje;
    
    public RegistroResponseDTO(Integer id, String nombre, String correoElectronico, String perfil) {
        this.id = id;
        this.nombre = nombre;
        this.correoElectronico = correoElectronico;
        this.perfil = perfil;
        this.mensaje = "Usuario registrado exitosamente";
    }
}
