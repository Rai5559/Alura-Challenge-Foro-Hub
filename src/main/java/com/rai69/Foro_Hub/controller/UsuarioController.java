package com.rai69.Foro_Hub.controller;

import com.rai69.Foro_Hub.dto.UsuarioResponseDTO;
import com.rai69.Foro_Hub.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuarios", description = "Gestión de usuarios del foro")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/{id}")
    @Operation(
        summary = "Obtener usuario por ID",
        description = "Busca y retorna la información de un usuario específico por su ID único. Solo usuarios autenticados pueden acceder a esta información.",
        tags = {"Usuarios"},
        security = {@SecurityRequirement(name = "Bearer Authentication")}
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "✅ Usuario encontrado exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = UsuarioResponseDTO.class),
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    name = "Usuario encontrado",
                    value = """
                    {
                        "id": 1,
                        "nombre": "Carlos Rodriguez",
                        "email": "carlos@example.com",
                        "perfil": {
                            "id": 3,
                            "nombre": "USER"
                        }
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "401",
            description = "🔐 Token JWT requerido o inválido"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "❌ Usuario no encontrado",
            content = @Content(
                mediaType = "application/json",
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    name = "Usuario no encontrado",
                    value = """
                    {
                        "error": "Not Found",
                        "message": "No se encontró un usuario con ID: 999",
                        "timestamp": "2024-12-23T10:30:00Z"
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "⚠️ Error interno del servidor"
        )
    })
    public ResponseEntity<UsuarioResponseDTO> obtenerUsuario(
        @io.swagger.v3.oas.annotations.Parameter(
            description = "ID único del usuario a buscar",
            required = true,
            example = "1"
        )
        @PathVariable Integer id) {
        UsuarioResponseDTO usuario = usuarioService.obtenerUsuarioPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping
    @Operation(
        summary = "Listar todos los usuarios",
        description = "Obtiene una lista paginada de todos los usuarios registrados en el sistema, ordenados por nombre. Solo usuarios autenticados pueden acceder a esta información.",
        tags = {"Usuarios"},
        security = {@SecurityRequirement(name = "Bearer Authentication")}
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "✅ Lista de usuarios obtenida exitosamente",
            content = @Content(
                mediaType = "application/json",
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    name = "Lista de usuarios",
                    value = """
                    {
                        "content": [
                            {
                                "id": 1,
                                "nombre": "Ana García",
                                "email": "ana@example.com",
                                "perfil": {
                                    "id": 1,
                                    "nombre": "ADMIN"
                                }
                            },
                            {
                                "id": 2,
                                "nombre": "Carlos Rodriguez",
                                "email": "carlos@example.com",
                                "perfil": {
                                    "id": 3,
                                    "nombre": "USER"
                                }
                            },
                            {
                                "id": 3,
                                "nombre": "María López",
                                "email": "maria@example.com",
                                "perfil": {
                                    "id": 2,
                                    "nombre": "MODERADOR"
                                }
                            }
                        ],
                        "pageable": {
                            "pageNumber": 0,
                            "pageSize": 10,
                            "sort": {
                                "empty": false,
                                "sorted": true,
                                "unsorted": false
                            }
                        },
                        "totalElements": 25,
                        "totalPages": 3,
                        "last": false,
                        "first": true,
                        "numberOfElements": 3,
                        "size": 10,
                        "number": 0,
                        "empty": false
                    }
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
    public ResponseEntity<Page<UsuarioResponseDTO>> listarUsuarios(
        @io.swagger.v3.oas.annotations.Parameter(
            description = "Número de página (0-indexado)",
            example = "0"
        )
        @RequestParam(defaultValue = "0") int page,
        
        @io.swagger.v3.oas.annotations.Parameter(
            description = "Tamaño de página (número de elementos por página)",
            example = "10"
        )
        @RequestParam(defaultValue = "10") int size,
        
        @io.swagger.v3.oas.annotations.Parameter(
            description = "Campo de ordenamiento. Ejemplo: 'nombre', 'correoElectronico'",
            example = "nombre"
        )
        @RequestParam(defaultValue = "nombre") String sort,
        
        @io.swagger.v3.oas.annotations.Parameter(
            description = "Dirección del ordenamiento. Valores: 'asc' o 'desc'",
            example = "asc"
        )
        @RequestParam(defaultValue = "asc") String direction) {
        
        Sort.Direction sortDirection = 
            direction.equalsIgnoreCase("desc") ? 
            Sort.Direction.DESC : 
            Sort.Direction.ASC;
            
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));
        Page<UsuarioResponseDTO> usuarios = usuarioService.listarUsuarios(pageable);
        return ResponseEntity.ok(usuarios);
    }
}
