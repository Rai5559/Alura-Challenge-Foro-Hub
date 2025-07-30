package com.rai69.Foro_Hub.controller;

import com.rai69.Foro_Hub.dto.TopicoRequestDTO;
import com.rai69.Foro_Hub.dto.TopicoResponseDTO;
import com.rai69.Foro_Hub.exception.DuplicatedTopicException;
import com.rai69.Foro_Hub.exception.EntityNotFoundException;
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
    @Operation(summary = "Crear tópico", description = "Crea un nuevo tópico en el foro")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Tópico creado exitosamente",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = TopicoResponseDTO.class))),
        @ApiResponse(responseCode = "409", description = "Tópico duplicado"),
        @ApiResponse(responseCode = "404", description = "Curso o usuario no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<TopicoResponseDTO> registrarTopico(
            @Valid @RequestBody TopicoRequestDTO topicoRequestDTO) {
        
        try {
            TopicoResponseDTO response = topicoService.crearTopico(topicoRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (DuplicatedTopicException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
            
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener tópico", description = "Obtiene un tópico específico por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tópico encontrado",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = TopicoResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Tópico no encontrado")
    })
    public ResponseEntity<TopicoResponseDTO> obtenerTopico(@PathVariable Integer id) {
        try {
            TopicoResponseDTO response = topicoService.obtenerTopico(id);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<Page<TopicoResponseDTO>> listarTopicos(
            @PageableDefault(size = 10, page = 0, sort = "fechaCreacion", direction = Sort.Direction.ASC) 
            Pageable pageable) {
        try {
            Page<TopicoResponseDTO> response = topicoService.listarTopicos(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopicoResponseDTO> actualizarTopico(
            @PathVariable Integer id,
            @Valid @RequestBody TopicoRequestDTO topicoRequestDTO) {
        
        try {
            TopicoResponseDTO response = topicoService.actualizarTopico(id, topicoRequestDTO);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTopico(@PathVariable Integer id) {
        try {
            topicoService.eliminarTopico(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
