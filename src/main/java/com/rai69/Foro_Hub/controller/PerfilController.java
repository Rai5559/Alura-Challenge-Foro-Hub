package com.rai69.Foro_Hub.controller;

import com.rai69.Foro_Hub.model.PerfilModel;
import com.rai69.Foro_Hub.repository.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/perfiles")
@Tag(name = "Perfiles", description = "Gestión de perfiles de usuario (roles) del sistema")
public class PerfilController {

    @Autowired
    private PerfilRepository perfilRepository;

    @GetMapping
    @Operation(
        summary = "Listar todos los perfiles",
        description = "Obtiene la lista completa de perfiles (roles) disponibles en el sistema. Los perfiles incluyen: ADMIN (ID=1), MODERADOR (ID=2), y USER (ID=3). Solo usuarios autenticados pueden acceder a esta información.",
        tags = {"Perfiles"},
        security = {@SecurityRequirement(name = "Bearer Authentication")}
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "✅ Lista de perfiles obtenida exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = PerfilModel.class),
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    name = "Lista de perfiles",
                    value = """
                    [
                        {
                            "id": 1,
                            "nombre": "ADMIN"
                        },
                        {
                            "id": 2,
                            "nombre": "MODERADOR"
                        },
                        {
                            "id": 3,
                            "nombre": "USER"
                        }
                    ]
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "401",
            description = "🔐 Token JWT requerido o inválido"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "⚠️ Error interno del servidor"
        )
    })
    public ResponseEntity<List<PerfilModel>> listarPerfiles() {
        List<PerfilModel> perfiles = perfilRepository.findAll();
        return ResponseEntity.ok(perfiles);
    }
}
