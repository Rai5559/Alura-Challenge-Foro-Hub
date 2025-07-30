package com.rai69.Foro_Hub.dto;

import jakarta.validation.constraints.NotBlank;

public record CursoDTO(
    Integer id,
    @NotBlank(message = "El nombre del curso es obligatorio")
    String nombre,
    @NotBlank(message = "La categoría del curso es obligatoria")
    String categoria
) {

}
