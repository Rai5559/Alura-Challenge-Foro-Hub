package com.rai69.Foro_Hub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponseDTO {
    private String token;
    private String type = "Bearer";
    private String email;
    private String nombre;

    public JwtResponseDTO(String token, String email, String nombre) {
        this.token = token;
        this.email = email;
        this.nombre = nombre;
    }
}
