package com.rai69.Foro_Hub.controller;

import com.rai69.Foro_Hub.dto.TopicoRequestDTO;
import com.rai69.Foro_Hub.dto.TopicoResponseDTO;
import com.rai69.Foro_Hub.exception.DuplicatedTopicException;
import com.rai69.Foro_Hub.exception.EntityNotFoundException;
import com.rai69.Foro_Hub.service.TopicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoService topicoService;
    
    @PostMapping
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
    public ResponseEntity<Page<TopicoResponseDTO>> listarTopicos(Pageable pageable) {
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
