package com.rai69.Foro_Hub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rai69.Foro_Hub.dto.CursoDTO;
import com.rai69.Foro_Hub.service.CursoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/cursos")
@Tag(name = "Cursos", description = "Gestión de cursos del foro")
public class CursoController {
    
    @Autowired
    private CursoService cursoService;

    @PostMapping
    @Operation(
        summary = "Crear nuevo curso",
        description = "Crea un nuevo curso en el sistema. Solo usuarios con rol ADMIN o MODERADOR pueden crear cursos. El nombre del curso debe ser único en el sistema.",
        tags = {"Cursos"},
        security = {@SecurityRequirement(name = "Bearer Authentication")}
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "✅ Curso creado exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = CursoDTO.class),
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    name = "Curso creado",
                    value = """
                    {
                        "id": 1,
                        "nombre": "Spring Boot Avanzado",
                        "categoria": "Backend Development"
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "❌ Datos de entrada inválidos",
            content = @Content(
                mediaType = "application/json",
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    name = "Error de validación",
                    value = """
                    {
                        "error": "Bad Request",
                        "message": "El nombre del curso es requerido",
                        "timestamp": "2024-12-23T10:30:00Z"
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
            responseCode = "403",
            description = "🚫 Solo usuarios ADMIN o MODERADOR pueden crear cursos"
        ),
        @ApiResponse(
            responseCode = "409",
            description = "⚠️ Ya existe un curso con ese nombre",
            content = @Content(
                mediaType = "application/json",
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    name = "Curso duplicado",
                    value = """
                    {
                        "error": "Conflict",
                        "message": "Ya existe un curso con el nombre 'Spring Boot Avanzado'",
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
    public ResponseEntity<CursoDTO> crearCurso(
        @io.swagger.v3.oas.annotations.Parameter(
            description = "Datos del curso a crear",
            required = true,
            content = @Content(
                mediaType = "application/json",
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    name = "Nuevo curso",
                    value = """
                    {
                        "nombre": "Spring Boot Avanzado",
                        "categoria": "Backend Development"
                    }
                    """
                )
            )
        )
        @Valid @RequestBody CursoDTO cursoDTO){
        CursoDTO nuevoCurso = cursoService.crearCurso(cursoDTO);
        return ResponseEntity.ok(nuevoCurso);
    }
    
    @GetMapping("/{nombre}")
    @Operation(
        summary = "Obtener curso por nombre",
        description = "Busca y retorna un curso específico por su nombre único. Este endpoint es público y no requiere autenticación.",
        tags = {"Cursos"}
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "✅ Curso encontrado exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = CursoDTO.class),
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    name = "Curso encontrado",
                    value = """
                    {
                        "id": 1,
                        "nombre": "Spring Boot Avanzado",
                        "categoria": "Backend Development"
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "❌ Curso no encontrado",
            content = @Content(
                mediaType = "application/json",
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    name = "Curso no encontrado",
                    value = """
                    {
                        "error": "Not Found",
                        "message": "No se encontró un curso con el nombre 'Curso Inexistente'",
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
    public ResponseEntity<CursoDTO> obtenerCursoPorNombre(
        @io.swagger.v3.oas.annotations.Parameter(
            description = "Nombre único del curso a buscar",
            required = true,
            example = "Spring Boot Avanzado"
        )
        @PathVariable String nombre) {
        CursoDTO curso = cursoService.obtenerCursoPorNombre(nombre);
        if (curso == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(curso);
    }

    @GetMapping
    @Operation(
        summary = "Listar todos los cursos",
        description = "Obtiene una lista paginada de todos los cursos disponibles en el sistema. Este endpoint es público y soporta paginación para mejor rendimiento.",
        tags = {"Cursos"}
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "✅ Lista de cursos obtenida exitosamente",
            content = @Content(
                mediaType = "application/json",
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    name = "Lista de cursos",
                    value = """
                    {
                        "content": [
                            {
                                "id": 1,
                                "nombre": "Spring Boot Avanzado",
                                "categoria": "Backend Development"
                            },
                            {
                                "id": 2,
                                "nombre": "React Fundamentals",
                                "categoria": "Frontend Development"
                            },
                            {
                                "id": 3,
                                "nombre": "Database Design",
                                "categoria": "Database"
                            }
                        ],
                        "pageable": {
                            "pageNumber": 0,
                            "pageSize": 10,
                            "sort": {
                                "empty": true,
                                "sorted": false,
                                "unsorted": true
                            }
                        },
                        "totalElements": 15,
                        "totalPages": 2,
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
            responseCode = "500",
            description = "⚠️ Error interno del servidor"
        )
    })
    public ResponseEntity<Page<CursoDTO>> listarCursos(
        @io.swagger.v3.oas.annotations.Parameter(
            description = "Número de página (0-indexado)",
            example = "0"
        )
        @org.springframework.web.bind.annotation.RequestParam(defaultValue = "0") int page,
        
        @io.swagger.v3.oas.annotations.Parameter(
            description = "Tamaño de página (número de elementos por página)",
            example = "10"
        )
        @org.springframework.web.bind.annotation.RequestParam(defaultValue = "10") int size,
        
        @io.swagger.v3.oas.annotations.Parameter(
            description = "Campo de ordenamiento. Ejemplo: 'nombre' o 'categoria'",
            example = "nombre"
        )
        @org.springframework.web.bind.annotation.RequestParam(defaultValue = "nombre") String sort,
        
        @io.swagger.v3.oas.annotations.Parameter(
            description = "Dirección del ordenamiento. Valores: 'asc' o 'desc'",
            example = "asc"
        )
        @org.springframework.web.bind.annotation.RequestParam(defaultValue = "asc") String direction) {
        
        org.springframework.data.domain.Sort.Direction sortDirection = 
            direction.equalsIgnoreCase("desc") ? 
            org.springframework.data.domain.Sort.Direction.DESC : 
            org.springframework.data.domain.Sort.Direction.ASC;
            
        Pageable pageable = org.springframework.data.domain.PageRequest.of(
            page, size, org.springframework.data.domain.Sort.by(sortDirection, sort));
            
        Page<CursoDTO> cursos = cursoService.listarCursos(pageable);
        return ResponseEntity.ok(cursos);
    }

    @PutMapping("/{nombre}")
    @Operation(
        summary = "Actualizar curso",
        description = "Actualiza los datos de un curso existente identificado por su nombre. Solo usuarios con rol ADMIN o MODERADOR pueden actualizar cursos.",
        tags = {"Cursos"},
        security = {@SecurityRequirement(name = "Bearer Authentication")}
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "✅ Curso actualizado exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = CursoDTO.class),
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    name = "Curso actualizado",
                    value = """
                    {
                        "id": 1,
                        "nombre": "Spring Boot Avanzado y Microservicios",
                        "categoria": "Backend Development"
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "❌ Datos de entrada inválidos",
            content = @Content(
                mediaType = "application/json",
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    name = "Error de validación",
                    value = """
                    {
                        "error": "Bad Request",
                        "message": "El nombre del curso no puede estar vacío",
                        "timestamp": "2024-12-23T10:30:00Z"
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
            responseCode = "403",
            description = "🚫 Solo usuarios ADMIN o MODERADOR pueden actualizar cursos"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "❌ Curso no encontrado",
            content = @Content(
                mediaType = "application/json",
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    name = "Curso no encontrado",
                    value = """
                    {
                        "error": "Not Found",
                        "message": "No se encontró un curso con el nombre 'Curso Inexistente'",
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
    public ResponseEntity<CursoDTO> actualizarCurso(
        @io.swagger.v3.oas.annotations.Parameter(
            description = "Nombre único del curso a actualizar",
            required = true,
            example = "Spring Boot Avanzado"
        )
        @PathVariable String nombre, 
        @io.swagger.v3.oas.annotations.Parameter(
            description = "Nuevos datos del curso",
            required = true,
            content = @Content(
                mediaType = "application/json",
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    name = "Datos de actualización",
                    value = """
                    {
                        "nombre": "Spring Boot Avanzado y Microservicios",
                        "categoria": "Backend Development"
                    }
                    """
                )
            )
        )
        @Valid @RequestBody CursoDTO cursoDTO) {
        CursoDTO cursoExistente = cursoService.obtenerCursoPorNombre(nombre);
        if (cursoExistente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        CursoDTO cursoActualizado = cursoService.actualizarCurso(cursoDTO);
        return ResponseEntity.ok(cursoActualizado);
    }

}
