package com.rai69.Foro_Hub.controller;

import com.rai69.Foro_Hub.dto.TopicoRequestDTO;
import com.rai69.Foro_Hub.dto.TopicoResponseDTO;
import com.rai69.Foro_Hub.service.TopicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/topicos")
@Tag(name = "Tópicos", description = "Gestión de tópicos del foro")
public class TopicoController {

    @Autowired
    private TopicoService topicoService;
    
    @PostMapping
    @Operation(
        summary = "Crear nuevo tópico",
        description = "Crea un nuevo tópico en el foro. Requiere autenticación JWT. El usuario debe estar autenticado.",
        tags = {"Tópicos"},
        security = {@SecurityRequirement(name = "Bearer Authentication")}
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201", 
            description = "✅ Tópico creado exitosamente",
            content = @Content(
                mediaType = "application/json", 
                schema = @Schema(implementation = TopicoResponseDTO.class),
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    name = "Tópico creado",
                    value = """
                    {
                        "id": 1,
                        "titulo": "¿Cómo usar Spring Security?",
                        "mensaje": "Tengo dudas sobre la configuración...",
                        "fechaCreacion": "2025-07-29T15:30:00",
                        "estado": "ACTIVO",
                        "usuario": {
                            "id": 1,
                            "nombre": "Juan Pérez",
                            "correoElectronico": "juan@ejemplo.com"
                        },
                        "curso": {
                            "id": 1,
                            "nombre": "Spring Boot",
                            "categoria": "Backend"
                        }
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "409", 
            description = "❌ Tópico duplicado - Ya existe un tópico con el mismo título en el curso"
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "❌ Curso o usuario no encontrado"
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
    public ResponseEntity<TopicoResponseDTO> registrarTopico(
        @io.swagger.v3.oas.annotations.Parameter(
            description = "Datos del nuevo tópico",
            required = true,
            schema = @Schema(implementation = TopicoRequestDTO.class),
            example = """
            {
                "titulo": "¿Cómo usar Spring Security?",
                "mensaje": "Tengo dudas sobre la configuración de Spring Security con JWT...",
                "cursoId": 1,
                "usuarioId": 1
            }
            """
        )
        @Valid @RequestBody TopicoRequestDTO topicoRequestDTO) {
        
        TopicoResponseDTO response = topicoService.crearTopico(topicoRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Obtener tópico por ID",
        description = "Obtiene un tópico específico por su identificador único",
        tags = {"Tópicos"}
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "✅ Tópico encontrado",
            content = @Content(
                mediaType = "application/json", 
                schema = @Schema(implementation = TopicoResponseDTO.class),
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    name = "Tópico encontrado",
                    value = """
                    {
                        "id": 1,
                        "titulo": "¿Cómo usar Spring Security?",
                        "mensaje": "Tengo dudas sobre la configuración...",
                        "fechaCreacion": "2025-07-29T15:30:00",
                        "estado": "ACTIVO",
                        "usuario": {
                            "id": 1,
                            "nombre": "Juan Pérez",
                            "correoElectronico": "juan@ejemplo.com"
                        },
                        "curso": {
                            "id": 1,
                            "nombre": "Spring Boot",
                            "categoria": "Backend"
                        }
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "❌ Tópico no encontrado con el ID especificado"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "⚠️ Error interno del servidor"
        )
    })
    public ResponseEntity<TopicoResponseDTO> obtenerTopico(
        @io.swagger.v3.oas.annotations.Parameter(
            description = "ID único del tópico a obtener",
            required = true,
            example = "1"
        )
        @PathVariable Integer id) {
        TopicoResponseDTO response = topicoService.obtenerTopico(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(
        summary = "Listar tópicos con paginación",
        description = "Obtiene una lista paginada de todos los tópicos del foro, ordenados por fecha de creación",
        tags = {"Tópicos"}
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "✅ Lista de tópicos obtenida exitosamente",
            content = @Content(
                mediaType = "application/json",
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    name = "Lista paginada de tópicos",
                    value = """
                    {
                        "content": [
                            {
                                "id": 1,
                                "titulo": "¿Cómo usar Spring Security?",
                                "mensaje": "Tengo dudas sobre la configuración...",
                                "fechaCreacion": "2025-07-29T15:30:00",
                                "estado": "ACTIVO",
                                "usuario": {
                                    "id": 1,
                                    "nombre": "Juan Pérez"
                                },
                                "curso": {
                                    "id": 1,
                                    "nombre": "Spring Boot"
                                }
                            }
                        ],
                        "pageable": {
                            "pageNumber": 0,
                            "pageSize": 10,
                            "sort": {
                                "sorted": true,
                                "orderBy": "fechaCreacion"
                            }
                        },
                        "totalElements": 1,
                        "totalPages": 1,
                        "first": true,
                        "last": true,
                        "numberOfElements": 1
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
    public ResponseEntity<Page<TopicoResponseDTO>> listarTopicos(
        @io.swagger.v3.oas.annotations.Parameter(
            description = "Parámetros de paginación y ordenamiento",
            examples = {
                @io.swagger.v3.oas.annotations.media.ExampleObject(
                    name = "Primera página",
                    value = "page=0&size=10&sort=fechaCreacion,asc"
                ),
                @io.swagger.v3.oas.annotations.media.ExampleObject(
                    name = "Ordenar por título",
                    value = "page=0&size=5&sort=titulo,desc"
                )
            }
        )
        @PageableDefault(size = 10, page = 0, sort = "fechaCreacion", direction = Sort.Direction.ASC) 
        Pageable pageable) {
        Page<TopicoResponseDTO> response = topicoService.listarTopicos(pageable);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar tópico",
        description = "Actualiza un tópico existente. Requiere autenticación JWT. Solo el propietario puede actualizar su tópico.",
        tags = {"Tópicos"},
        security = {@SecurityRequirement(name = "Bearer Authentication")}
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "✅ Tópico actualizado exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = TopicoResponseDTO.class),
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    name = "Tópico actualizado",
                    value = """
                    {
                        "id": 1,
                        "titulo": "¿Cómo usar Spring Security? - RESUELTO",
                        "mensaje": "Ya entendí la configuración, gracias por la ayuda...",
                        "fechaCreacion": "2025-07-29T15:30:00",
                        "estado": "RESUELTO",
                        "usuario": {
                            "id": 1,
                            "nombre": "Juan Pérez"
                        },
                        "curso": {
                            "id": 1,
                            "nombre": "Spring Boot"
                        }
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "❌ Tópico no encontrado"
        ),
        @ApiResponse(
            responseCode = "401",
            description = "🔐 Token JWT requerido o inválido"
        ),
        @ApiResponse(
            responseCode = "403",
            description = "🚫 Sin permisos para actualizar este tópico"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "⚠️ Error interno del servidor"
        )
    })
    public ResponseEntity<TopicoResponseDTO> actualizarTopico(
        @io.swagger.v3.oas.annotations.Parameter(
            description = "ID único del tópico a actualizar",
            required = true,
            example = "1"
        )
        @PathVariable Integer id,
        @io.swagger.v3.oas.annotations.Parameter(
            description = "Nuevos datos del tópico",
            required = true,
            schema = @Schema(implementation = TopicoRequestDTO.class),
            example = """
            {
                "titulo": "¿Cómo usar Spring Security? - RESUELTO",
                "mensaje": "Ya entendí la configuración, gracias por la ayuda...",
                "cursoId": 1,
                "usuarioId": 1
            }
            """
        )
        @Valid @RequestBody TopicoRequestDTO topicoRequestDTO) {
        
        TopicoResponseDTO response = topicoService.actualizarTopico(id, topicoRequestDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Eliminar tópico",
        description = "Elimina un tópico del foro. Solo usuarios con rol ADMIN pueden eliminar tópicos.",
        tags = {"Tópicos"},
        security = {@SecurityRequirement(name = "Bearer Authentication")}
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "✅ Tópico eliminado exitosamente"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "❌ Tópico no encontrado"
        ),
        @ApiResponse(
            responseCode = "401",
            description = "🔐 Token JWT requerido o inválido"
        ),
        @ApiResponse(
            responseCode = "403",
            description = "🚫 Solo usuarios ADMIN pueden eliminar tópicos"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "⚠️ Error interno del servidor"
        )
    })
    public ResponseEntity<Void> eliminarTopico(
        @io.swagger.v3.oas.annotations.Parameter(
            description = "ID único del tópico a eliminar",
            required = true,
            example = "1"
        )
        @PathVariable Integer id) {
        topicoService.eliminarTopico(id);
        return ResponseEntity.noContent().build();
    }
}
