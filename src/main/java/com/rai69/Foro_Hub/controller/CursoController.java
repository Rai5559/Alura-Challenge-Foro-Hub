package com.rai69.Foro_Hub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rai69.Foro_Hub.dto.CursoDTO;
import com.rai69.Foro_Hub.exception.EntityNotFoundException;
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
    @Operation(summary = "Crear curso", description = "Crea un nuevo curso en el sistema (requiere rol ADMIN o MODERADOR)")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Curso creado exitosamente",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = CursoDTO.class))),
        @ApiResponse(responseCode = "404", description = "Entidad no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<CursoDTO> crearCurso(
        @Valid @RequestBody CursoDTO cursoDTO){
        try {
            CursoDTO nuevoCurso = cursoService.crearCurso(cursoDTO);
            return ResponseEntity.ok(nuevoCurso);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/{nombre}")
    public ResponseEntity<CursoDTO> obtenerCursoPorNombre(@PathVariable String nombre) {
        try {
            CursoDTO curso = cursoService.obtenerCursoPorNombre(nombre);
            if (curso == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.ok(curso);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<Page<CursoDTO>> listarCursos(
        @PageableDefault(size = 10, page = 0) Pageable pageable) {
        try {
            Page<CursoDTO> cursos = cursoService.listarCursos(pageable);
            return ResponseEntity.ok(cursos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{nombre}")
    public ResponseEntity<CursoDTO> actualizarCurso(
        @PathVariable String nombre, @Valid @RequestBody CursoDTO cursoDTO) {
        try {
            CursoDTO cursoExistente = cursoService.obtenerCursoPorNombre(nombre);
            if (cursoExistente == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            CursoDTO cursoActualizado = cursoService.actualizarCurso(cursoDTO);
            return ResponseEntity.ok(cursoActualizado);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
