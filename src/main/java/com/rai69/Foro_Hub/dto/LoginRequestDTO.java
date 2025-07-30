package com.rai69.Foro_Hub.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDTO {
    
    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "Debe ser un correo electrónico válido")
    private String correoElectronico;
    
    @NotBlank(message = "La contraseña es obligatoria")
    private String contrasena;
}
